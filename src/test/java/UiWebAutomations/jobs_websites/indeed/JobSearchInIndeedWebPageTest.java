package UiWebAutomations.jobs_websites.indeed;

import UiWebAutomations.BaseTest;
import UiWebAutomations.jobs_websites.elempleo.JobSearchInElempleoWebPageTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_configuration.SetupPage;
import page_configuration.indeed_page.IndeedPage;

import java.util.List;

import static page_configuration.BaseProperties.getProperty;
import static report_configuration.LogsToHtmlReport.generateHtmlReport;

public class JobSearchInIndeedWebPageTest extends BaseTest {

    //Test method that is going to search a job in indeed web page
    @Test(description = "Search Jobs in indeed.com web page")
    public void shouldSearchJobsInIndeedPage() throws Exception{
        //Initialize logger
        Logger logger = LogManager.getLogger(JobSearchInElempleoWebPageTest.class);

        new SetupPage().openIndeedPage();
        logger.info("Page: "+getProperty("indeed.page.url"));

        IndeedPage indeedPage = new IndeedPage();

        indeedPage.searchJobName(getProperty("jobName"));
        logger.info("Job name searched: "+getProperty("jobName"));

        List<String> listOfJobs = indeedPage.getAllTheJobsForEveryPageOfPagination(getProperty("jobName"));

        for (String job: listOfJobs){
            logger.info("---"+job);
        }

        LogManager.shutdown();

    }

}
