package testcases;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import payloads.Payload;
import pojo.Login;
import routes.Routes;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static payloads.Payload.loginPayload;

public class LoginTests extends BaseClass{

//1) Test using invalid login
@Test
    public void testInvalidLoginAuth(){

    Login authPayload = Payload.loginPayload();
    given()
            .contentType(ContentType.JSON)
            .body(authPayload)
            .when()
            .post(Routes.LOGIN)
            .then()
            .log().body()
            .statusCode(401)
            .body(equalTo("username or password is incorrect"));
    }

    //2) Test using Valid login
    @Test
    public void testValidLogin(){
    /*
     "username": "mor_2314",
     "password": "83r5^_"
    */
     String username = ConfigReader.getProperty("username");
     String password= ConfigReader.getProperty("password");

     Login login = new Login(username, password);

    given()
            .contentType(ContentType.JSON)
            .body(login)
            .when()
            .post(Routes.LOGIN)
            .then()
            .log().body()
            .statusCode(200)
            .body("token", notNullValue());
    }
}
