package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
   public static Properties prop;
    private static final String CONFIG_FILE_PATH = ".\\src\\test\\resources\\config.properties";

    public ConfigReader() {
        prop = new Properties();
        try (FileInputStream inputStream = new FileInputStream(CONFIG_FILE_PATH)) {
            prop.load(inputStream);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String getProperty(String key){
       return prop.getProperty(key);
    }

    public static int getIntProperty(String key){
        return Integer.parseInt(prop.getProperty(key));
    }


}