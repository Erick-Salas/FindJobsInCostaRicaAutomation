package UiWebAutomations.jobs_websites.buscojobs;

import UiWebAutomations.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_configuration.SetupPage;
import page_configuration.buscojobs_page.BuscoJobsPage;

import java.util.List;

import static page_configuration.BaseProperties.getProperty;
import static report_configuration.LogsToHtmlReport.generateHtmlReport;

public class JobSearchInBuscoJobsWebPageTest extends BaseTest {

    //Test method that is going to search a job in buscojobs web page
    @Test(description = "Search Jobs in bucojobs.cr web page")
    public void shouldSearchJobsInBuscoJobsPage() throws Exception{

        //Initialize logger
        Logger logger = LogManager.getLogger(JobSearchInBuscoJobsWebPageTest.class);

        new SetupPage().openBuscoJobsPage();
        logger.info("Page: "+getProperty("buscojobs.page.url"));

        BuscoJobsPage buscoJobsPage = new BuscoJobsPage();

        buscoJobsPage.searchAJobName(getProperty("jobName"));
        logger.info("Job name searched: "+getProperty("jobName"));

        List<String> listOfJobs = buscoJobsPage.getAllTheJobsOfEveryPagination(getProperty("jobName"));
        for (String job: listOfJobs){
            logger.info("---"+job);
        }

        LogManager.shutdown();

    }
}
