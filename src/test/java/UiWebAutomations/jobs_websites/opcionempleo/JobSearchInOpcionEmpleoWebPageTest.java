package UiWebAutomations.jobs_websites.opcionempleo;

import UiWebAutomations.BaseTest;
import UiWebAutomations.jobs_websites.jooble.JobSearchInJoobleWebPageTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_configuration.SetupPage;
import page_configuration.opcionempleo_page.OpcionEmpleoPage;

import java.util.List;

import static page_configuration.BaseProperties.getProperty;
import static report_configuration.LogsToHtmlReport.generateHtmlReport;

public class JobSearchInOpcionEmpleoWebPageTest extends BaseTest {

    //Test method that is going to search a job in opcionempleo web page
    @Test(description = "Search Jobs in opcionempleo.com web page")
    public void shouldSearchJobsInOpcionEmpleoPage() throws Exception{
        //Initialize logger
        Logger logger = LogManager.getLogger(JobSearchInOpcionEmpleoWebPageTest.class);

        new SetupPage().openOpcionEmpleoPage();
        logger.info("Page: "+getProperty("opcionempleo.page.url"));

        OpcionEmpleoPage opcionEmpleoPage = new OpcionEmpleoPage();

        opcionEmpleoPage.searchJobInInputField(getProperty("jobName"));
        logger.info("Job name searched: "+getProperty("jobName"));

        List<String> listOfJobs = opcionEmpleoPage.getAllJobDescriptionByPageOfThePagination(getProperty("jobName"));
        for (String job: listOfJobs){
            logger.info("---"+job);
        }

        LogManager.shutdown();

    }
}
