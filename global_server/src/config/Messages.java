package config;

/**
 * List of Predefined Messages
 */
public class Messages {

    // Slave Status
    public static String status_avaiable = "free";
    public static String status_not_avaiable = "busy";
    public static String status_error = "error";

    // Slave Response
    public static String response_OK = "done";
    public static String response_error = "error";

    //Task Status
    public static String task_pending = "pending";
    public static String task_completed = "complete";

    //Seperators
    public static String seperator = "/";
    public static String seperator_slave = ";";
    public static String seperator_client = ";";


    //Request Type
    public static String type_request = "to_master";
    public static String type_request_client = "from_client";
    public static String type_response = "from_slave";

    //Action Type for Slave
    public static final int action_select = 0;
    public static final int action_sort_like = 1;
    public static final int action_sort_time = 2;
}
