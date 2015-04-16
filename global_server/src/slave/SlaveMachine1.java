package slave;

import com.google.gson.Gson;
import config.Messages;
import mapper_reducer.Mapper;
import task.Task;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class SlaveMachine1 extends Slave{

    private Task task;

    public SlaveMachine1(int id){
        super(id);
        init();
    }

    /**
     * Setting up environment variables
     */
    public void init()
    {
    }

    /**
     * Setting up Server Socket
     */
    public void initServer(){
        System.out.print("Slave Server " + id + " : ");
        try{
            ServerSocket socket = new ServerSocket(port);

            while(true)
            {
                // open socket
                Socket connection = socket.accept();
                System.out.println( "Master Command Received");

                // read command
                InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
                theIn = new BufferedReader(inputStream);
                readCommand();

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
        try
        {
            String command = theIn.readLine();
            System.out.println("Slave " + id + " : [Command] " + command);

            //Do Master's Orders
            Gson gson = new Gson();
            this.task = gson.fromJson(command, Task.class);
            System.out.println("Slave " + id + " : [Format] " + task.toString());

            executeCommand();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Send Response
     */
    public void sendResponse()
    {
        String responseString = "Successfully Recieved Command at slave_"+id ;
        theOut.println(responseString);
    }


    /**
     * Executing Command
     */
    private void executeCommand()
    {

        switch (task.completed_state) {

            case Messages.action_select: // 1.Map_count 2.Map_Select
                //Mapper_Count
                //read all files in DATA_IN_FOLDER and store result in MAP_FOLDER
                Mapper map_count = new Mapper(task.task_id, task.DATA_IN_FOLDER, task.MAP_FOLDER);
                map_count.mapCount();
                //Mapper_Select
                //read all files in MAP_FOLDER and store result in DATA_OUT_FOLDER
                Mapper map_select = new Mapper(task.task_id, task.MAP_FOLDER, task.DATA_OUT_FOLDER);
                map_select.mapSelect(task.tag); // select by given tag
                break;

            case Messages.action_sort_like:  // Sort by likes
                //Mapper_Sort_Like
                //read file in folder DATA_OUT_FOLDER with name == task_id and store result in DATA_OUT_FOLDER
                Mapper map_sort_like = new Mapper(task.task_id, task.DATA_OUT_FOLDER, task.DATA_OUT_FOLDER);
                map_sort_like.sortByLike();
                break;

            case Messages.action_sort_time: // Sort by created time
                //Mapper_sort_time
                //read file in folder DATA_OUT_FOLDER with name == task_id and store result in DATA_OUT_FOLDER
                Mapper map_sort_time = new Mapper(task.task_id, task.DATA_OUT_FOLDER, task.DATA_OUT_FOLDER);
                map_sort_time.sortByCreatedTime();
                break;

            default: // Send error if slave do not have case for given task
                Gson gson = new Gson();
                ResponseToMaster response = new ResponseToMaster(id, Messages.response_error, gson.toJson(task));
                response.init();
        }

        Gson gson = new Gson();
        task.result = task.DATA_OUT_FOLDER + task.task_id;
        ResponseToMaster response = new ResponseToMaster(id, Messages.response_OK, gson.toJson(task));
        response.init();

    }


    public static void main(String[] args) {
        //Initialising the Slave Server
        int slave_id = 0;
        SlaveMachine1 slave = new SlaveMachine1(slave_id);
        slave.initServer();

    }
}