package page_configuration.elempleo_page;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page_configuration.BasePage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ElempleoPage extends BasePage {

    //Get job description by page
    public List<String> getListOfJobs(String jobName) {
        waitFewSec(3);
        List<WebElement> blockList = driver.findElements(By.xpath("//div[contains(@class, 'result-item')]"));
        List <String> jobsList = new ArrayList<>();

        String[] splitSearchedWords = jobName.split(" ");
        List<String> searchedWordsList = new ArrayList<>(Arrays.asList(splitSearchedWords).stream().map(String::toLowerCase).collect(Collectors.toList()));

        for(WebElement job : blockList) {

            String title = job.findElement(By.xpath(".//a")).getAttribute("title");
            String[] splitJobWords = title.split(" ");
            List<String> jobWordsList = new ArrayList<>(Arrays.asList(splitJobWords).stream().map(String::toLowerCase).collect(Collectors.toList()));

            if (jobWordsList.containsAll(searchedWordsList)){
                String company = job.findElement(By.xpath(".//span[contains(@class, 'info-company')]")).getText();
                String href = job.findElement(By.xpath(".//a")).getAttribute("href");
                jobsList.addAll(Arrays.asList(title, company, href));

            }
        }

        return jobsList;
    }

    //Get numbers of the pagination
    public List<Integer> getNumberPagination() throws Exception {

        try {

            Actions actions = new Actions(driver);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//ul[contains(@class, 'pagination')]")));
            WebElement paginationComponent = driver.findElement(By.xpath("//ul[contains(@class, 'pagination')]"));

            actions.moveToElement(paginationComponent);
            actions.perform();

            List<Integer> paginationNumber = new ArrayList<>();
            List<WebElement> paginationNumberElements = driver.findElements(By.xpath("//ul[contains(@class, 'pagination')]/li/a"));

            for (WebElement paginationNumberElement : paginationNumberElements) {
                String page = paginationNumberElement.getText();
                Pattern pattern = Pattern.compile("[^0-9]");
                String numberOnly = pattern.matcher(page).replaceAll("");

                if (isNumeric(numberOnly)) {
                    int n = Integer.parseInt(numberOnly);
                    paginationNumber.add(n);
                }

            }

            return paginationNumber;

        } catch (TimeoutException ex){
            return null;
        }

        catch (ElementNotInteractableException ex2){
            return null;
        }

    }

    //Click in each pagination and get all jobs description until last page of pagination
    public List getAllJobsDescriptionsByPagination(String jobName) throws Exception {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));


        int contraCounter = 2;
        int primaryCounter = 1;

        List<String> jobsDescriptions = new ArrayList<>();

        for(int counter = primaryCounter; counter<contraCounter; counter++) {
            List<Integer>paginationNumbers = getNumberPagination();

            //Conditions to act differently according the values get from pagination element
            if (paginationNumbers == null){

                jobsDescriptions.addAll(getListOfJobs(jobName));

            }

            else if(paginationNumbers.contains(counter+1)) {

                primaryCounter++;
                contraCounter = primaryCounter+1;

                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[contains(@class, 'pagination ee-mod')]/li/*[contains(text(), '"+counter+"')]")));
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
                Thread.sleep(2000);


                jobsDescriptions.addAll(getListOfJobs(jobName));

            }

            else if (!paginationNumbers.contains(counter+1)){

                primaryCounter = contraCounter;
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[contains(@class, 'pagination ee-mod')]/li/*[contains(text(), '"+counter+"')]")));
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
                Thread.sleep(2000);

                jobsDescriptions.addAll(getListOfJobs(jobName));

            }
        }
        return jobsDescriptions;
    }

    //Check if the string is a number
    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }

        if(str.isEmpty()) {
            return false;
        }
        return true;
    }


    public void searchJobsName(String jobName){

        WebElement searchbox = driver.findElement(By.xpath("//input[contains(@placeholder, 'Busca')]"));
        searchbox.sendKeys(jobName);
        WebElement searchbutton = driver.findElement(By.xpath("//*[contains(@class,'input-group')]/*[contains(@class,'btn-primary')]"));
        searchbutton.click();
        waitFewSec(3);

    }

}
