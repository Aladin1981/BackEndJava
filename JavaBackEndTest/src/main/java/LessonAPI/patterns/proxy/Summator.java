package LessonAPI.patterns.proxy;

public class Summator implements Calculator {
    @Override
    public int calculate() {
        return 1 + 2;
    }
}
