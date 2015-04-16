package parallel_programming.animation;

public class AnimationThread extends Thread{

    AnimationThread(){

    }

    public void run(){
        for ( int i1=1; i1<100; i1++)
            System.out.println(">>> i1 = "+i1);

    }
}
