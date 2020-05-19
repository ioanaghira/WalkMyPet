package session_3_concurrency;

// Singleton pattern
public class Connection {

    private static Connection instance;

    // only a single thread can enter this method
    // check without synchronized what happens
    public static synchronized Connection getInstance() {

        if (instance == null) {
            instance = new Connection();
        }

        return instance;
    }
}
