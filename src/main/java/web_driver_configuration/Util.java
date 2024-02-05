package web_driver_configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {

    private static Properties config = new Properties();

    //Method to get the path where chrome driver is located and urls of web pages
    public static Properties readPropertiesFile() throws IOException {
        FileInputStream inputStream = new FileInputStream("config/data.properties");
        config.load(inputStream);
        return config;
    }

}
