package page_configuration.buscojobs_page;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import page_configuration.BasePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BuscoJobsPage extends BasePage {

    //Search jobs words
    public void searchAJobName(String job) throws Exception {
        driver.findElement(By.xpath("//input[@class='form-control cargo']")).sendKeys(job);
        driver.findElement(By.xpath("//button[@id='btn-busqueda']")).click();
        waitForPage();
    }

    //Get all jobs for every page of the pagination
    public List<String> getAllTheJobsOfEveryPagination(String job){
        scrollDown();

        int firstPageLoad = 0;
        int page = 1;
        int firstPageAdd =0;

        List<String> allJobsDescriptions =  new ArrayList<>();

        if (firstPageLoad < 1) {
            allJobsDescriptions.addAll(getAllJobsOfThePage(job));
            firstPageLoad++;
        }

        while(checkIfThereIsAGreaterNumberAndReturnValue(page)!=0) {
            int actualPaginationValue = checkIfThereIsAGreaterNumberAndReturnValue(page);

            //This section of the code is to refresh the page to close the first add that appear when the first pagination number is clicked
            if (firstPageAdd<1){
                driver.findElement(By.xpath("//ul[@class='pagination']/li/a[text()='"+actualPaginationValue+"']")).click();
                driver.navigate().refresh();
                waitFewSec(2);
            }

            driver.findElement(By.xpath("//ul[@class='pagination']/li/a[text()='"+actualPaginationValue+"']")).click();
            waitFewSec(4);
            scrollDown();
            allJobsDescriptions.addAll(getAllJobsOfThePage(job));
            page++;
            firstPageAdd++;
        }
        return allJobsDescriptions;
    }

    //Get all jobs by pagination page
    public List<String> getAllJobsOfThePage(String job) {
        List<WebElement> blocks = driver.findElements(By.xpath("//div[@class='row ListadoOfertas_result__vlmRK click undefined']"));
        List<WebElement> companyBlock = driver.findElements(By.xpath("//div[@class='row ListadoOfertas_result__vlmRK click undefined']/div[@class='col-lg-9 col-md-12 ListadoOfertas_oferta__6GIri']/span"));

        int counter = 0;

        String company = "";

        List<String> jobsDescriptionList = new ArrayList<>();

        String[] splitSearchedWords = job.split(" ");
        List<String> searchedWordsList = new ArrayList<>(Arrays.asList(splitSearchedWords).stream().map(String::toLowerCase).collect(Collectors.toList()));


        while (counter<=blocks.size()-1){

            String jobTitle = blocks.get(counter).findElement(By.xpath("./div[@class='col-lg-9 col-md-12 ListadoOfertas_oferta__6GIri']/h3")).getText();

            try {
                if (companyBlock.get(counter).findElement(By.xpath("./a")).isDisplayed()) {
                    company = companyBlock.get(counter).findElement(By.xpath("./a")).getText();
                }
            }catch (NoSuchElementException ex) {
                company = companyBlock.get(counter).getText();
            }

            String[] splitJobWords =  jobTitle.split(" ");
            List<String> jobWordsList = new ArrayList<>(Arrays.asList(splitJobWords).stream().map(String::toLowerCase).collect(Collectors.toList()));

            String href = blocks.get(counter).findElement(By.xpath("./a")).getAttribute("href");

            if (jobWordsList.containsAll(searchedWordsList)){
                jobsDescriptionList.addAll(Arrays.asList(jobTitle, company, href));
            }
            counter++;

        }

        return jobsDescriptionList;
    }

    //Scroll down
    public void scrollDown(){
        waitFewSec(4);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
        waitFewSec(4);
    }

    //Get all pagination numbers
    public List getPaginationWebElementsNumbers(){
        List<WebElement> paginationSectionElements = driver.findElements(By.xpath("//ul[@class='pagination']/li"));
        List<String> paginationNumbers = new ArrayList<>();
        String elementText ="";
        for (WebElement element: paginationSectionElements){
            try {
                if (element.findElement(By.xpath("./span")).isDisplayed()) {
                    elementText = element.findElement(By.xpath("./span")).getText();
                }
            }catch (NoSuchElementException ex) {
                elementText = element.findElement(By.xpath("./a")).getText();
            }

            if (StringUtils.isNumeric(elementText)){
                paginationNumbers.add(elementText);
            }
        }
        return paginationNumbers;
    }

    //Check for a greater pagination number element
    public int checkIfThereIsAGreaterNumberAndReturnValue(int number){
        List<String> list = getPaginationWebElementsNumbers();
        int returnValue=0;

        for (String forList : list) {
            int forValue = Integer.valueOf(forList);
            if (forValue > number) {
                returnValue = forValue;
                break;
            }
        }

        return returnValue;
    }

}
