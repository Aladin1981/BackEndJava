package LessonAPI.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import LessonAPI.retrofit.dto.ProductDto;

public class JDBCTest {

    private static final String SQL = "" +
            "SELECT P.ID    AS PRODUCT_ID, " +
            "       P.TITLE AS PRODUCT_TITLE, " +
            "       PRICE, " +
            "       C.TITLE AS CATEGORY_TITLE " +
            "FROM PRODUCTS P " +
            "         JOIN CATEGORIES C ON P.CATEGORY_ID = C.ID";

    private static final String JDBC_URL = "jdbc:h2:C:/java/JavaBackendTest/geekdb";

    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "sa");
        connectionProperties.put("password", "");
        Connection connection = DriverManager.getConnection(JDBC_URL, connectionProperties);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        List<ProductDto> products = new ArrayList<>();
        while (resultSet.next()) {
            ProductDto dto = new ProductDto();
            dto.setId(resultSet.getLong("PRODUCT_ID"));
            dto.setTitle(resultSet.getString("PRODUCT_TITLE"));
            dto.setPrice(resultSet.getLong("PRICE"));
            dto.setCategoryTitle(resultSet.getString("CATEGORY_TITLE"));
            products.add(dto);
        }
        System.out.println(products);
        // connection.close();
    }
}