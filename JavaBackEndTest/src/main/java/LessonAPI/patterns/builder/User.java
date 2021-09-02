package LessonAPI.patterns.builder;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private final String name;
    private final String surname;
    private final String fatherName;
    private final int age;
    private final String address;
    private final String phoneNumber;
}
