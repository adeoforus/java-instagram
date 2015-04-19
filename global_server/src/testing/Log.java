package testing;

import config.Parameter;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private PrintWriter out;

    public Log(){
        setupDirectories();
        try{
            out = new PrintWriter(new BufferedWriter(new FileWriter(Parameter.log_dir+Parameter.log_file, true)));
        }
        catch (IOException e) {
            System.err.println("Could not setup Print Writer in Logger ");
        }
    }

    /**
     * Setting up required directoreis
     */
    public void setupDirectories(){
        //  Output Dir
        File root = new File(Parameter.log_dir);
        if(!root.isDirectory()){
            root.mkdir();
        }
    }

    /**
     * Empty Log file contents
     */
    public void empty(){
        try {
            PrintWriter writer = new PrintWriter(Parameter.log_dir + Parameter.log_file);
            writer.print("");
            writer.close();
        }
        catch (IOException e){

        }
    }

    /**
     * Specifying new sequence of Entries
     */
    public void newEntry(){
        out.println("--------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        out.println(dateFormat.format(date));
        out.flush();
    }

    /**
     * Printing a message to log file
     * @param message
     */
    public void log(String message){
        out.println(message);
        out.flush();
    }

}
