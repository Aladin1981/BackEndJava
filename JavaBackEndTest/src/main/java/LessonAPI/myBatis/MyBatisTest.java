package LessonAPI.myBatis;

import db.dao.CategoriesMapper;
import db.dao.ProductsMapper;
import db.model.Categories;
import db.model.CategoriesExample;
import db.model.Products;
import db.model.ProductsExample;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class MyBatisTest {

    public static void main(String[] args) {

        SqlSessionFactory sessionFactory =
                new SqlSessionFactoryBuilder()
                        .build(MyBatisTest.class.getResourceAsStream("/mybatis-config.xml"));

        SqlSession session = sessionFactory.openSession();
        ProductsMapper productsMapper = session.getMapper(ProductsMapper.class);

                                              //Get Products
      //  Products product = productsMapper.selectByPrimaryKey(8L);
        ProductsExample example = new ProductsExample();
                 example.createCriteria();
                //.andCategoryIdEqualTo(2L)
                //.andIdLessThan(60000L);
        List<Products> products = productsMapper.selectByExample(example);
               System.out.println(products);


                                                 //create Category
     CategoriesMapper categoriesMapper = session.getMapper(CategoriesMapper.class);
//        Categories category = new Categories();
//        category.setTitle("Ololo");
//        categoriesMapper.insert(category);
//        session.commit();
//        List<Categories> categories = categoriesMapper.selectByExample(new CategoriesExample());
//
//        Assertions.assertEquals("Ololo",category.getTitle());


   //     System.out.println(categories);


                                                        // Get Categories
//        CategoriesExample categoryExample = new CategoriesExample();
//        categoryExample.createCriteria();
//
//        List<Categories> cat = categoriesMapper.selectByExample(categoryExample);
//        System.out.println(cat);
//
//                                                             // Update Category
//        //CategoriesMapper categoriesMapper1 = session.getMapper(CategoriesMapper.class);
//        Categories categoryUP = new Categories();
//
//        categoryUP.setTitle("Cars");
//        categoryUP.setId(10l);
//        categoriesMapper.updateByPrimaryKey(categoryUP);
//        session.commit();
//
//        List<Categories> categories1 = categoriesMapper.selectByExample(new CategoriesExample());
//
//        Assertions.assertEquals("Cars",categoryUP.getTitle());
//        Assertions.assertEquals(10l,categoryUP.getId());
//        System.out.println(categories1);

                                                  // create product
//        Products prod = new Products();
//        prod.setTitle("Ololo product");
//        prod.setPrice(999999);
//        prod.setCategoryId(10L);
//
//        productsMapper.insert(prod);
//        session.commit();

//        System.out.println(prod);
                                           //get Product
//        ProductsExample example1 = new ProductsExample();
//       // example1.createCriteria();
//        List<Products> products1 = productsMapper.selectByExample(example1);
//        System.out.println(products1);


                                   // Update Product
        Products prod1 = new Products();
        prod1.setTitle("Tesla");
        prod1.setPrice(1000000);
        prod1.setId(11L);
        prod1.setCategoryId(10l);

        productsMapper.updateByPrimaryKey(prod1);

        Assertions.assertEquals(1000000,prod1.getPrice());
        Assertions.assertEquals("Tesla",prod1.getTitle());

        System.out.println(prod1);

                                              // Delete Product



//        productsMapper.deleteByPrimaryKey(9l);
//
//        categoriesMapper.deleteByPrimaryKey(8l);





        session.commit();

    }
}
