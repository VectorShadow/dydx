package linker.remote;

import error.LogReadyTraceableException;

import java.net.Socket;

public class ClientRemoteDataLink extends AbstractRemoteDataLink {

    public ClientRemoteDataLink(Socket s) throws LogReadyTraceableException {
        super(s);
    }

    @Override
    public void handle(byte instruction, byte[] body) {
        System.out.println("\nInstruction: " + instruction + " Size: " + body.length + " Body: ");
        for (byte b : body) {
            System.out.print(b + ", ");
        }
        //todo - called from AbstractRemoteDataLink.listen()
    }
}
