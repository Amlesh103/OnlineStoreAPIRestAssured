package testcases;

import com.github.fge.jsonschema.main.JsonSchema;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import pojo.Login;
import routes.Routes;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static routes.Routes.GET_ALL_PRODUCTS;

public class SchemaValidatorTests extends BaseClass{
    @Test
    public void testProductSchemaValidation(){
        given()
                .pathParam("id", ConfigReader.getIntProperty("productId"))
                .when()
                .get(Routes.GET_PRODUCT_BY_ID)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("productSchema.json"));
    }
    @Test
    public void testUserSchemaValidation(){
        given()
                .pathParam("id",ConfigReader.getIntProperty("userId"))
                .when()
                .get(Routes.GET_USER_BY_ID)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("userSchema.json"));
    }

    @Test
    public void testLoginSchemaValidation(){

        given()
                .when()
                .then()
               .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("loginSchema.json"));
    }

    @Test
    public void testCartSchemaValidation(){
        given()
                .pathParam("id",ConfigReader.getIntProperty("cartId"))
                .when()
                .get(Routes.GET_CART_BY_ID)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("cartSchema.json"));
    }

}
