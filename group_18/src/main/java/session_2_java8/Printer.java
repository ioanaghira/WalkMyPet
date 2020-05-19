package session_2_java8;

public interface Printer {

    void test();

    // Java 8
    default void print() {
        System.out.println("Print");
    }

    // CRUD (create, read, update, delete)
}
