package session_3_concurrency;

public class StepNameThread extends Thread {

    // 2, 3, 5
    private int step;
    private String name;

    public StepNameThread(int step, String name) {

        this.name = name;
        this.step = step;
    }

    @Override
    public void run() {

        long startTime = System.currentTimeMillis();

        // Try greater values here 1_000, 1_000_000
        // See how the times vary
        for (int i = 1; i < 100; i += step) {
            System.out.println(name + ": " + i);
        }

        long endTime = System.currentTimeMillis();
        long difference = endTime - startTime;

        System.out.println("Duration thread: " + name +
                ": " + difference);
    }
}
