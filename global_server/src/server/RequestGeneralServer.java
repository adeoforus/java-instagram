package server;

import com.google.gson.Gson;
import config.Messages;
import config.Parameter;
import task.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Send a task to Master Server
 */
public class RequestGeneralServer {

    private String host;
    private int port;
    private Socket socket;
    private BufferedReader theIn;
    private PrintStream theOut;
    private Task task;

    /**
     * Constructor
     */
    public RequestGeneralServer(Task request) {
        String[] parameters = Parameter.general_server();
        this.host = parameters[0];
        this.port = Integer.parseInt(parameters[1]);
        this.task = request;
    }

    /**
     * Setting up Socket
     */
    public void init(){
        System.out.println("Init Request to General Server" + task.toString());

        try
        {
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
        Gson gson = new Gson();
        String task = gson.toJson(this.task);
        theOut.println(Messages.type_request_client+Messages.seperator_client+task);
        System.out.println(Messages.type_request_client+Messages.seperator_client+task);
    }


}

