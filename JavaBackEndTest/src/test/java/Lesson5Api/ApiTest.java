package Lesson5Api;

import LessonAPI.BaseApiTest;
import LessonAPI.myBatis.MyBatisTest;
import LessonAPI.retrofit.api.MiniMarketApi;
import LessonAPI.retrofit.dto.Category;
import LessonAPI.retrofit.dto.ProductDto;
import LessonAPI.retrofit.utils.RetrofitGetter;
import db.dao.ProductsMapper;
import db.model.Products;
import db.model.ProductsExample;
import okhttp3.Headers;
import org.apache.http.HttpStatus;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class ApiTest extends BaseApiTest {


   private static Long currentId;
    private final MiniMarketApi api;

    SqlSessionFactory sessionFactory =
            new SqlSessionFactoryBuilder()
                    .build(MyBatisTest.class.getResourceAsStream("/mybatis-config.xml"));
    SqlSession session = sessionFactory.openSession();
    ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);

    public ApiTest() throws IOException {
        Retrofit retrofit = new RetrofitGetter().getInstance();
        api = retrofit.create(MiniMarketApi.class);
    }
    @Test
    void testGetProducts() throws IOException {

        ProductDto dto = ProductDto.builder()
                .build();
        api.getProducts().execute();


        //Products product = productsMapper.selectByPrimaryKey(1L);
        ProductsExample example = new ProductsExample();
        example.createCriteria()
                .andCategoryIdEqualTo(2L)
                .andIdLessThan(60000L);
        List<Products> products = productsMapper.selectByExample(example);
        System.out.println(products);


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

        assertEquals(HttpStatus.SC_OK, response);
        Assertions.assertEquals(HttpStatus.SC_OK,response.headers());

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
