package test;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProducerConsumerProblem {
    public static void main(String args[]){
        //Creating shared object
        BlockingQueue sharedQueue = new LinkedBlockingQueue();
        BlockingQueue<Integer> sharedQueue1 = new ArrayBlockingQueue<>(10);

        //Creating Producer and Consumer Thread
        Thread prod = new Thread(new Producer(sharedQueue1));
        Thread cons = new Thread(new Consumer(sharedQueue));

        //Starting producer and Consumer thread
        prod.start();
        cons.start();
        Callable c = ()->{

            return null;
        };
        Runnable r = ()->{
            System.out.println("jkdk");
        };
        Thread t = new Thread(() -> System.out.println("Thread is running"));

        ExecutorService e = Executors.newSingleThreadExecutor();
        e.submit(c);
        e.submit(r);
        e.submit(t);
        e.execute(t);
        e.execute(r);

    }

}

//Producer Class in java
class Producer implements Runnable {

    private final BlockingQueue sharedQueue;

    public Producer(BlockingQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        for(int i=0; i<100; i++){
            try {
                System.out.println("Produced: " + i);
                Thread.sleep(10);
                sharedQueue.put(i);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

//Consumer Class in Java
class Consumer implements Runnable{

    private final BlockingQueue sharedQueue;

    public Consumer (BlockingQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }

    @Override
    public void run() {
        while(true){
            try {
                System.out.println("Consumed: "+ sharedQueue.take());
            } catch (InterruptedException ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}


class person{
    int a;
    double b;

    person(int a){
        this.a = a;
    }

    person(double b){
        this.b = b;
        new person(1);
    }
}