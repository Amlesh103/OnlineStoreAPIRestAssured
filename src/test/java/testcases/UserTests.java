package testcases;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import payloads.Payload;
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
import static payloads.Payload.userPayload;
import static routes.Routes.*;

public class UserTests extends BaseClass {

    User userPayload = Payload.userPayload();
    int userId;

    //1) Test to get all users
    @Test
    public void testGetAllUsers(){
        given()
                .when()
                .get(Routes.GET_ALL_USERS)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()",greaterThan(0));
    }

    //2)Test to get users by ID
    @Test
    public void testGetUsersByID(){
        given()
                .pathParam("id",ConfigReader.getIntProperty("userId"))
                .when()
                .get(Routes.GET_USER_BY_ID)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()",greaterThan(0));
    }

   //3)Test to fetch limited number of users
@Test
public void testGetUsersWithLimit(){

        given()
                .pathParam("limit",ConfigReader.getIntProperty("limit"))
                .when()
                .get(Routes.GET_USER_WITH_LIMIT)
                .then()
                .statusCode(200)
                .body("size()",greaterThan(0))
                .contentType(ContentType.JSON)
                .body("size()",equalTo(ConfigReader.getIntProperty("limit")));
    }

    //4)Test to sort users based on Ascending or Descending order
    @Test
    public void testGetUsersSorted(){
        String order = ConfigReader.getProperty("order");
        boolean flag=false;

     List<Integer> IdOrder =   given()
                .pathParam("order",order)
                .when()
                .get(Routes.GET_USER_SORTED)
                .then()
                .statusCode(200)
                .body("size()",greaterThan(0))
                .contentType(ContentType.JSON)
                .log().body()
                .extract().response().jsonPath().getList("id");
     if(order.equals("asc")){
         flag = isSortAscending(IdOrder);
     }
     else if(order.equals("desc")){
         flag = isSortDescending(IdOrder);
        }

     assertThat(flag, is(true));

    }

    //5) Test to create the users
    @Test
    public void testCreateUsers(){


    userId=  given()
              .contentType(ContentType.JSON)
              .body(userPayload)
              .when()
              .post(Routes.ADD_USER)
              .then()
              .statusCode(200)
              .body("id", notNullValue())
              .extract().jsonPath().getInt("id");
    }

    //6) Test to update the users
@Test
    public void testUpdateUsers(){
        given()
                .contentType(ContentType.JSON)
                .pathParam("id",userId)
                .body(userPayload)
                .when()
                .put(Routes.UPDATE_USER)
                .then()
                .statusCode(200)
                .body("username",equalTo(userPayload.getUsername()))
                .body("phone",equalTo(userPayload.getPhone()));
    }

//7) Test to delete users
    @Test
    public void testDeleteUsers(){
        given()
                .pathParam("id", userId)
                .when()
                .delete(Routes.DELETE_USER)
                .then()
                .statusCode(200);

    }


}
