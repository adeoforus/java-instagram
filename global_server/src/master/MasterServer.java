package master;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import config.Messages;
import config.Parameter;
import server.RequestMaster;
import server.RequestSlave;
import task.Task;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class listening to General Server
 */
public class MasterServer {

    private String host;
    private int port;
    private Socket connection;
    private BufferedReader theIn;
    private PrintStream theOut;
    private Task request;
    private Master master;
    /**
     * Constructor
     */
    public MasterServer()
    {
        String[] parameters = Parameter.master_server();
        this.host = parameters[0];
        this.port = Integer.parseInt(parameters[1]);
        this.master = new Master();

    }

    /**
     * Setting up Server Socket
     */
    public void init()
    {
        System.out.println("Master General Server: ");
        try{
            ServerSocket socket = new ServerSocket(port);

            while(true)
            {
                // open socket
                connection = socket.accept();

                // read command
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                theIn = new BufferedReader(inputStream);
                readCommand();

                // send response
                //sendResponse();

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Reading Command
     */
    public void readCommand()
    {
        try
        {
            String str = theIn.readLine();
            String[] command = str.split(Messages.seperator);

            //Recieved Task Request
            if(command[0].equals(Messages.type_request))
            {
                System.err.println("Recieved Request From General Server");
                Gson gson = new Gson();
                Task task = gson.fromJson(command[1], Task.class);
                //Call Master
                master.assignTaskToSchuler(task);
                System.err.println("");
//                close();
            }
            //Recieved Response from Slave
            else
            {
                System.err.println("Recieved Response From Slave");
                master.updateScheduler(command[1]);
                System.err.println("");
//                close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void close()
    {
        try{
            connection.close();
            theIn.close();
            theOut.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * If String is a valid Json
     * @param test
     * @return
     */
    public boolean isJSONValid(String test)
    {
        try
        {
            Gson gson = new Gson();
            Task request = gson.fromJson(test, Task.class);
        } catch (JsonParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Send Response
     */
    public void sendResponse(String resultLocation)
    {
        try {
            theOut = new PrintStream(connection.getOutputStream());
            theOut.println(resultLocation);
            close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Test
     */
    public void test()
    {
        Task task = new Task("barcelona",0,0);
        task.completed_state=0;
        master.assignTaskToSchuler(task);
    }

    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args)
    {
        MasterServer server = new MasterServer();
        server.init();
    }


}
