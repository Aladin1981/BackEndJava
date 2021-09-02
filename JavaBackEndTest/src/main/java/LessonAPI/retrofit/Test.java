package LessonAPI.retrofit;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import LessonAPI.retrofit.api.MiniMarketApi;
import LessonAPI.retrofit.dto.Category;
import LessonAPI.retrofit.dto.ProductDto;
import LessonAPI.retrofit.utils.RetrofitGetter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Test {

    public static void main(String[] args) throws IOException {
        RetrofitGetter getter = new RetrofitGetter();

        Retrofit retrofit = getter.getInstance();

        MiniMarketApi marketApi = retrofit.create(MiniMarketApi.class);

        Response<List<ProductDto>> productsResp = marketApi.getProducts().execute();
        List<ProductDto> products = productsResp.body();

        System.out.println(products);

        ProductDto newProduct = ProductDto.builder()
                //.id(6L)
                .title("Honey")
                .price(227L)
                .categoryTitle(Category.FOOD.getTitle())
                .build();

        marketApi.updateProduct(newProduct).execute();

//        System.out.println("New product created!");
//        Call<ResponseBody> productsCall = marketApi.getProducts();
//        Response<ResponseBody> productsResponse = productsCall.execute();// block
//
//        System.out.println(productsResponse);
//        System.out.println(productsResponse.body());
//        System.out.println(productsResponse.body().string());
//
//        productsCall.enqueue(new Callback<>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
//
//            }
//        });

    }
}
