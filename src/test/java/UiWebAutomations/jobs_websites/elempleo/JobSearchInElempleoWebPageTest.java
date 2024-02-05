package UiWebAutomations.jobs_websites.elempleo;

import UiWebAutomations.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_configuration.SetupPage;
import page_configuration.elempleo_page.ElempleoPage;

import java.util.List;

import static page_configuration.BaseProperties.getProperty;
import static report_configuration.LogsToHtmlReport.generateHtmlReport;

public class JobSearchInElempleoWebPageTest extends BaseTest {

    //Test method that is going to search a job in elempleo web page
    @Test(description = "Search Jobs in elempleo.com web page")
    public void shouldSearchJobsInElempleoPage() throws Exception{

        //Initialize logger
        Logger logger = LogManager.getLogger(JobSearchInElempleoWebPageTest.class);

        new SetupPage().openElempleoPage();
        logger.info("Page: "+getProperty("elempleo.page.url"));

        ElempleoPage elempleoPage = new ElempleoPage();

        elempleoPage.searchJobsName(getProperty("jobName"));
        logger.info("Job name searched: "+getProperty("jobName"));

        List<String> listOfJobs = elempleoPage.getAllJobsDescriptionsByPagination(getProperty("jobName"));

        for (String job: listOfJobs){
            logger.info("---"+job);
        }

        LogManager.shutdown();

    }

}
