package linker.remote;

import data.DataPacker;
import data.StreamConverter;
import error.ErrorLogger;
import error.LogReadyTraceableException;
import linker.AbstractDataLink;
import server.FileManager;

import java.io.EOFException;
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

    public AbstractRemoteDataLink(Socket s) {
        socket = s;
    }

    public void send(byte[] transmission){
        try {
            socket.getOutputStream().write(transmission);
        } catch (IOException ioe) {
            ErrorLogger.logFatalException(ErrorLogger.trace(ioe));
        }
    }
    public void listen() throws LogReadyTraceableException {
        byte instruction = 0; //byte code associated with a unique instruction type
        int instructionBodySize = 0; //the number of bytes expected by the current instruction
        int bytesReadInInstruction = 0; //the number of bytes read from the stream so far for this instruction
        byte[] instructionBody = new byte[0]; //an array which accumulates the data for this instruction
        byte[] streamBlock; //the block read from the stream this iteration
        ArrayList<Byte> excess = new ArrayList<>(); //leftover data in the stream after completing the last instruction
        int bytesRead = 0; //the number of bytes read from the stream this iteration
        boolean firstBlock = true; //only the first block of a transmission should exclude the header segment
        for (;;) {
            try {
                if (excess.size() > 0) {
                    streamBlock = listToArray(excess);
                    bytesRead = excess.size();
                    excess = new ArrayList<>();
                } else {
                    streamBlock = new byte[BLOCK_SIZE];
                    bytesRead = socket.getInputStream().read(streamBlock,0, BLOCK_SIZE);
                }
                if (bytesRead < 0) { //error reading on socket - connection lost
                    socket.close();
                    if (this instanceof ServerRemoteDataLink)  //for now - try to save the character if connection is lost - more todo
                        FileManager.saveCharacter(account.getAccountName(), character);
                    //todo - additionally handling here - let the server or client know connection was lost.
                } else if (bytesRead > 0){ //data was read from the stream this pass - we do nothing on 0
                    if (instruction == 0) { //if we have no current instruction, parse for the next one
                        if (bytesRead < DataPacker.HEADER_LENGTH) throw new IllegalArgumentException( //we need a 4 byte header to begin
                                "Stream contained too few bytes to parse: " + bytesRead + " bytes were in the stream.");
                        instruction = streamBlock[0]; //set the instruction code
                        instructionBodySize = DataPacker.readSize(streamBlock[1], streamBlock[2], streamBlock[3]); //get the size as an int
                        instructionBody = new byte[instructionBodySize]; //initialize the body block
                    } else firstBlock = false; //else ensure we no longer exclude the header until this instruction is complete
                    for (int i = firstBlock ? DataPacker.HEADER_LENGTH : 0; i < bytesRead; ++i) { //iterate through the bytes read this pass
                        if (bytesReadInInstruction < instructionBodySize){ //bytes from the current instruction
                            instructionBody[bytesReadInInstruction++] = streamBlock[i];
                        } else { //bytes from a new instruction
                            excess.add(streamBlock[i]); //these go into excess to be handled next pass
                        }
                    }
                    if (bytesReadInInstruction >= instructionBodySize) { //if we finished an instruction, handle it
                        handle(instruction, StreamConverter.toObject(instructionBody));
                        instruction = 0; //then reset the data members
                        instructionBodySize = 0;
                        bytesReadInInstruction = 0;
                        firstBlock = true;
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
    private byte[] listToArray(ArrayList<Byte> list) {
        byte[] array = new byte[BLOCK_SIZE];
        for (int i = 0; i < list.size(); ++i) array[i] = list.get(i);
        return array;
    }
    private byte[] concatenate(ArrayList<Byte> excess, byte[] stream, int streamSize) {
        byte[] total = new byte[excess.size() + streamSize];
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
