package LessonAPI.patterns.singleton;

// Singleton
public class Network {

    private static volatile Network instance;

    private Network() {
    }

    public static Network getInstance() {

        if (instance == null) {
            instance = new Network();
        }

        return instance;
    }

}
