package linker.local;

import linker.ServerDataLink;

public class ServerLocalDataLink extends AbstractLocalDataLink implements ServerDataLink {
    @Override
    public void handle(byte instruction, byte[] body) {
        System.out.println("\nInstruction: " + instruction + " Size: " + body.length + " Body: ");
        for (byte b : body) {
            System.out.print(b + ", ");
        }
        //todo - same as ServerRemoteDataLink.handle()
    }
}
