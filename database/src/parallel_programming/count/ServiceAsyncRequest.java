package parallel_programming.count;

public class ServiceAsyncRequest
{
    private static final int ARRAY_SIZE = 100000;
    private static final int NUMBER_OF_SERVERS = 100;
    public static void main(String[] args) {
// start time
        long startTime = System.currentTimeMillis();
// array creation, init with random boolean values
        boolean[] array = new boolean[ARRAY_SIZE];
        for(int i = 0; i < ARRAY_SIZE; i++) {
            if(Math.random() < 0.1) array[i] = true;
            else array[i] = false;
        }

        for(int k=0;k<3;k++){
// creation of array for service objects and threads
            Service[] service = new Service[NUMBER_OF_SERVERS];
            Thread[] serverThread = new Thread[NUMBER_OF_SERVERS];
            int start = 0;
            int end;
            int howMany = ARRAY_SIZE / NUMBER_OF_SERVERS;
            // creation of services and threads
            for(int i = 0; i < NUMBER_OF_SERVERS; i++) {
                end = start + howMany - 1;
                service[i] = new Service(array, start, end);
                serverThread[i] = new Thread(service[i]);
                serverThread[i].start(); // start thread i
                start = end + 1;
            }
// wait for termination of each service (thread)
            try {
                for(int i = 0; i < NUMBER_OF_SERVERS; i++)
                    serverThread[i].join();
            } catch(InterruptedException e) {
            }
// accumulate service results
            int result = 0;
            for(int i = 0; i < NUMBER_OF_SERVERS; i++) {
                result += service[i].getResult();
            }
// end time
            long endTime = System.currentTimeMillis();
            float time = (endTime-startTime) / 1000.0f;
            System.out.println("computation time: " + time);
            // print result
            System.out.println("result: " + result);


            startTime = System.currentTimeMillis();
            int sum=0;
            for(int i=0;i < ARRAY_SIZE ; i++){
                if(array[i])
                    sum++;
            }
            endTime = System.currentTimeMillis();
            time = (endTime-startTime) / 1000.0f;
            System.out.println("Exhaustive computation time: " + time);
            // print result
            System.out.println("result: " + sum);

        }

    }

}