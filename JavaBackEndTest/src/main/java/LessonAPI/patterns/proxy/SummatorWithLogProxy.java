package LessonAPI.patterns.proxy;

public class SummatorWithLogProxy extends Summator {

    @Override
    public int calculate() {
        System.out.println("Start calculation");
        int result = super.calculate();
        System.out.println("Finish calculation...");
        System.out.println("Result: " + result);
        return result;
    }
}
