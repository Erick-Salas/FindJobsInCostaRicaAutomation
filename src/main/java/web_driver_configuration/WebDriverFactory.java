package web_driver_configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.Properties;

import static web_driver_configuration.ChromeUserDataPath.getChromeUserDataPath;

public class WebDriverFactory {

    private static Properties config;

    public WebDriverFactory(){}

    //Configuration to create and behavior of the chrome web browser
    public static Driver createDriver(Browser browser) throws Exception{
        Driver driver = new Driver();

        switch(browser){
            case CHROME :
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
                chromeOptions.addArguments(new String[]{"disable-infobars"});
                chromeOptions.addArguments(new String[]{"--start-maximized"});
                chromeOptions.addArguments(new String[]{"--disable-extensions"});
                chromeOptions.addArguments(new String[]{"--ignore-certificate-errors"});
                chromeOptions.addArguments("--remote-allow-origins=*");
//                chromeOptions.addArguments("--user-data-dir="+getChromeUserDataPath());
//                chromeOptions.addArguments("--profile-directory=Default");
                ChromeDriver chrome;

                //Try to initialize ChromeDriver with chromeOptions if something fail with chromeOptions, start a default ChromeDriver
                try {
                    WebDriverManager.chromedriver().setup();
                    chrome = new ChromeDriver(chromeOptions);
                }catch (Exception ex){
                    new ChromeDriver().quit();
                    System.out.println("Default Chrome browser displayed");
                    chrome = new ChromeDriver();
                }

                driver.setDriver(chrome);
                break;

            default:
                throw new Exception("Option Not Available");
        }

        driver.getDriver();
        return driver;
    }

    //Load properties file once class is loaded
    static {
        try{
            config = Util.readPropertiesFile();
        } catch (IOException var){
            var.printStackTrace();
        }
    }

    public static enum Browser{
        CHROME;

        private Browser(){}
    }

}
