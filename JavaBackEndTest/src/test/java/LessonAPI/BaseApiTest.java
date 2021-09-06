package LessonAPI;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import LessonAPI.PropertyScanner;
public abstract class BaseApiTest {

    private final String token;
    private final String baseUri;
    private final String userName;
    private final PropertyScanner scanner;
    private String currentCommentId;

    public BaseApiTest() throws IOException {
        scanner = new PropertyScanner();
        token = scanner.getProperty("imgur.auth.token");
        baseUri = scanner.getProperty("imgur.api.url");
        userName = scanner.getProperty("imgur.username");
        currentCommentId = ImgurApiTest.commentId;
    }
    public String getCurrentCommentId(){
        return currentCommentId;
    }

    public String getToken() {
        return token;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public String getUserName() {
        return userName;
    }

    public PropertyScanner getScanner() {
        return scanner;
    }

    public void assertObjects(Object expected, Object actually) throws Exception {

        Field[] fields = expected.getClass().getDeclaredFields();

        Map<String, Field> actuallyFieldsMap = Stream.of(actually.getClass().getDeclaredFields())
                .collect(Collectors.toMap(
                        Field::getName,
                        Function.identity()
                ));

        for (Field field : fields) {
            Object expectedValue = field.get(expected);
            Object actuallyValue = actuallyFieldsMap.get(field.getName()).get(actually);
            if (!(expectedValue instanceof Number) && !(expectedValue instanceof String)) {
                assertObjects(expectedValue, actuallyValue);
            }
            Assertions.assertEquals(expectedValue, actuallyValue);
        }

    }
}
