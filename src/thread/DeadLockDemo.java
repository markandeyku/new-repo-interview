package thread;



public class DeadLockDemo {
    public static void main(String[] args) throws InterruptedException {
        SharedResource resource1 = new SharedResource("Resource 1");
        SharedResource resource2 = new SharedResource("Resource 2");
        Thread t1 = new Thread(()->{

            synchronized (resource1){
                System.out.println("Thread 1: Holding resource 1... waiting for resource 2");

                synchronized (resource2){
                    System.out.println("Thread 1: Holding resource 2...");
                }
            }
        });

        Thread t2 = new Thread(()->{

            synchronized (resource2){
                System.out.println("Thread 2: Holding resource 2... waiting for resource 1");

                synchronized (resource1){
                    System.out.println("Thread 2: Holding resource 1...");
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}


class SharedResource {

    private String name;

    public SharedResource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
