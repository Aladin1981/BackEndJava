package LessonAPI.serialization;

import java.util.NoSuchElementException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.node.NumericNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import org.jsfr.json.JacksonParser;
import org.jsfr.json.JsonSurfer;
import org.jsfr.json.provider.JacksonProvider;
import LessonAPI.patterns.builder.User;

public class SerializationTest {
    public static void main(String[] args) {
        User user = User.builder()
                .age(12)
                .name("Ivan")
                .surname("Ivanov")
                .build();

        Gson gson = new Gson();
        String json = gson.toJson(user);
        System.out.println(json);

        User fromJson = gson.fromJson(json, User.class);

        System.out.println(fromJson);

        String user2 = "{\"name\":\"Ivan\",\"surname\":\"Ivanov\",\"age\":12}";

        User user3 = gson.fromJson(user2, User.class);
        System.out.println(user3);

        String data = "{\n" +
                "    \"data\": {\n" +
                "        \"id\": 153514053,\n" +
                "        \"url\": \"levinmk23\",\n" +
                "        \"bio\": null,\n" +
                "        \"avatar\": \"https://imgur.com/user/levinmk23/avatar?maxwidth=290\",\n" +
                "        \"avatar_name\": \"default/L\",\n" +
                "        \"cover\": \"https://imgur.com/user/levinmk23/cover?maxwidth=2560\",\n" +
                "        \"cover_name\": \"default/1-space\",\n" +
                "        \"reputation\": -1,\n" +
                "        \"reputation_name\": \"Neutral\",\n" +
                "        \"created\": 1629291233,\n" +
                "        \"pro_expiration\": false,\n" +
                "        \"user_follow\": {\n" +
                "            \"status\": false\n" +
                "        },\n" +
                "        \"is_blocked\": false\n" +
                "    },\n" +
                "    \"success\": true,\n" +
                "    \"status\": 200\n" +
                "}";

        new JsonSurfer(new JacksonParser(new JsonFactory()), new JacksonProvider())
                .configBuilder()
                .bind("$.data.id", (val, con) -> System.out.println(val))
                .bind("$.data.avatar", (val, con) -> System.out.println(val))
                .buildAndSurf(data);

    }
}
