package session_3_concurrency;

public class MyThread extends Thread {

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName());
        System.out.println("My Thread");
    }
}
