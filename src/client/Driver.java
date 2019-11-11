package client;

import contract.Gui;
import core.DualityGUI;
import data.TestDatum;
import error.ErrorLogger;
import linker.AbstractDataLink;
import data.StreamConverter;
import server.engine.WorldManager;

public class Driver {
    /**
     * executable for starting the client program
     * @param args
     */
    public static void main(String[] args) {
        try {
            //open a GUI, attempt to connect to a remote server, and display information
            //if no connection can be established or player chooses to play locally, create a new WorldManager
            //and establish a datalink bound to the WorldManager's datalink.
            //if connection is establised, create a datalink bound to the connection socket.

            /* test */
            WorldManager wm = new WorldManager(false);
            AbstractDataLink adl = wm.generateClientDataLink();
//            AbstractDataLink adl = new Client().connect();
            adl.send(new byte[] {1, 0, 0, 5, 0, 1, 2, 3, 4});
            adl.send(new byte[] {7, 0, 0, 0x10, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f});
            TestDatum td = new TestDatum();
            System.out.println("" + (td.getTestString().equals(((TestDatum)(StreamConverter.toObject(StreamConverter.toByteArray(td)))).getTestString())));
            Gui gui = new DualityGUI();
        } catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
}
