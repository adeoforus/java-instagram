package testing;

import config.Parameter;
import server.Client;

/**
 * Class Testing the Project Architecture
 */
public class Test {
    private Log log;

    /**
     * Constructor
     * Initiating All Tests
     */
    public Test(){
        this.log = new Log();
        log.empty();

        requestWithSort();
        //requestWithCloud();
        //multipleClientRequests();

    }


    public void requestWithSort() {
        log.newEntry();
        log.log("Request with Sort");

        Client client = new Client("barcelona",1,0);
        client.init();

    }

    //TODO
    public void requestWithCloud() {
        log.newEntry();
        log.log("Request with Cloud");
    }


    //TODO
    public void multipleClientRequests(){
        log.newEntry();

        //Without Sort or Cloud
        log.log("Multiple Client Requests");
        Client client1 = new Client("barcelona",0,0);
        client1.init();
        Client client2 = new Client("barcelona",0,0);
        client2.init();
        Client client3 = new Client("barcelona",0, 0);
        client3.init();
    }

    /**
     * Main Funtion
     * @param args
     */
    public static void main(String[] args){
        Parameter.print("Started Architecture Testing");
        Test tester = new Test();
    }

}
