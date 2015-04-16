package server;

import com.google.gson.Gson;
import config.Messages;
import config.Parameter;
import task.Task;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class responbile for receiving messages from PHP-Client
 * And transmits data to Master GeneralServer
 */
public class GeneralServer {

    private int port;
    private BufferedReader theIn;
    private PrintStream theOut;
    private Socket connection;

    /**
     * Constructor
     */
    public GeneralServer(){
        String[] parameters = Parameter.general_server();
        port = Integer.parseInt(parameters[1]);
        theOut = null;
    }

    /**
     * Setting up GeneralServer Socket
     */
    public void init()
    {
        System.out.println("General Server: ");
        try{
            ServerSocket socket = new ServerSocket(port);
            while(true)
            {
                // open socket
                System.out.println("Waiting for client");
                connection = socket.accept();
                System.out.println( "Client Connected");

                // read command
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                theIn = new BufferedReader(inputStream);
                readCommand();

                // send response
                theOut = new PrintStream(connection.getOutputStream());
                close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Reading Command
     */
    public void readCommand(){
        try {

            String str = theIn.readLine();

            String[] command = str.split(Messages.seperator_client);
            System.out.println("Message Recieved : " +str );
            if(command[0].equals(Messages.type_request_client))
            { //Request from Client
                System.out.println("Request From Client ---");
                Gson gson = new Gson();
                Task task = gson.fromJson(command[1], Task.class);
                boolean status = task.analyseParameters();

                if(status){
                    //Create a task for master
                    RequestMaster masterRequest = new RequestMaster(task);
                    masterRequest.init();
                }
            } else
            { //Response from Master
                System.out.println("Response From Master ---");
                sendResponse(command[1]);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Send Response
     */
    public void sendResponse(String resultLocation)
    {
        System.out.println("Sending Response to Client");
        ResponseToClient response = new ResponseToClient(resultLocation);
        response.init();

    }

    public void close(){
        try {
            connection.close();
            theIn.close();
            theOut.close();
//            theIn=null;
//            theOut=null;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args){

        GeneralServer server = new GeneralServer();
        server.init();

    }

}

