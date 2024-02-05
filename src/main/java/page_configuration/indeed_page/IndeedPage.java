package page_configuration.indeed_page;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import page_configuration.BasePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IndeedPage extends BasePage {

    public void searchJobName(String job) throws Exception{
        driver.findElement(By.xpath("//input[@id='text-input-what']")).sendKeys(job);
        driver.findElement(By.xpath("//button[@class='yosegi-InlineWhatWhere-primaryButton']")).click();
        waitForPage();
    }

    public List getAllTheJobsByPage(String jobName){
        List<WebElement> blocks = driver.findElements(By.xpath("//div[contains(@class, 'cardOutline')]"));
        int counter = 0;
        List <String> jobsList = new ArrayList<>();

        String[] splitSearchedWords = jobName.split(" ");
        List<String> searchedWordsList = new ArrayList<>(Arrays.asList(splitSearchedWords).stream().map(String::toLowerCase).collect(Collectors.toList()));

        while (counter<=blocks.size()-1){

            String jobTitle = blocks.get(counter).findElement(By.xpath(".//a/span[@title]")).getText();
            String[] splitJobWords = jobTitle.split(" ");
            List<String> jobWordsList = new ArrayList<>(Arrays.asList(splitJobWords).stream().map(String::toLowerCase).collect(Collectors.toList()));


            if (jobWordsList.containsAll(searchedWordsList)){
                String company = blocks.get(counter).findElement(By.xpath(".//span[@data-testid='company-name']")).getText();
                String href = blocks.get(counter).findElement(By.xpath(".//a/span[@title]/..")).getAttribute("href");
                jobsList.addAll(Arrays.asList(jobTitle, company, href));
            }

            counter++;
        }
        return jobsList;
    }

    public List getAllTheJobsForEveryPageOfPagination(String jobName){
        scrollDown();

        List<String> jobsDescriptions = new ArrayList<>();

        int firstPageLoad = 0;
        int page = 1;


        if (firstPageLoad < 1) {
            jobsDescriptions.addAll(getAllTheJobsByPage(jobName));
            firstPageLoad++;
        }

        while(checkIfThereIsAGreaterNumberAndReturnValue(page)!=0) {
            int actualPaginationValue = checkIfThereIsAGreaterNumberAndReturnValue(page);
            WebElement paginationButton = driver.findElement(By.xpath("//nav[@role='navigation']//a[text()='"+actualPaginationValue+"']"));
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", paginationButton);
            waitFewSec(3);
            driver.navigate().refresh();
            scrollDown();
            jobsDescriptions.addAll(getAllTheJobsByPage(jobName));
            page++;
        }

        return jobsDescriptions;

    }

    public void scrollDown(){
        try {
            waitFewSec(4);
            WebElement pagination = driver.findElement(By.xpath("//nav[@role='navigation']"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", pagination);
            waitFewSec(4);
        }catch (Exception ex){
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
            waitFewSec(4);
        }
    }

    //Get all the numbers in the pagination section
    public List getPaginationWebElementsNumbers(){
        List<WebElement> paginationSectionElements = driver.findElements(By.xpath("//nav[@role='navigation']//a"));
        List<String> paginationNumbers = new ArrayList<>();
        for (WebElement element: paginationSectionElements){
            String textElement = element.getText();
            if (StringUtils.isNumeric(textElement)){
                paginationNumbers.add(textElement);
            }
        }
        return paginationNumbers;
    }

    //Check in which page you are and if there is a greater value to be clicked in the pagination section
    public int checkIfThereIsAGreaterNumberAndReturnValue(int number){
        List<String> numbersList = getPaginationWebElementsNumbers();
        int returnValue=0;

        for (String numberInList : numbersList) {
            int integerNumber = Integer.valueOf(numberInList);
            if (integerNumber > number) {
                returnValue = integerNumber;
                break;
            }
        }

        return returnValue;
    }

}
