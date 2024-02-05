package page_configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class BaseProperties {

    private static Properties properties;
    private static final String PROPERTY_FILE= "config/data.properties";

    private BaseProperties(){

    }

    //Load data.properties file
    public static void loadProperties(){
        BufferedReader reader;

        try{
            reader = new BufferedReader(new FileReader(PROPERTY_FILE));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e){

            }
        }
        catch (FileNotFoundException ex){

        }
    }

    //Get properties from the file
    public static String getProperty(String propName){
        if(properties == null){
            loadProperties();
        }
        String propValue = properties.getProperty(propName);
        if (propValue !=null) return propValue;
        else throw new RuntimeException(String.format("%s not specified in the .properties file"));
    }

}
