package Lesson5Api;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import LessonAPI.retrofit.api.MiniMarketApi;
import LessonAPI.retrofit.dto.Category;
import LessonAPI.retrofit.dto.ProductDto;
import LessonAPI.retrofit.utils.RetrofitGetter;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.junit.Assert.*;

public class ApiTest {


   private static Long currentId;
    private final MiniMarketApi api;

    public ApiTest() throws IOException {
        Retrofit retrofit = new RetrofitGetter().getInstance();
        api = retrofit.create(MiniMarketApi.class);
    }
    @Test
    void testGetProducts() throws IOException {

        ProductDto dto = ProductDto.builder()
                .build();
        api.getProducts().execute();
    }

    @Test
    void testCreateProduct() throws IOException {

        ProductDto dto = ProductDto.builder()
                .title("Cola")
                .categoryTitle(Category.FOOD.getTitle())
                .price(150L)
                .build();

        Response<ProductDto> response = api.createProduct(dto).execute();
        currentId = response.body().getId();

        ProductDto actually = api.getProduct(currentId).execute().body();

        assertEquals(dto.getTitle(), actually.getTitle());
        assertEquals(dto.getPrice(), actually.getPrice());
        assertEquals(dto.getCategoryTitle(), actually.getCategoryTitle());
        System.out.println(currentId);

       // api.deleteProduct(id).execute();
    }

    @Test
    void testGetProduct() throws IOException {

        ProductDto dto = ProductDto.builder()
                .build();
        Response<ProductDto> response = api.getProduct(6).execute();

        ProductDto actually = api.getProduct(6).execute().body();

        assertEquals("Cola",actually.getTitle());
        assertEquals("Food", actually.getCategoryTitle());
       // assertEquals(2L, actually.getId());
    }
@Test
void testUpdateProduct() throws IOException {
    ProductDto dto = ProductDto.builder()
            .id(6l)
            .title("Cola")
            .categoryTitle(Category.FOOD.getTitle())
            .price(5500L)
            .build();
    Response<ProductDto> response = api.updateProduct(dto).execute();
    ProductDto actually = api.getProduct(6).execute().body();

     assertEquals("Cola",actually.getTitle());
    assertEquals(dto.getPrice(), actually.getPrice());
    // assertEquals(2L, actually.getId());
}

    @Test
    void testDeleteProduct() throws IOException {

        ProductDto dto = ProductDto.builder()
                  .build();
        Response<ProductDto> response = api.getProduct(6).execute();
        ProductDto actually = api.getProduct(6).execute().body();
        assertTrue(response.isSuccessful());
    }

    @Test
    void testDeleteNotExistingProduct() throws IOException {

        ProductDto dto = ProductDto.builder()
                .build();
        Response<ProductDto> response = api.getProduct(16).execute();
        assertFalse(response.isSuccessful());
    }

    @Test
    void testGetAfterDelete() throws IOException {

        ProductDto dto = ProductDto.builder()
                .build();
        Response<ProductDto> response = api.getProduct(7).execute();
        ProductDto actually = api.getProduct(7).execute().body();
      //  assertThrows()
         assertFalse(response.isSuccessful());

    }


}
