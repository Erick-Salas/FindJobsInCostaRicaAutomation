package page_configuration.tecoloco_page;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import page_configuration.BasePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TecolocoPage extends BasePage {

    public void searchJob(String job) throws Exception {
        driver.findElement(By.xpath("//input[@class='search-job-title search-box']")).sendKeys(job);
        driver.findElement(By.xpath("//button[@class='search-job-btn']")).click();
        waitForPage();
    }

    public List getAllTheJobsOnThePage(String jobSearched){
        List<WebElement> blocks = driver.findElements(By.xpath("//div[@class='module job-result  ']"));
        int counter = 0;
        List<String> jobDescriptionsList = new ArrayList<>();

        String [] arrayJobWordsSearched = jobSearched.split(" ");
        List<String> jobTitleWordsSearched = new ArrayList<>(Arrays.asList(arrayJobWordsSearched).stream().map(String::toLowerCase).collect(Collectors.toList()));

        while (counter<=blocks.size()-1){

            String jobTitle = blocks.get(counter).findElement(By.xpath(".//h2/a")).getText();

            String [] jobTitleSplitBySpace = jobTitle.split(" ");

            List<String> listOfJobNameWords = new ArrayList<>(Arrays.asList(jobTitleSplitBySpace).stream().map(String::toLowerCase).collect(Collectors.toList()));

            if (listOfJobNameWords.containsAll(jobTitleWordsSearched)){
                String company = blocks.get(counter).findElement(By.xpath(".//li[@class='name']")).getText();
                String href = blocks.get(counter).findElement(By.xpath(".//h2/a")).getAttribute("href");
                jobDescriptionsList.addAll(Arrays.asList(jobTitle, company, href));
            }

            counter++;
        }
        return jobDescriptionsList;
    }

    public List getAllTheJobsOfEveryPageOfPagination(String job){
        scrollDown();

        List<String> jobDescriptions = new ArrayList<>();

        int firstPageLoad = 0;
        int page = 1;


        if (firstPageLoad < 1) {
            jobDescriptions.addAll(getAllTheJobsOnThePage(job));
            firstPageLoad++;
        }

        while(checkIfThereIsAGreaterNumberAndReturnValue(page)!=0) {
            int actualMethodValue = checkIfThereIsAGreaterNumberAndReturnValue(page);
            driver.findElement(By.xpath("//ul[@id='pagination']//a[text()='"+actualMethodValue+"']")).click();
            waitFewSec(3);
            scrollDown();
            jobDescriptions.addAll(getAllTheJobsOnThePage(job));
            page++;
        }

        return jobDescriptions;
    }

    public void scrollDown(){
        waitFewSec(4);
        WebElement pagination = driver.findElement(By.xpath("//ul[@id='pagination']"));
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(true);", pagination);
        waitFewSec(4);
    }

    public List getPaginationWebElementsNumbers(){
        List<WebElement> paginationSectionElements = driver.findElements(By.xpath("//ul[@id='pagination']//a"));
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
