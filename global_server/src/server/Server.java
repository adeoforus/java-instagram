package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    protected ServerSocket socket;
    protected final int port = 9005;
    protected Socket connection;
    protected String command = new String();
    protected String responseString = new String();

    public void init(){

        System.out.println( "Launching Server: " );
        try{
            socket = new ServerSocket(port);


            while(true)
            {
                // open socket
                connection = socket.accept();
                System.out.println( "Client Connected " );

                // get input reader
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                BufferedReader input = new BufferedReader(inputStream);

                // get input
                command = input.readLine();

                // process input
                System.out.println("Command: " + command);
                responseString = command + " MC2 It Works!";

                // get output handler
                PrintStream response = new PrintStream(connection.getOutputStream());

                // send response
                response.println(responseString);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}

