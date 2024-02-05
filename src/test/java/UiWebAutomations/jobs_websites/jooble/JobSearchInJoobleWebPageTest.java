package UiWebAutomations.jobs_websites.jooble;

import UiWebAutomations.BaseTest;
import UiWebAutomations.jobs_websites.elempleo.JobSearchInElempleoWebPageTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_configuration.SetupPage;
import page_configuration.jooble_page.JooblePage;

import java.util.List;

import static page_configuration.BaseProperties.getProperty;
import static report_configuration.LogsToHtmlReport.generateHtmlReport;

public class JobSearchInJoobleWebPageTest extends BaseTest {


    //Test method that is going to search a job in jooble web page
    @Test(description = "Search Jobs in jooble.com web page")
    public void shouldSearchJobsInJooblePage() throws Exception{
        //Initialize logger
        Logger logger = LogManager.getLogger(JobSearchInJoobleWebPageTest.class);

        new SetupPage().openJooblePage();
        logger.info("Page: "+getProperty("jooble.page.url"));

        JooblePage jooblePage = new JooblePage();

        jooblePage.searchJobInSearchField(getProperty("jobName"));
        logger.info("Job name searched: "+getProperty("jobName"));

        List<String> listOfJobs = jooblePage.getInformationForEachJobPost(getProperty("jobName"));
        for (String job: listOfJobs){
            logger.info(job);
        }

        LogManager.shutdown();

    }

}
