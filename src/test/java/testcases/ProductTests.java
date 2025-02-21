package testcases;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import payloads.Payload;
import pojo.Product;
import routes.Routes;
import utils.ConfigReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static routes.Routes.BASE_URL;
import static routes.Routes.GET_ALL_PRODUCTS;

public class ProductTests extends BaseClass {
    int productId;

    //1) Test to retrieve all the products
//@Test
    public void testGetAllProducts(){
    given()
            .when()
            .get(Routes.GET_ALL_PRODUCTS)
            .then()
            .statusCode(200)
            .body("size()",greaterThan(0));

    }
//2) Test to retrieve a single product based on ID
 //   @Test
public void testGetProductByID(){

given()
        .pathParam("id", ConfigReader.getIntProperty("productId"))
        .when()
        .get(Routes.GET_PRODUCT_BY_ID)
        .then()
        .statusCode(200)
        .body("size()",greaterThan(0));
    }

//3) Test to retrieve products based on limit
//@Test
public void testGetProductsWithLimit(){

    given()
            .pathParam("limit",ConfigReader.getIntProperty("limit"))
            .when()
            .get(Routes.GET_PRODUCT_WITH_LIMIT)
            .then()
            .statusCode(200)
            .body("size()",greaterThan(0))
            .log().body();
}

//4) Test to retrieve products in ascending or descending order

boolean status= false;
   @Test
    public void testGetProductsSorted(){
       String order= ConfigReader.getProperty("order");
    Response response=given()
            .pathParam("order",order)
            .when()
            .get(Routes.GET_PRODUCT_SORTED)
            .then()
            .statusCode(200)
            .extract().response();

    List<Integer> productIds= response.jsonPath().getList("id", Integer.class);

            if (order.equals("desc")) {
            status=  isSortDescending(productIds);
            } else if (order.equals("asc")) {
            status= isSortAscending(productIds);
            }

    assertThat(status, is(true));
    }

//5) Test to get all product categories

    @Test
    public void getAllProductCategories(){

        given()
                .when()
                .get(Routes.GET_ALL_CATEGORIES)
                .then()
                .statusCode(200)
                .body("size()",greaterThan(0));
    }

    //6)Test to get product by category
    @Test
    public void testGetProductByCategory(){
        given()
                .pathParam("category","electronics")
                .when()
                .get(Routes.GET_PRODUCTS_BY_CATEGORY)
                .then()
                .statusCode(200)
                .body("size()",greaterThan(0))
                .body("category",everyItem(notNullValue()))
                .body("category",everyItem(equalTo("electronics")));
    }

    //7)Test to create products
    @Test
    public void testCreateProducts(){
     Product productPayload= Payload.productPayload();

        productId= given()
                .contentType("application/json")
                .body(productPayload)
                .when()
                .post(Routes.CREATE_PRODUCT)
                .then()
               .log().body()
                .statusCode(200)
                .body("id",notNullValue())
                .body("title",equalTo(productPayload.getTitle()))
                .extract().jsonPath().getInt("id");
        System.out.println(productId);

    }

    //8) Test to update the product
    @Test
    public void testUpdateProduct(){

        Product updatedPayload= Payload.productPayload();
        given()
                .contentType("application/json")
                .pathParam("id", ConfigReader.getIntProperty("productId"))
                .body(updatedPayload)
                .when()
                .put(Routes.UPDATE_PRODUCT)
                .then()
                .statusCode(200)
                .log().body()
                .body("title",equalTo(updatedPayload.getTitle()));
    }

    //10) Test to delete the product record
    @Test
    public void testDeleteProduct(){
        given()
                .pathParam("id",ConfigReader.getIntProperty("productId"))
                .when()
                .delete(Routes.DELETE_PRODUCT)
                .then()
                .log().body()
                .statusCode(200);
    }


}
