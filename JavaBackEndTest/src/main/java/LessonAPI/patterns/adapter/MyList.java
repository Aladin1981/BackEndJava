package LessonAPI.patterns.adapter;

public interface MyList {
    void add(int value);
    void add(int idx, int value);
    void remove(int value);
    int get(int idx);
    void set(int idx, int value);
}
