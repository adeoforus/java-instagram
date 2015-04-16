package parallel_programming.animation;

public class Animation implements Runnable{
    int value;

    public Animation(int val){
        value = val;
    }

    public void run(){
        for(int i1=1; i1<100; i1++){
            System.out.println(">>> i1"+ i1);

        }
    }
}
