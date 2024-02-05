package UiWebAutomations.jobs_websites.tecoloco;

import UiWebAutomations.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_configuration.SetupPage;
import page_configuration.tecoloco_page.TecolocoPage;

import java.util.List;

import static page_configuration.BaseProperties.getProperty;
import static report_configuration.LogsToHtmlReport.generateHtmlReport;

public class JobSearchInTecolocoWebPageTest extends BaseTest {

    //Test method that is going to search a job in tecoloco web page
    @Test(description = "Search Jobs in tecoloco.com web page")
    public void shouldSearchJobsInTecolocoPage() throws Exception{
        //Initialize logger
        Logger logger = LogManager.getLogger(JobSearchInTecolocoWebPageTest.class);

        new SetupPage().openTecolocoPage();
        logger.info("Page: "+getProperty("tecoloco.page.url"));

        TecolocoPage tecolocoPage = new TecolocoPage();

        tecolocoPage.searchJob(getProperty("jobName"));
        logger.info("Job name searched: "+getProperty("jobName"));

        List<String> listOfJobs = tecolocoPage.getAllTheJobsOfEveryPageOfPagination(getProperty("jobName"));
        for (String job: listOfJobs){
            logger.info(job);
        }

        LogManager.shutdown();

    }
}
