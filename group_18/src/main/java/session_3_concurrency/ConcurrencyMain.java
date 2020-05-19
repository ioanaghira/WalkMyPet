package session_3_concurrency;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ConcurrencyMain {

    public static void main(String[] args) {

        //System.out.println(Thread.currentThread().getName());

        //   exampleThread();

        //     exampleRunnable();

        // stepProblem();
        //  countProblem();

        //  singletonProblem();

        //  sequentialVsParallelStreams();
        // joinThreads();

//        producerConsumerSynchronized();
//        producerConsumerNonSync();

//        deadlockExample();

//        starvationExample();

//        atomicNonAtomicElements();
    }

    private static void atomicNonAtomicElements() {

        // Atomic (thread safe)
        AtomicInteger randomAtomicNumber = new AtomicInteger(1);

        // Non atomic (not thread safe)
        final Integer[] randomNumber = {1};

        // Start 1000 threads and modify the values by incrementing with 1
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                randomNumber[0]++;
                randomAtomicNumber.getAndAdd(1);
            }).start();
        }

        // Never the expected value: 1001
        System.out.println(randomNumber[0]);
        // Always the expected value: 1001
        System.out.println(randomAtomicNumber.get());
    }

    /**
     * A thread blocked (starved) forever by another thread which doesn't release the lock.
     */
    private static void starvationExample() {

        LinkedList<String> list = new LinkedList<>();

        Consumer consumer = new Consumer(list);
        Producer producer = new Producer(list);

        consumer.consumeStarveThread();
        // The thread will wait for the "list" lock to be release by the Consumer thread forever.
        producer.produceSynchronized();
    }

    /**
     * In the execution of Thread1 it locks resource1 and then tries to acquire lock for resource2
     * In the execution of Thread2 it locks resource2 and then tries to acquire lock for resource1
     * <p>
     * Lock for resource1 never released
     * Lock for resource2 never released
     */
    private static void deadlockExample() {

        final String resource1 = "resource1";
        final String resource2 = "resource2";

        Thread t1 = new Thread(() -> {

            synchronized (resource1) {
                System.out.println("Thread1: locked resource1");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) {
                    System.out.println("Thread1: locked resource2");
                }
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread2: locked resource2");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource1) {
                    System.out.println("Thread2: locked resource1");
                }
            }
        });

        t1.start();
        t2.start();
    }

    /**
     * Multiple operations on the same shared resource:
     * <p>
     * Should throw: Exception in thread "Thread-0" java.util.ConcurrentModificationException
     */
    private static void producerConsumerNonSync() {

        LinkedList<String> list = new LinkedList<>();

        Consumer consumer = new Consumer(list);
        Producer producer = new Producer(list);

        consumer.consumeNonSync();
        producer.produceNonSync();
    }

    /**
     * Shared resource: the linked list (Queue based) -> has the .poll option to always GET the first element introduced
     * <p>
     * Check Queue vs Stack.
     */
    private static void producerConsumerSynchronized() {

        LinkedList<String> list = new LinkedList<>();

        Consumer consumer = new Consumer(list);
        Producer producer = new Producer(list);

        consumer.consumeSynchronized();
        producer.produceSynchronized();
    }

    /**
     * StepNameThread st2 waits for StepNameThread st1 until he finishes the run() block logic.
     */
    private static void joinThreads() {

        StepNameThread st1 = new StepNameThread(2, "thread1");
        st1.start();

        try {
            st1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        StepNameThread st2 = new StepNameThread(3, "thread2");
        st2.start();
    }

    /**
     * Sequential: 1 thread started -> 10+ seconds
     * Parallel: 5 (available) threads started -> instant
     * <p>
     * Useful for logic which needs to be fast (ex: PDF generation for 10_000 people).
     */
    private static void sequentialVsParallelStreams() {

        String[] randomStrings = {"1", "2", "3", "4", "5"};

        System.out.println("Sequential...");
        run(Arrays.stream(randomStrings).sequential());

        System.out.println("Parallel...");
        run(Arrays.stream(randomStrings).parallel());
    }

    private static void run(Stream<String> stream) {

        stream.forEach(streamElement -> {
            System.out.println(LocalTime.now() + " - time" + streamElement +
                    " - thread: " + Thread.currentThread().getName());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * The requirement: a unique Connection instance for any call.
     * <p>
     * Not a problem if a single thread is calling it (through multiple methods)
     * A problem if multiple threads call it, there is a possibility that multiple new instances are created
     */
    private static void singletonProblem() {

//        Connection connection = new Connection();

//        System.out.println(Connection.getInstance().hashCode());
//        System.out.println(Connection.getInstance().hashCode());

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                // The logic from run() method.
                Connection connection = Connection.getInstance();
                System.out.println(connection.hashCode());
            }).start();
        }
    }

    private static void countProblem() {

        CountThread countThread = new CountThread();
        Thread t = new Thread(countThread);
        t.start();
    }

    private static void stepProblem() {

        // 3 threads with step and name
        StepNameThread t1 = new StepNameThread(2, "thread1");
        StepNameThread t2 = new StepNameThread(3, "thread2");
        StepNameThread t3 = new StepNameThread(5, "thread3");

        t1.start();
        t2.start();
        t3.start();
    }

    private static void exampleRunnable() {

        MyRunnable runnableInstance = new MyRunnable();
        Thread t = new Thread(runnableInstance);
        t.start();
    }

    private static void exampleThread() {

        MyThread t = new MyThread();
        // You never call .run() method directly.
        t.start();
    }
}
