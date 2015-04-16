package server;

import com.google.gson.Gson;
import config.Messages;
import config.Parameter;
import task.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Simulating PHP-Client Task
 */
public class Client {

    private String host;
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader theIn;
    private PrintStream theOut;
    private Task task;

    /**
     * Constructor
     * @param tags
     * @param sort
     * @param cloud
     */
    public Client(String tags, int sort, int cloud) {
        String[] parameters = Parameter.client_server();
        this.host = parameters[0];
        this.port = Integer.parseInt(parameters[1]);
        task = new Task(tags, sort, cloud);
    }

    /**
     * Setting up Socket
     */
    public void init(){
        System.out.println("Client Server: ");
        System.out.println("Sending Request to General Server");
        RequestGeneralServer request = new RequestGeneralServer(task);
        request.init();

        try{
            serverSocket = new ServerSocket(port);
            while(true) {
                System.out.println("Waiting for General Server Response");
                socket = serverSocket.accept();

                theIn = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                //read Response
                readResponse();

                theIn.close();
                socket.close();
                System.exit(999);
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Read Response
     */
    public void readResponse(){
        try {
            String message = theIn.readLine();
            System.out.println(message);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Testing
     * Main Method
     * @param args
     */
    public static void main(String[] args){
        Client request = new Client("barcelona",0,0);
        request.init();

    }
}

