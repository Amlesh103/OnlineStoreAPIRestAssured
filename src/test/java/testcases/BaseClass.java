package testcases;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import routes.Routes;
import utils.ConfigReader;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseClass {

    ConfigReader configReader;

    //For logging
    RequestLoggingFilter requestLoggingFilter;
    ResponseLoggingFilter responseLoggingFilter;

@BeforeClass
    public void setup(){
    RestAssured.baseURI = Routes.BASE_URL;
    configReader = new ConfigReader();

    FileOutputStream fos = null;
    try {
        fos = new FileOutputStream(".\\logs\\test_logging.txt");
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
    PrintStream log = new PrintStream(fos, true);
    requestLoggingFilter = new RequestLoggingFilter(log);
    responseLoggingFilter = new ResponseLoggingFilter(log);
    RestAssured.filters(requestLoggingFilter, responseLoggingFilter);

    }
    //Helper method to check if a list is in descending order
    public boolean isSortDescending(List<Integer> list){
        List<Integer> sortedList = new ArrayList<>(list);
        Collections.sort(sortedList, Collections.reverseOrder());
        return sortedList.equals(list);
    }
    //Helper method to check if a list is in ascending order
    public boolean isSortAscending(List<Integer> list){
    List<Integer> sortedList = new ArrayList<>(list);
    Collections.sort(sortedList);
    return sortedList.equals(list);
    }
//Helper method to check dates fall within the specified range
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public boolean validateCartDatesWithinRange(List<String> cartDates, String startDate, String endDate){
        LocalDate start = LocalDate.parse(startDate, FORMATTER);
        LocalDate end = LocalDate.parse(endDate, FORMATTER);

        for(String dateTime : cartDates){
           LocalDate cartDate = LocalDate.parse(dateTime.substring(0,10), FORMATTER);
           if(cartDate.isBefore(start)||cartDate.isAfter(end)){
               return false; // return false if any cart date is out of range
           }
        }
        return true; // All dates are within the range

    }
}
