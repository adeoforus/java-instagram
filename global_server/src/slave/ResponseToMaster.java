package slave;

import com.google.gson.Gson;
import config.Messages;
import config.Parameter;
import sun.misc.resources.Messages_es;
import task.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Respond to Master Server
 */
public class ResponseToMaster {

    private String host;
    private int port;
    private Socket socket;
    private BufferedReader theIn;
    private PrintStream theOut;
    private int id;
    private String message;
    private String options;

    /**
     * Constructor
     */
    public ResponseToMaster(int id, String message, String options )
    {
        String[] parameters = Parameter.master_server();
        this.host = parameters[0];
        this.port = Integer.parseInt(parameters[1]);
        this.id = id;
        this.message = message;
        this.options = options;
    }

    /**
     * Setting up Socket
     */
    public void init()
    {
        try
        {
            Socket socket = new Socket(host, port);
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
        String response = id + Messages.seperator_slave + message + Messages.seperator_slave + options+ "\n";
        theOut.println(Messages.type_response + Messages.seperator + response);
        System.out.println("Response TO MASter: " + Messages.type_response + Messages.seperator + response);
    }

    public static void main(String[] args)
    {
        Task task = new Task("barcelona",0,0);
        Gson gson = new Gson();
        ResponseToMaster response = new ResponseToMaster(0, Messages.response_OK, gson.toJson(task));
        response.sendCommand();
    }

}