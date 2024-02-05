package page_configuration.acciontrabajo_page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import page_configuration.BasePage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccionTrabajoPage extends BasePage {

    //Search a job name in the profession field and click on search jobs button
    public void searchJobInInputField(String job){
        driver.findElement(By.xpath("//input[contains(@placeholder, 'profesi')]")).sendKeys(job);
        driver.findElement(By.xpath("//input[@value='buscar empleos']")).click();
        waitForPage();
        waitFewSec(3);
    }

    //Get job title, company and link of the jobs that match the job name that you are searching
    public List<String> getAllTheJobsDescriptions(String job){

        //Get all the blocks of jobs
        List<WebElement> blocks = driver.findElements(By.xpath("//div[contains(@class, 'listing_url')]"));
        List<String> listOfJobsThatMatchSearchCriteria = new ArrayList<>();

        int counter = 0;
        String[] splitSearchedWords = job.split(" ");
        List<String> searchedWordsList = new ArrayList<>(Arrays.asList(splitSearchedWords));

        while (counter<=blocks.size()-1){
            //Find the job title in the block
            String jobTitle = blocks.get(counter).findElement(By.xpath(".//h2")).getText();

            String[] splitJobWords =  jobTitle.split(" ");
            List<String> jobWordsList = new ArrayList<>(Arrays.asList(splitJobWords));

            //Find company in the block
            String company = blocks.get(counter).findElement(By.xpath("./b")).getText();

            //Find link in the block
            String href = blocks.get(counter).getAttribute("href");

            //add https://acciontrabajo.co.cr at the beginning of some links that comes without that part
            if(!href.startsWith("https://acciontrabajo.co.cr")){
                href = "https://acciontrabajo.co.cr" + href;
            }

            //Verify that the words that you are searching are in the job post
            if (jobWordsList.containsAll(searchedWordsList)){
                listOfJobsThatMatchSearchCriteria.addAll(Arrays.asList(jobTitle, company, href));
            }

            counter++;
        }
        return  listOfJobsThatMatchSearchCriteria;
    }

    //Order by date
    public void orderByDate(){
        waitFewSec(3);
        driver.findElement(By.xpath("//div[@class='filters_here']/div[1]")).click();
        waitFewSec(3);
        driver.findElement(By.xpath("//div[@class='filters_here']/div[1]//div[text()='Fecha']")).click();
    }

    //Get all the job post by all the pagination
    public List<String> getAllTheJobsAfterScrolling(String job){
        scrollDown();
        List<String> listOfAllJobsFound = new ArrayList<>();
        int page = 0;

        while(page<=4) {
            scrollDown();
            page++;
        }

        listOfAllJobsFound.addAll(getAllTheJobsDescriptions(job));

        return listOfAllJobsFound;
    }

    //Scroll down the web browser
    public void scrollDown(){
        waitFewSec(4);
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0, document.body.scrollHeight)");
        waitFewSec(4);
    }

}
