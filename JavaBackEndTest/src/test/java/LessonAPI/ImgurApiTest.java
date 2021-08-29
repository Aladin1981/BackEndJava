package LessonAPI;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

class ImgurApiTest extends BaseApiTest {

    public static String commentId;
    private String currentDeleteHash;
    private String currentImageHash;

   // PojoImgur pojoImgur = new PojoImgur();


    public ImgurApiTest() throws IOException {
    }
    RequestSpecification requestSpecification =
             new RequestSpecBuilder()
                     .setBaseUri(getBaseUri())
                     .setAccept(ContentType.JSON)
                     .setContentType(ContentType.ANY)
                     .setAuth(oauth2(getToken()))
                   //.addHeader("Authorization", getToken())
                    // .log(LogDetail.ALL)
                    .build();

    ResponseSpecification responseSpecification =
            new ResponseSpecBuilder()
                    .expectStatusCode(200)
                    .expectStatusLine("HTTP/1.1 200 OK")
                    .expectContentType(ContentType.JSON)
                    .expectResponseTime(Matchers.lessThan(5000L))
                    //.expectHeader("Access-Control-Allow-Credentials", "true")
                    .expectBody("success", is(true))
                    .log(LogDetail.ALL)
                    .build();


    @BeforeEach
    void setUp() {
      //  RestAssured.baseURI = getBaseUri();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @DisplayName("Получение информации об аккаунте")
    void testGetAccountBase() {

        given()
                .spec(requestSpecification)
                .expect()
                .body("data.url", is("alalala12345"))
                .body("data.find{it.id=='153629973'}.url", equalTo("alalala12345"))
                .when()
                .get("3/account/{username}", getUserName())
                .then()
                .spec(responseSpecification)
                .extract().jsonPath().getList("data",PojoImgur.class);


    }
//    @Test
//    @DisplayName("Получение информации об аккаунте")
//    void testGetAccountBase() {
//
//        given()
//                // .spec(requestSpecification)
//                .contentType(ContentType.JSON)
//                .accept(ContentType.JSON)
//                .auth()
//                .oauth2(getToken())
//                .expect()
//                .body("data.url", is("alalala12345"))
//                .log()
//                .all()
//                //.statusCode(200)
//                .when()
//                .get("3/account/{username}", getUserName())
//                .then()
//                .statusCode(200);
//        // .spec(responseSpecification);



//    @Test
//    @DisplayName("Проверка статуса Блока Аккаунта")
//    void testAccountBlock(){
//        given()
////                .contentType(ContentType.JSON)
////                .accept(ContentType.JSON)
//                .auth()
//                .oauth2(getToken())
//                .expect()
//                .body("success", is(true))
//                .log()
//                .all()
//                .when()
//                .get("3/account/me/block")
//                .then()
//                .spec(responseSpecification);
//
//    }
//
@Test
@DisplayName("Проверка Коментов")
void testComments() {
    List<PojoImgur> comments =
            given()
                    .spec(requestSpecification)
                    .expect()
                    .body("data.find{it.image_id=='Y9H3txa'}.author", equalTo("alalala12345"))
                    .when()
                    .get("3/account/{username}/comments/", getUserName())
                    .then()
                    .spec(responseSpecification)
                    //.extract().jsonPath().getList("data.email")
                    .extract().jsonPath().getList("data", PojoImgur.class);

   assertThat(comments).extracting(PojoImgur::getId).contains(2124213461);

    }

    @Test
    @DisplayName("Пост Коммента")
    void testPostComment(){
       commentId=given()
               .spec(requestSpecification)
                .header("content-type", "multipart/form-data")
                .multiPart("image_id", "Y9H3txa")
                .multiPart("comment", "ABUabuABuabu")
                .multiPart("parent_id", "{commentId}")
                .when()
                .post("3/comment")
               .then()
               .spec(responseSpecification)
               .extract().jsonPath().getString("data.id");

       System.out.println(commentId);

    }

    @Test
    @DisplayName("Проверка Post Коментов")
    void testPostedComments() {
        given()
                .spec(requestSpecification)
                .expect()
                .body("data[0].id", is(notNullValue()), "data.comment[0]", is("ABUabuABuabu"))
                .when()
                .get("3/account/{username}/comments/", getUserName())
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Delete comment")
    void testDeleteComment(){
        given()
                .spec(requestSpecification)
                .when()
                .delete("3/comment/{commentId}",2128045905)
                .then()
                .spec(responseSpecification);

    }

    @Test
    @DisplayName("Проверка Delete Коментов")
    void testAfterDeletedComments() {
        given()
                .spec(requestSpecification)
                .expect()
                .body("data[0].id", is(not(2128032297)), "data.comment[0]", is(not("ABUabuABuabu")))
                .when()
                .get("3/account/{username}/comments/", getUserName())
                .then()
                .spec(responseSpecification);
    }




    @Test
    @DisplayName("Тест загрузки картинки")
    void testImageUpload() throws Exception {

        currentDeleteHash = given()
                .spec(requestSpecification)
                .header(new Header("content-type", "multipart/form-data"))
                .multiPart("image", new File( "./src/test/resources/res.jpg"))
                .expect()
                .body("data.id", is(notNullValue()))
                .body("data.deletehash", is(notNullValue()))
                .when()
                .post("3/upload")
                .then()
                .spec(responseSpecification)
                .extract().jsonPath().getString("data.deletehash");
    }

    @Test
    @DisplayName("Проверка get image")
    void testGetImage() {
        given()
                .spec(requestSpecification)
                .when()
                .get("3/image/{imageHash}", "ZW1Y2Tn") //imageId
                .then()
                .spec(responseSpecification);
    }

    @Test
    @DisplayName("Test Delete Image")
    void testDeleteImage(){
        given()
                .spec(requestSpecification)
                .when()
                .delete("3/image/{imageDeleteHash}", "GOLmAlBwmsXzqgr") //currentDeleteHash
                .then()
                .spec(responseSpecification);

    }
    @Test
    @DisplayName("Test Delete Image")
    void testDeleteImage2(){
        given()
                .spec(requestSpecification)
                .when()
                .delete("3/image/{imageHash}", "GOLmAlBwmsXzqgr")  //currentDeleteHash
                .then()
                .spec(responseSpecification);

    }

    @Test
    @DisplayName("Проверка get image")
    void testGetDeletedImage() {
        given()
               .spec(requestSpecification)
                .expect()
                .statusCode(404)
                .body("data.imageHash[0]", is(not("ZW1Y2Tn")))
                .when()
                .get("3/image/{imageHash}", "ZW1Y2Tn");
                //.then()
                //.spec(responseSpecification);
    }





}
