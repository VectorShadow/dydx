package linker.local;

public class ClientLocalDataLink extends AbstractLocalDataLink {
    @Override
    public void handle(byte instruction, byte[] body) {
        System.out.println("\nInstruction: " + instruction + " Size: " + body.length + " Body: ");
        for (byte b : body) {
            System.out.print(b + ", ");
        }
        //todo - same as ClientRemoteDataLink.handle()
    }
}
