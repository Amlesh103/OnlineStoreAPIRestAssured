package testcases;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import payloads.Payload;
import pojo.Cart;
import pojo.Product;
import pojo.User;
import routes.Routes;
import utils.ConfigReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static payloads.Payload.*;
import static routes.Routes.*;

public class CartTests extends BaseClass {
    int cartId;
//    int userId = ConfigReader.getIntProperty("userId");


    //1) Test to get all carts
    @Test
    public void testGetAllCarts(){
        given()
                .when()
                .get(Routes.GET_ALL_CARTS)
                .then()
                .statusCode(200)
                .body("size()",notNullValue());
    }

    //2)Test to get cart by ID
    @Test
    public void testGetCartBYID(){

        given()
                .pathParam("id",ConfigReader.getIntProperty("cartId"))
                .when()
                .get(Routes.GET_CART_BY_ID)
                .then()
                .statusCode(200)
                .body("size()",notNullValue())
                .body("id",equalTo(ConfigReader.getIntProperty("cartId")));
    }

    //3) Test to get cart with limit
    @Test
    public void testGetCartWithLimit(){
        given()
                .pathParam("limit",ConfigReader.getIntProperty("limit"))
                .when()
                .get(Routes.GET_CART_WITH_LIMIT)
                .then()
                .statusCode(200)
                .body("size()",notNullValue())
                .body("size()",equalTo(ConfigReader.getIntProperty("limit")));
    }

    //4) Test to get Cart sorted
    @Test
    public void testGetCartSorted(){
        String sortValue = ConfigReader.getProperty("order");

  Response response= given()
                .pathParam("order",sortValue)
                .when()
                .get(Routes.GET_CART_SORTED)
                .then()
                .statusCode(200)
          .log().body()
                .body("size()",notNullValue())
                .extract().response();

  List<Integer> carts = response.jsonPath().getList("id");

        boolean flag = false;
        if(sortValue.equals("asc")){
            flag = isSortAscending(carts);
        }
        else if(sortValue.equals("desc")){
            flag = isSortDescending(carts);
        }

        assertThat(flag, is(true));

    }

    //5) Test to get Cart in date range

    @Test
    public void testGetCartWithinDateRange(){
        String startDate = ConfigReader.getProperty("startdate");
        String endDate = ConfigReader.getProperty("enddate");

   Response response= given()
                .pathParams("startdate", startDate)
                .pathParams("enddate", endDate)
                .when()
                .get(Routes.GET_CART_IN_DATE_RANGE)
                .then()
                .statusCode(200)
                .body("size()",greaterThan(0)) //Validate that response is not empty
                .extract().response();

   List<String> cartDates = response.jsonPath().getList("date");
 //  validateCartDatesWithinRange(cartDates, startDate, endDate);
   assertThat(validateCartDatesWithinRange(cartDates, startDate, endDate),is(true));
    }



    //6) Test to get cart for user
    @Test
    public void testGetCartForUser(){
        int userId = ConfigReader.getIntProperty("userId");

        System.out.println("********************************************");
        given()
                .pathParam("userId",userId)
                .when()
                .get(Routes.GET_USER_CARTS)
                .then()
                .statusCode(200)
                .log().body()
                .body("size()",notNullValue())
                .body("userId", everyItem(equalTo(userId)));

        System.out.println("********************************************");
    }

    //7) Add new product to Cart
    @Test
    public void testAddProductToCart(){
        int userId = ConfigReader.getIntProperty("userId");
        Cart cartPayLoad= Payload.cartPayload(userId);

     cartId=   given()
             .contentType(ContentType.JSON)
                .body(cartPayLoad)
                .when()
                .post(Routes.ADD_PRODUCT_TO_CART)
                .then()
                .statusCode(200)
             .body("size()",notNullValue())
             .extract().response().jsonPath().getInt("id");
    }

    //8) Update the cart
    @Test
    public void testUpdateProductToCart(){
        int userId = ConfigReader.getIntProperty("userId");
        Cart cartPayLoad= Payload.cartPayload(userId);
        given()
                .pathParam("id",cartId)
                .body(cartPayLoad)
                .when()
                .put(UPDATE_CART)
                .then()
                .statusCode(200);
    }

    //9) Delete the cart
    @Test
    public void testDeleteProductFromCart(){

        given()
                .pathParam("id",cartId)
                .when()
                .delete(Routes.DELETE_CART)
                .then()
                .statusCode(200);
    }


}
