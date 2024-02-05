package page_configuration.jooble_page;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import page_configuration.BasePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class JooblePage extends BasePage {

    public void searchJobInSearchField(String job){
        WebElement jobSearchInput= findElement(By.xpath("//input[@placeholder='¿Qué empleo está buscando?']"));
        jobSearchInput.sendKeys(job);
        WebElement submitButton = findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
        waitFewSec(5);
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE).perform();
    }

    public void scrollDownToTheButtonOfThePageContinually(){
        int numberOfScrollDowns = 0;
        while(numberOfScrollDowns<10) {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            //Scroll down till the bottom of the page
            js.executeScript("window.scrollBy(0,900)");
            waitFewSec(2);

            numberOfScrollDowns++;
        }
    }

    public List getInformationForEachJobPost(String jobTitle){

        scrollDownToTheButtonOfThePageContinually();
        List<WebElement> listOfHeaderTitleJobs= driver.findElements(By.xpath("//article/header/h2"));
        List<WebElement> listOfLinks = driver.findElements(By.xpath("//article/header/h2/a"));
        List<WebElement> listOfBlocks= driver.findElements(By.xpath("//article/section"));

        Iterator<WebElement> iteratorListOfHeaderTitleJobs = listOfHeaderTitleJobs.iterator();
        Iterator <WebElement> iteratorListOfLinks = listOfLinks.iterator();
        Iterator<WebElement> iteratorBlocks = listOfBlocks.iterator();

        List<String> jobsList = new ArrayList<>();

        String [] arrayJobWordsSearched = jobTitle.split(" ");
        List<String> jobTitleWordsSearched = new ArrayList<>(Arrays.asList(arrayJobWordsSearched).stream().map(String::toLowerCase).collect(Collectors.toList()));

        while (iteratorListOfHeaderTitleJobs.hasNext()){

            String jobName = iteratorListOfHeaderTitleJobs.next().findElement(By.xpath("./a")).getText();
            String url = iteratorListOfLinks.next().getAttribute("href");

            String company = "";

            try {
                company = iteratorBlocks.next().findElement(By.xpath("./div[2]/div[2]/div[1]//p")).getText();
            } catch (NoSuchElementException ex) {
                company = "";
            }

            String [] arrayJobWords = jobName.split(" ");

            List<String> listOfJobNameWords = new ArrayList<>(Arrays.asList(arrayJobWords).stream().map(String::toLowerCase).collect(Collectors.toList()));

            if (listOfJobNameWords.containsAll(jobTitleWordsSearched)){
                jobsList.addAll(Arrays.asList(jobName, company, url));
            }

        }
        return jobsList;
    }
}
