package session_3_concurrency;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

public class Producer {

    private List<String> list;

    public Producer(List<String> list) {

        this.list = list;
    }

    /**
     * A thread that produces elements in the list.
     */
    public void produceSynchronized() {

        new Thread(() -> {

            // P0, P1, P2, P3...P9
            for (int i = 0; i < 10; i++) {

//                String payload = i + i;
                String payload = "P" + i;
//                String payload = i + "P";

                synchronized (list) {
                    list.add(payload);
                }

                System.out.println("Produced: " + payload);
                System.out.println("List content: " + list);

                try {
                    Thread.sleep(500 + new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * No sync on shared resource.
     */
    public void produceNonSync() {

        new Thread(() -> {

            int i = 0;
            while (true) {

                String payload = "P" + i++;
                System.out.println("Produced: " + payload);

                try {
                    list.add(payload);
                    System.out.println("List content: " + list);
                } catch (ConcurrentModificationException ex) {
                    System.out.println("Producer modifying same resource as Consumer");
                    // Exit the application
                    System.exit(1);
                }
            }
        }).start();
    }
}
