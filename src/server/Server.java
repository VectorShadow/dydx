package server;

import linker.ConnectionProperties;
import error.ErrorLogger;
import linker.AbstractDataLink;
import linker.ServerDataLink;
import linker.remote.ServerRemoteDataLink;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
    ServerSocket serverSocket = null;
    Socket socket = null;
    ConnectionProperties connectionProperties = new ConnectionProperties();
    ArrayList<ServerDataLink> openConnections = new ArrayList<>();

    public void run() {
        try {
            serverSocket = new ServerSocket(connectionProperties.getPort());
            while (true) {
                socket = serverSocket.accept();
                ServerRemoteDataLink srdl = new ServerRemoteDataLink(socket);
                srdl.start();
                openConnections.add(srdl);
            }
        } catch (Exception e) {
            ErrorLogger.logFatalException(ErrorLogger.trace(e));
        }
    }
    public ArrayList<ServerDataLink> getOpenConnections() {
        return openConnections;
    }
}
