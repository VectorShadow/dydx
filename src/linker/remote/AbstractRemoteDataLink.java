package linker.remote;

import data.DataPacker;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.AbstractDataLink;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 * defines the behavior of a DataLink between a client and server connected by socket remotely
 */
public abstract class AbstractRemoteDataLink extends AbstractDataLink {

    private static final int BLOCK_SIZE = 1_024;

    Socket socket;

    public AbstractRemoteDataLink(Socket s) throws LogReadyTraceableException {
        socket = s;
    }

    public void send(byte[] transmission) throws LogReadyTraceableException {
        try {
            socket.getOutputStream().write(transmission);
        } catch (IOException ioe) {
            throw ErrorLogger.trace(ioe);
        }
    }
    protected void listen() throws LogReadyTraceableException {
        //todo - loop on the instream until an instruction can be pieced together, then handle() it
        byte instruction = 0; //byte code associated with a unique instruction type
        int instructionBodySize = 0; //the number of bytes expected by the current instruction
        int bytesReadInInstruction = 0; //the number of bytes read from the stream so far for this instruction
        byte[] instructionBody = new byte[0]; //an array which accumulates the data for this instruction
        byte[] streamBlock; //the block read from the stream this iteration
        ArrayList<Byte> excess = new ArrayList<>(); //leftover data in the stream after completing the last instruction
        int bytesRead = 0; //the number of bytes read from the stream this iteration
        for (;;) {
            try {
                streamBlock = new byte[BLOCK_SIZE]; //reset the stream block
                bytesRead = socket.getInputStream().read(streamBlock, 0 , BLOCK_SIZE); //read up to 1024 bytes from the stream
                if (excess.size() > 0) { //handle excess data from the previous pass
                    streamBlock = concatenate(excess, streamBlock); //build a new block from old excess and current read
                    bytesRead = streamBlock.length; //adjust bytes read to account for prepended data
                    excess = new ArrayList<>(); //reset excess
                }
                if (bytesRead < 0) { //error reading on socket - connection lost
                    socket.close();
                    //todo - additionally handling here - let the server or client know connection was lost.
                } else if (bytesRead > 0){ //data was read from the stream this pass - we do nothing on 0
                    if (instruction == 0) { //if we have no current instruction, parse for the next one
                        if (bytesRead < DataPacker.HEADER_LENGTH) throw new IllegalArgumentException( //we need a 4 byte header to begin
                                "Stream contained too few bytes to parse: " + bytesRead + " bytes were in the stream.");
                        instruction = streamBlock[0]; //set the instruction code
                        instructionBodySize = DataPacker.readSize(streamBlock[1], streamBlock[2], streamBlock[3]); //get the size as an int
                        instructionBody = new byte[instructionBodySize]; //initialize the body block
                    }
                    for (int i = DataPacker.HEADER_LENGTH; i < bytesRead; ++i, bytesReadInInstruction++) { //iterate through the bytes read this pass
                        if (bytesReadInInstruction < instructionBodySize){ //bytes from the current instruction
                            instructionBody[bytesReadInInstruction] = streamBlock[i];
                        } else { //bytes from a new instruction
                            excess.add(streamBlock[i]); //these go into excess to be handled next pass
                        }
                    }
                    if (bytesReadInInstruction >= instructionBodySize) { //if we finished an instruction, handle it
                        handle(instruction, instructionBody);
                        instruction = 0; //then reset the data members
                        instructionBodySize = 0;
                        bytesReadInInstruction = 0;
                    }
                }
            } catch (SocketException se){
                break;
            } catch (IOException ioe) {
                throw ErrorLogger.trace(ioe);
            }
        }
        terminate();
    }
    private byte[] concatenate(ArrayList<Byte> excess, byte[] stream) {
        byte[] total = new byte[excess.size() + stream.length];
        int index = 0;
        while (index < excess.size()) {
            total[index] = excess.get(index);
            index++;
        }
        while (index < total.length) {
            total[index] = stream[index - excess.size()];
            index++;
        }
        return total;
    }
    @Override
    public void run() {
        try {
            listen();
        } catch (LogReadyTraceableException e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
}
