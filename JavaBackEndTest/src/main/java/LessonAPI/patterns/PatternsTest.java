package LessonAPI.patterns;

import LessonAPI.patterns.singleton.Network;
import LessonAPI.patterns.adapter.MyCustomList;
import LessonAPI.patterns.adapter.MyList;
import LessonAPI.patterns.builder.Person;
import LessonAPI.patterns.builder.User;
import LessonAPI.patterns.fabric.ButtonFabric;
import LessonAPI.patterns.fabric.WinButtonFabric;
import LessonAPI.patterns.proxy.Calculator;
import LessonAPI.patterns.proxy.Summator;
import LessonAPI.patterns.proxy.SummatorWithLogProxy;
import LessonAPI.patterns.singleton.Network;

public class PatternsTest {
    public static void main(String[] args) {
        Network network = Network.getInstance();
        ButtonFabric winFabric = new WinButtonFabric();
        winFabric.createButton();

        Person person = Person.builder()
                .setAge(15)
                .setName("Ivan")
                .setSurname("Ivanov")
                .build();

        User user = User.builder()
                .age(12)
                .name("Ivan")
                .surname("Ivanov")
                .build();

        MyList list = new MyCustomList();
        list.add(12);

        Calculator calculator = new Summator();
        Calculator proxy = new SummatorWithLogProxy();

        calculator.calculate();
        proxy.calculate();

        Runnable r1 = () -> System.out.println("Hello");
        Runnable r2 = () -> System.out.println(" World!");
    }
}
