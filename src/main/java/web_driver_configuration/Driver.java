package web_driver_configuration;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class Driver {

    private WebDriver driver;
    private WebDriverWait wait;
    private Wait<WebDriver> fluentWait;
    private JavascriptExecutor js;
    private int[] timeouts;

    public Driver(){

    }

    //Method for Configuration of WebDriver should run
    public void setTiming(int waitTimeout, int pollingCadence, Duration waitDurationTimeout){
        this.timeouts = new int[]{waitTimeout, pollingCadence};
        this.wait = new WebDriverWait(this.driver, waitDurationTimeout);
        this.js = (JavascriptExecutor) this.driver;
        this.fluentWait = (new FluentWait(this.driver)).withTimeout(Duration.ofSeconds((long)waitTimeout)).pollingEvery(Duration.ofSeconds((long)pollingCadence)).ignoring(NoSuchElementException.class).ignoring(ElementClickInterceptedException.class).ignoring(WebDriverException.class);

    }
    //Get and Set of Web Driver
    public void setDriver(WebDriver driver){ this.driver=driver;}

    public WebDriver getDriver(){
        return this.driver;
    }

    public WebDriverWait getWait() { return this.wait;}

    public JavascriptExecutor getJs(){ return this.js;}

    public Wait<WebDriver> getFluentWait(){return this.fluentWait;}

    public int getWaitTimeout() {return this.timeouts[0];}

    public int getPollingCadence() {return this.timeouts[1];}


}
