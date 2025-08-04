package test;

/**
 * Multithreading
 * - Thread Creation
 * - Thread Class vs Runnable Interface
 * - Join Method
 * - Synchronized Keyword
 * - Thread Lifecycle
 * - Inter-Thread Communication
 * - Daemon Thread, Thread Priority
 * - Callable Interface
 * - Difference Between Runnable and Callable
 * - Locks and Condition
 * - Lock-Free Concurrency
 * - Thread Pools
 * - Thread Pool Executor
 * - Volatile Keyword
 * - Interrupting Threads
 */

public class ThreadCreation {
    public static void main(String[] args) throws InterruptedException {
        // creating thread using Thread class
        Thread t = new Thread("My Thread") {
            @Override
            public void run(){
                try{
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName());

                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        t.start();
        t.join();
        System.out.println(Thread.currentThread().getName());

        // now creating thread using my own class by extending thread class

        MyThreadClass myThreadClass = new MyThreadClass();
        myThreadClass.start();
        System.out.println(myThreadClass.getName());

        // now creating thread using runnable interface

        Thread runnableThread = new Thread(new Mark(), "Runnable Thread");
        runnableThread.start();
        System.out.println(runnableThread.getName());
        runnableThread.join();


        // notify

        Message msg = new Message("process it");
        Waiter waiter = new Waiter(msg);
        new Thread(waiter,"waiter").start();

        Waiter waiter1 = new Waiter(msg);
        new Thread(waiter1, "waiter1").start();

        Notifier notifier = new Notifier(msg);
        new Thread(notifier, "notifier").start();
        System.out.println("All the threads are started");
    }




    // thread can be created from the Thread class as well as Runnable interface
    // what are advantages  --
    // join method to block the main thread until complete execution
    //synchronized keyword to avoid race conditions in multithreading program
    //thread lifecycle are new, running, blocked, waiting, terminated
    //inter-thread communication are send and receive by using wait,notify and notifyAll methods
    //daemon thread is a thread that runs in the background of the application and does not prevent the application from exiting
    //thread priority is used to set the priority of a thread and can be set using setPriority method
    //Callable interface can be used to create a thread that takes a function as an argument and returns a result
    //difference between Runnable and Callable interface is that Runnable interface does not return any value while Callable interface returns a value
    //locks and conditions are used to synchronize access to shared resources in multithreading program
    //lock-free concurrency is a concurrency model that does not use locks to synchronize access to shared resources
    //thread pools are used to manage a pool of threads and can be used to create a fixed number of threads or a pool of threads that can be reused
    //thread pool executor is a thread pool that can be used to execute tasks in parallel
    //volatile keyword is used to make a variable visible to all threads
    //interrupting threads is used to stop a thread from executing
}


class MyThreadClass extends Thread {
    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}

class Mark implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread is running");
    }
}



class Waiter implements Runnable {

        private Message msg;

        public Waiter(Message m) {
            this.msg = m;
        }

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            synchronized (msg) {
                try {
                    System.out.println(name + " waiting to get notified at time:" + System.currentTimeMillis());
                    msg.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " waiter thread got notified at time:" + System.currentTimeMillis());
                //process the message now
                System.out.println(name + " processed: " + msg.getMsg());
            }
        }
    }

 class Notifier implements Runnable {

    private Message msg;

    public Notifier(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name+" started");
        try {
            Thread.sleep(1000);
            synchronized (msg) {
                msg.setMsg(name+" Notifier work done");
                msg.notify();
                // msg.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}

class Message {
        private String msg;

        public Message(String str) {
            this.msg = str;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String str) {
            this.msg = str;
        }
    }


