package UiWebAutomations;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import web_driver_configuration.WebDriverFactory;
import web_driver_configuration.WebDriverManager;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;

import static report_configuration.LogsToHtmlReport.generateHtmlReport;

public class BaseTest {

    //Method executed before the class that contains test methods is executed
    @BeforeClass
    public void beforeClass() throws Exception{
        createChromeDriver();
        System.setProperty("log4j.configurationFile", "src/test/resources/log4j2.xml");
    }

    //Method executed before test method is executed
    @BeforeMethod
    public void beforeMethod() throws Exception{
        if (((ChromeDriver) WebDriverManager.getDriver().getDriver()).getSessionId() == null){
            createChromeDriver();
        }
    }

    //Method executed every time after test method is executed
    @AfterMethod(alwaysRun = true)
    public void afterMethod(){
        WebDriverManager.getDriver().getDriver().quit();
    }

    //Start and create chrome driver
    private void createChromeDriver() throws Exception{
        WebDriverManager.setWebDriver(WebDriverFactory.createDriver(WebDriverFactory.Browser.valueOf("CHROME")));
        WebDriverManager.getDriver().setTiming(20, 1, Duration.ofSeconds(30));
    }

    @BeforeSuite
    public void beforeSuite() throws Exception{
        File logFile = new File("logs/app.log");
        try {
            Thread.sleep(1000); // Wait for 1 second
            Files.deleteIfExists(logFile.toPath());
            System.out.println("Log file deleted successfully.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            System.err.println("Thread interrupted while waiting.");
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    @AfterSuite
    public void afterSuite() throws Exception{
        generateHtmlReport();
    }

}
