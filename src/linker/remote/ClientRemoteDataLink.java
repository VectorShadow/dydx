package linker.remote;

import data.StreamConverter;
import data.crypto.RSAPublicKeyDatum;
import error.LogReadyTraceableException;
import linker.ClientDataLink;

import java.net.Socket;

public class ClientRemoteDataLink extends AbstractRemoteDataLink implements ClientDataLink {

    public ClientRemoteDataLink(Socket s) throws LogReadyTraceableException {
        super(s);
    }

    @Override
    public void handle(byte instruction, byte[] body) {
        System.out.println("\nInstruction: " + instruction + " Size: " + body.length + " Body: ");
        for (byte b : body) {
            System.out.print(b + ", ");
        }
        //test
        System.out.println("\nKey: " + ((RSAPublicKeyDatum) StreamConverter.toObject(body)).getKey());
        //todo - called from AbstractRemoteDataLink.listen()
    }
}
