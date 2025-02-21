package payloads;

import com.github.javafaker.Faker;
//import pojo.Cart;
import pojo.*;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.*;


public class Payload {
//create payload for:
    //product
    //cart
    //user
    //login

    private static final  Faker faker = new Faker();
    private static final String categories[] = {"electronics","furniture","clothing","books","beauty"};
    private static final Random random = new Random();
    private static String username="";
    private static String password="";
    private static int userId;
    private static int id;

    //Product
   public static Product productPayload()
    {
        String name = faker.commerce().productName();
        double price = Double.parseDouble(faker.commerce().price());
        String description = faker.lorem().sentence();
        String imageURL = "https://i.pravatar.cc/100";
        String category = categories[random.nextInt(categories.length)];
        return new Product(name, price, description, imageURL, category);

    }

    //Cart

    /*
    * userId
    * date
    * product{
    * productId
    * quantity
    *}
    *
    * */


    public static Cart cartPayload(int userId){
    List<CartProduct> products = new ArrayList<>();
        userId = random.nextInt(0,100);
//        DateFormatter dateFormatter = new DateFormatter("YYYY-MM-DD");
//        String date=faker.date().future("YYYY-MM-DD");
        int productId= random.nextInt(0,100);
        int productQuantity= random.nextInt(10)+1;
        //CartProduct cart = new CartProduct(productId,productQuantity)
        products.add(new CartProduct(productId,productQuantity));
        //Date date=new Date()
        // new Date() ------> returns date like Wed Feb 19 13:17:52 IST 2025
        // We need to convert this to "yyyy-MM-dd" format in String

        SimpleDateFormat outputFormat =  new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String date = outputFormat.format(new Date()); //Converting date in String format

        return new Cart(userId, date, products);
    }




    //User


    /*
    email
    username
    password
    firstname
    lastname
    city
    street
    number
    zipcode
    geolocation.lat
    geolocation.long
    phone
     */
public static User userPayload(){
    String email = faker.internet().emailAddress();
    String username = faker.name().username();
    String password = faker.internet().password();

    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    Name name= new Name(firstName,lastName);

    String city=faker.address().cityName();
    String street = faker.address().streetName();
    int number = random.nextInt(0,100);
    String zipcode= faker.address().zipCode();

    String geolocationLat=faker.address().latitude();
    String geolocationLong=faker.address().longitude();

    Geolocation geolocation = new Geolocation(geolocationLat,geolocationLong);

    Address address = new Address(city,street,number,zipcode,geolocation);
    String phone=faker.phoneNumber().cellPhone();

    return new User( email,  username,  password,  name,  address,  phone);
}

    //Login

    /*
    * username
    * password
    *
    * */
    public static Login loginPayload() {
  String     username = faker.name().username();
   String   password = faker.internet().password();
  //    String  username = "mor_2314";
  //    String  password = "83r5^_";
        return new Login(username, password);
    }
}

