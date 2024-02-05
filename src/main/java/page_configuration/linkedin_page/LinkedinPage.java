package page_configuration.linkedin_page;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_configuration.BasePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LinkedinPage extends BasePage {

    public void clickOnJobsButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@title='Empleos']/ancestor::a")));
        driver.findElement(By.xpath("//span[@title='Empleos']/ancestor::a")).click();
    }

    public void searchJobInInputField(String jobName){
        clickOnJobsButton();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[contains(@id, 'jobs-search-box-keyword')]")));
        driver.findElement(By.xpath("//input[contains(@id, 'jobs-search-box-keyword')]")).click();
        waitFewSec(3);
        Actions action = new Actions(driver);
        action.sendKeys(jobName).perform();
        waitFewSec(3);
        action.sendKeys(Keys.ESCAPE).perform();
        waitFewSec(2);
        action.sendKeys(Keys.ENTER).perform();
    }

    public List getAllTheJobsInThePage(String jobSearched){
        scrollDown();
        List<WebElement> blocks = driver.findElements(By.xpath("//ul[@class='scaffold-layout__list-container']/li"));
        int counter = 0;
        List<String> jobsList = new ArrayList<>();
        String [] arrayJobWordsSearched = jobSearched.split(" ");
        List<String> jobTitleWordsSearched = new ArrayList<>(Arrays.asList(arrayJobWordsSearched).stream().map(String::toLowerCase).collect(Collectors.toList()));

        while (counter<=blocks.size()-1){
            String jobTitle = blocks.get(counter).findElement(By.xpath(".//a")).getText();
            String company ="";
            String [] jobTitleSplitBySpace = jobTitle.split(" ");
            List<String> listOfJobNameWords = new ArrayList<>(Arrays.asList(jobTitleSplitBySpace).stream().map(String::toLowerCase).collect(Collectors.toList()));
            try {
                company = blocks.get(counter).findElement(By.xpath(".//span[contains(@class,'job-card-container')]")).getText();
            }catch (Exception e){
                company="";
            }
            String href = blocks.get(counter).findElement(By.xpath(".//a")).getAttribute("href");

            if (listOfJobNameWords.containsAll(jobTitleWordsSearched)){
                jobsList.addAll(Arrays.asList(jobTitle, company, href));
            }
            counter++;
        }
        return jobsList;
    }

    public List getAllJobDescriptionsByPageOfPagination(String jobName){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        scrollDown();

        List<String> jobDescriptions = new ArrayList<>();

        int firstPageLoad = 0;
        int page = 1;


        if (firstPageLoad < 1) {
            jobDescriptions.addAll(getAllTheJobsInThePage(jobName));
            firstPageLoad++;
        }

        while(checkIfThereIsAGreaterNumberAndReturnValue(page)!=0) {
            int paginationNumber = checkIfThereIsAGreaterNumberAndReturnValue(page);
            WebElement element = driver.findElement(By.xpath("//ul[contains(@class,'artdeco-pagination__pages')]/li//span[text()='"+paginationNumber+"']/.."));
            js.executeScript("arguments[0].click()", element);
            waitFewSec(4);
            scrollDown();
            jobDescriptions.addAll(getAllTheJobsInThePage(jobName));
            page++;
        }

        return jobDescriptions;
    }

    public void scrollDown(){
        waitFewSec(4);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        int scrollDowns = 0;
        while(scrollDowns<5) {
            WebElement pagination = driver.findElement(By.xpath("//header[contains(@class,'scaffold-layout')]/following-sibling::div[contains(@class, 'jobs-search-results-list')]"));
            js.executeScript("arguments[0].scrollBy(0,document.body.scrollHeight)", pagination);
            waitFewSec(3);
            scrollDowns++;
        }
    }

    public List getPaginationWebElementsNumbers(){
        List<WebElement> paginationSectionElements = driver.findElements(By.xpath("//ul[contains(@class,'artdeco-pagination__pages')]/li//span"));
        List<String> paginationNumbers = new ArrayList<>();
        for (WebElement element: paginationSectionElements){
            String elementText = element.getText();
            if (StringUtils.isNumeric(elementText)){
                paginationNumbers.add(elementText);
            }
        }
        return paginationNumbers;
    }

    public int checkIfThereIsAGreaterNumberAndReturnValue(int number){
        List<String> numbersList = getPaginationWebElementsNumbers();
        int returnValue=0;

        for (String numberValue : numbersList) {
            int value = Integer.valueOf(numberValue);
            if (value > number) {
                returnValue = value;
                break;
            }
        }

        return returnValue;
    }

}
