package LessonAPI;

import java.io.File;
import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import patterns.builder.User;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class ImgurApiTest extends BaseApiTest {

    private String currentDeleteHash;

    public ImgurApiTest() throws IOException {
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = getBaseUri();
    }


    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName("Получение информации об аккаунте")
    void testGetAccountBase() {

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("data.url", is("alalala12345"))
                .log()
                .all()
                .statusCode(200)
                .when()
                .get("3/account/{username}", getUserName());
    }

    @Test
    @DisplayName("Проверка статуса Блока Аккаунта")
    void testAccountBlock(){
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("success", is(true))
                .log()
                .all()
                .statusCode(200)
                .when()
                .get("3/account/me/block");

    }

    @Test
    @DisplayName("Проверка Коментов")
    void testComments(){
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .expect()
               //.body("data[0].id", is(2124213461),"data.comment[0]", is("I'm a giraffe!"))
                .log()
                .all()
                .statusCode(200)
                .when()
                .get("3/account/{username}/comments/",getUserName());
    }

    @Test
    @DisplayName("Пост Коммента")
    void testPostComment(){
        given()
                .auth()
                .oauth2(getToken())
                .when()
                .header("content-type", "multipart/form-data")
                .multiPart("image_id", "Y9H3txa")
                .multiPart("comment", "ABUabuABuabu")
                .expect()
                //.body("success", is(true))
                .log()
                .all()
                .statusCode(200)
                .when()
                .post("3/comment");


    }

    @Test
    @DisplayName("Проверка Post Коментов")
    void testPostedComments() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("data[0].id", is(notNullValue()), "data.comment[0]", is("ABUabuABuabu"))
                .log()
                .all()
                .statusCode(200)
                .when()
                .get("3/account/{username}/comments/", getUserName());
    }

               Integer commentId = 2127053077;
    @Test
    @DisplayName("Delete comment")
    void testDeleteComment(){
        given()
                .auth()
                .oauth2(getToken())
                .when()
//                .header("content-type", "multipart/form-data")
//                .multiPart("image_id", "Y9H3txa")
//                .multiPart("comment", "ABUabuABuabu")
                .expect()
                //.body("success", is(true))
                .log()
                .all()
                .statusCode(200)
                .when()
                .delete("3/comment/{commentId}", 2127042129);

    }

    @Test
    @DisplayName("Проверка Delete Коментов")
    void testAfterDeletedComments() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .expect()
                .body("data[0].id", is(notNullValue()), "data.comment[0]", is(not("ABUabuABuabu")))
                .log()
                .all()
                .statusCode(200)
                .when()
                .get("3/account/{username}/comments/", getUserName());
    }




    @Test
    @DisplayName("Тест загрузки картинки")
    void testImageUpload() throws Exception {

        currentDeleteHash = given()
                .auth()
                .oauth2(getToken())
                .when()
                .header(new Header("content-type", "multipart/form-data"))
                .multiPart("image", new File( "./src/test/resources/res.jpg"))
                .expect()
                .statusCode(200)
                .body("data.id", is(notNullValue()))
                .body("data.deletehash", is(notNullValue()))
                .log()
                .all()
                .when()
                .post("3/upload")
                .jsonPath()
                .getString("data.deletehash");
        System.out.println(currentDeleteHash);
    }

    @Test
    @DisplayName("Проверка get image")
    void testGetImage() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth()
                .oauth2(getToken())
                .expect()
                .log()
                .all()
                .statusCode(200)
                .when()
                .get("3/image/{imageHash}", "l6VfMPTbK462rtS");
    }

    @Test
    @DisplayName("Test Delete Image")
    void testDeleteImage(){
        given()
                .auth()
                .oauth2(getToken())
                .when()
                .expect()
                //.body("success", is(true))
                .log()
                .all()
                .statusCode(200)
                .when()
                .delete("3/image/{imageDeleteHash}", "RzaWGAy1nGLKRtL"); //currentDeleteHash

    }





}
