package server;


import config.Messages;
import config.Parameter;
import task.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ResponseToClient {
    private String host;
    private int port;
    private Socket socket;
    private BufferedReader theIn;
    private PrintStream theOut;
    private String resultLocation;

    /**
     * Constructor
     */
    public ResponseToClient(String path) {
        String[] parameters = Parameter.client_server();
        this.host = parameters[0];
        this.port = Integer.parseInt(parameters[1]);
        this.resultLocation = path;
    }

    /**
     * Setting up Socket
     */
    public void init(){
        try{

            socket = new Socket(host, port);
            theOut = new PrintStream(socket.getOutputStream());

            //Send Command
            sendCommand();

            //Closing connections
            socket.close();
            theOut.close();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Send Command
     */
    public void sendCommand()
    {
        theOut.println(Messages.type_response + Messages.seperator_client + resultLocation);
        System.out.println(Messages.type_response + Messages.seperator_client + resultLocation);
    }


}
