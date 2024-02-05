package page_configuration.opcionempleo_page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import page_configuration.BasePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OpcionEmpleoPage extends BasePage {


    public void searchJobInInputField(String job){
        driver.findElement(By.xpath("//input[@type='search']")).sendKeys(job);
        driver.findElement(By.xpath("//button[@type='submit' and @class='btn btn-primary btn-l']")).click();
        waitForPage();
    }

    public void scrollDownToTheButtonOfThePage(){
        waitForPage();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //Scroll down till the bottom of the page
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        waitFewSec(3);

    }

    public List getAllJobsSearched(String jobSearched){

        waitFewSec(5);

        List<WebElement> jobTitles = driver.findElements(By.xpath("//article[@class='job clicky']//header/h2/a"));
        List<String> jobDescriptionsList = new ArrayList<>();

        int counter=0;

        String [] arrayJobWordsSearched = jobSearched.split(" ");
        List<String> jobTitleWordsSearched = new ArrayList<>(Arrays.asList(arrayJobWordsSearched).stream().map(String::toLowerCase).collect(Collectors.toList()));

        while (jobTitles.size()-1>=counter){

            String jobName = jobTitles.get(counter).getAttribute("title");

            String [] jobTitleSplitBySpace = jobName.split(" ");

            List<String> listOfJobNameWords = new ArrayList<>(Arrays.asList(jobTitleSplitBySpace).stream().map(String::toLowerCase).collect(Collectors.toList()));

            if (listOfJobNameWords.containsAll(jobTitleWordsSearched)) {

                String company;
                try {
                    company = jobTitles.get(counter).findElement(By.xpath("./ancestor::ul/li[not(@class='cjgad-outer')]["+counter+1+"]//p[@class='company']")).getText();
                }catch (Exception e){
                    company = "";
                }
                String link = jobTitles.get(counter).getAttribute("href");

                jobDescriptionsList.addAll(Arrays.asList(jobName, company, link));

            }

            counter++;
        }

        return jobDescriptionsList;

    }

    public List getAllJobDescriptionByPageOfThePagination(String jobName){

        List<String> allJobDescriptions = new ArrayList<>();

        boolean isNextPageButtonVisible;

        scrollDownToTheButtonOfThePage();

        try{
            isNextPageButtonVisible = driver.findElement(By.xpath("//a[contains(text(), 'Siguiente')]")).isDisplayed();
        }catch (Exception ex){
            isNextPageButtonVisible = false;
        }

        allJobDescriptions.addAll(getAllJobsSearched(jobName));

        while(isNextPageButtonVisible){
            driver.findElement(By.xpath("//a[contains(text(), 'Siguiente')]")).click();
            scrollDownToTheButtonOfThePage();

            allJobDescriptions.addAll(getAllJobsSearched(jobName));

            try{
                isNextPageButtonVisible = driver.findElement(By.xpath("//a[contains(text(), 'Siguiente')]")).isDisplayed();
            }catch (Exception ex){
                isNextPageButtonVisible = false;
            }

        }
        return allJobDescriptions;
    }

}
