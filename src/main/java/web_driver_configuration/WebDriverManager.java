package web_driver_configuration;

public class WebDriverManager {

    private static ThreadLocal<Driver> webDriver = new ThreadLocal();

    public WebDriverManager(){

    }

    //Set and get Driver
    public static Driver getDriver(){ return (Driver) webDriver.get();}

    public static void setWebDriver(Driver driver){
        webDriver.set(driver);
    }

}
