package page_configuration;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import web_driver_configuration.WebDriverManager;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected WebDriver driver;
    protected Wait<WebDriver> fluentWait;


    protected BasePage(){
        driver = WebDriverManager.getDriver().getDriver();
        waitForPage();
    }

    public void waitForPage(){
        (new WebDriverWait(WebDriverManager.getDriver().getDriver(), Duration.ofSeconds(30))).until((wd) -> {
            return ((JavascriptExecutor)wd).executeScript("return document.readyState", new Object[0]).equals("complete");
        });
    }

    public void waitFewSec(int timeoutInSeconds){
        try{
            Thread.sleep(Math.multiplyExact(timeoutInSeconds, 1000));
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public WebElement findElement(By by){
        Duration duration = Duration.ofSeconds(20);
        Duration pooling = Duration.ofSeconds(1);
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(duration)
                .pollingEvery(pooling)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public List<WebElement> findElements(By by){

        Duration duration = Duration.ofSeconds(20);
        Duration pooling = Duration.ofSeconds(1);

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(duration)
                .pollingEvery(pooling)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);

        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public boolean isVisible(WebElement element){
        try {
            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    public boolean isClickable(WebElement element){
        try {
            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        }catch (Exception ex){
            return false;
        }
    }

}
