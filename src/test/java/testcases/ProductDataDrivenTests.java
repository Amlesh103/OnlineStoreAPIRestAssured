package testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import payloads.Payload;
import pojo.Product;
import routes.Routes;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class ProductDataDrivenTests extends BaseClass{
    int productId;
@Test(dataProvider = "jsonDataProvider", dataProviderClass = utils.DataProviders.class)
    public void testAddNewProduct(Map<String, String> data) {
 //   Product productPayload= Payload.productPayload();

    String title = data.get("title");
    double price = Double.parseDouble(data.get("price"));
    String description = data.get("description");
    String image= data.get("image");
    String category = data.get("category");

    Product productPayload= new Product(title,price,description,image,category);


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
@Test
public void testdeleteProduct(){
   given()
           .pathParam("id",productId)
           .when()
           .delete(Routes.DELETE_PRODUCT)
           .then()
           .statusCode(200);
}
}
