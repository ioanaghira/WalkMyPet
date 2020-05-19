package session_3_concurrency;

import java.nio.charset.Charset;
import java.util.Random;

public class CountThread implements Runnable {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public void run() {

        for (int i = 0; i <= 100; i++) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (i % 10 == 0) {
                System.out.println(randomAlphaNumeric());
            }
        }
    }

    private String generateRandomByte() {

        byte[] byteArray = new byte[7];
        new Random().nextBytes(byteArray);
        return new String(byteArray, Charset.forName("UTF-8"));
    }

    private static String randomAlphaNumeric() {

        StringBuilder builder = new StringBuilder();
        int count = 10;

        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
