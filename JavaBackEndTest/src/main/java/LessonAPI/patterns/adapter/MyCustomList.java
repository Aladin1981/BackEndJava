package LessonAPI.patterns.adapter;

public class MyCustomList extends ListAdapter {

    @Override
    public void add(int value) {
        System.out.println("Hello world");
    }
}
