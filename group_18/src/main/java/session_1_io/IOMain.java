package session_1_io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 26th of February 2020.
 *
 * Main things to take into consideration:
 * - input / output
 * - streams (meaning)
 * - byte / character streams (unbuffered)
 * - buffered streams (difference)
 * - object serialization
 */
public class IOMain {

    // 1 byte = 8 bits
    // 1 char = 16 bits

    // Paths Windows: C:\ProgramFiles\...
    // Paths Ubuntu: /usr/lib/...
    public static void main(String... args) {

        simpleGenericCase();
        byteStreamExample();
        characterStreamExample();
        bufferedStreamExample();
        scannerExample();
        personSerialization();

        // extra NIO
        nioExample();
    }

    private static void nioExample() {

        // New IO => channels / buffers

        // API: FileChannel, ByteBuffer, CharBuffer,..

        Path path = Paths.get("buffered_example.txt");

        FileChannel fileChannel = null;
        try {
            fileChannel = FileChannel.open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = 0;

        try {
            bytesRead = fileChannel.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (bytesRead != -1) {
            System.out.println("bytes read: " + bytesRead);

            buffer.flip(); // make buffer ready for read
            byte[] destination = new byte[bytesRead];
            buffer.get(destination);

            System.out.println(new String(destination, StandardCharsets.UTF_8));
            buffer.clear(); // make buffer ready for writing

            try {
                bytesRead = fileChannel.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void personSerialization() {

        String fileName = "people.csv";

        PeopleCSVReader peopleCSVReader = new PeopleCSVReader();
        List<Person> personList = peopleCSVReader.readPeople(fileName);

        for (Person person : personList) {
            System.out.println(person);
        }

        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(new FileOutputStream("people.obj"));

            objectOutputStream.writeObject(personList.get(1));
            objectOutputStream.flush();
            objectOutputStream.close();

            // Client
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(new FileInputStream("people.obj"));

            try {
                Object o = objectInputStream.readObject();
                Person person = (Person) o;
                System.out.println(person);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void scannerExample() {

        String stringExample = "Hello, this is Java";
        Scanner scan = new Scanner(stringExample);
        System.out.println("Boolean Result: " + scan.hasNext());
        System.out.println("String: " + scan.nextLine());
        scan.close();

        System.out.println("--------------Enter your details---------------");
        Scanner in = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = in.next();
        System.out.println("Name: " + name);
        in.close();
    }

    private static void bufferedStreamExample() {

        Charset charset = Charset.forName("UTF8");

        Path path = Paths.get("buffered_example.txt");

//        try {
//            BufferedReader reader = Files.newBufferedReader(path, charset)
//
//        } catch (...)

        // Try with resources (implements autocloseable, doesn't need to be
        // manually closed.
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void characterStreamExample() {

    }

    private static void byteStreamExample() {

        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("example_byte.txt");
            out = new FileOutputStream("example_byte_out.txt");

            int val;
            while ((val = in.read()) != -1) {
                out.write(val);
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        } finally {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void simpleGenericCase() {

        List exampleList = new ArrayList<>();
        exampleList.add("abc");
        exampleList.add(1);
        exampleList.add(new Object());

        List<String> exampleListString = new ArrayList<>();
    }
}
