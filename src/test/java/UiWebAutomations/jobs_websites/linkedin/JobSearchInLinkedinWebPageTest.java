package UiWebAutomations.jobs_websites.linkedin;

import UiWebAutomations.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_configuration.SetupPage;
import page_configuration.linkedin_page.LinkedinPage;

import java.util.List;

import static page_configuration.BaseProperties.getProperty;
import static report_configuration.LogsToHtmlReport.generateHtmlReport;

public class JobSearchInLinkedinWebPageTest extends BaseTest {

    //Test method that is going to search a job in linkedin web page
    @Test(description = "Search Jobs in linkedin.com web page")
    public void shouldSearchJobsInLinkedinPage() throws Exception{
        //Initialize logger
        Logger logger = LogManager.getLogger(JobSearchInLinkedinWebPageTest.class);

        new SetupPage().openLinkedinPage();
        logger.info("Page: "+getProperty("linkedin.page.url"));

        LinkedinPage linkedinPage = new LinkedinPage();

        linkedinPage.searchJobInInputField(getProperty("jobName"));
        logger.info("Job name searched: "+getProperty("jobName"));

        List<String> listOfJobs = linkedinPage.getAllJobDescriptionsByPageOfPagination(getProperty("jobName"));
        for (String job: listOfJobs){
            logger.info(job);
        }

        LogManager.shutdown();
    }
}
