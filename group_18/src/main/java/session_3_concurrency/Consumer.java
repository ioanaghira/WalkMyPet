package session_3_concurrency;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;

public class Consumer {

    private LinkedList<String> list;

    public Consumer(LinkedList<String> list) {

        this.list = list;
    }

    /**
     * A thread that always consumes from the list.
     */
    public void consumeSynchronized() {

        new Thread(() -> {

            // Runs forever
            while (true) {

                try {
                    synchronized (list) {
                        System.out.println("Consumed: " + list.poll());
                    }
                    System.out.println("List content: " + list);

                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * No sleep added. Synchronized before while(true). It never releases the lock for the Producer thread.
     */
    public void consumeStarveThread() {

        new Thread(() -> {
            synchronized (list) {
                // Runs forever
                while (true) {
                    System.out.println("List content: " + list);
                    System.out.println("Consumed: " + list.poll());
                }
            }
        }).start();
    }

    /**
     * No sync on shared resource.
     */
    public void consumeNonSync() {

        new Thread(() -> {

            // Runs forever
            while (true) {
                try {
                    System.out.println("Consumed: " + list.poll());
                    System.out.println("List content: " + list);
                } catch (ConcurrentModificationException ex) {
                    System.out.println("Consumer modifying same resource as Producer");
                    // Exit the application
                    System.exit(1);
                }
            }
        }).start();
    }
}
