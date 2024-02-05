package UiWebAutomations.jobs_websites.acciontrabajo;

import UiWebAutomations.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;
import page_configuration.SetupPage;
import page_configuration.acciontrabajo_page.AccionTrabajoPage;

import java.util.List;

import static page_configuration.BaseProperties.getProperty;
import static report_configuration.LogsToHtmlReport.generateHtmlReport;

public class JobSearchInAccionTrabajoWebPageTest extends BaseTest {

    //Test method that is going to search a job in acciontrabajo web page
    @Test(description = "Search Jobs in acciontrabajo.co.cr web page")
    public void shouldSearchJobsInAccionTrabajoPage() throws Exception {
        //Initialize logger
        Logger logger = LogManager.getLogger(JobSearchInAccionTrabajoWebPageTest.class);

        new SetupPage().openAccionTrabajoPage();
        logger.info("Page: "+getProperty("acciontrabajo.page.url"));

        AccionTrabajoPage accionTrabajoPage = new AccionTrabajoPage();

        accionTrabajoPage.searchJobInInputField(getProperty("jobName"));
        logger.info("Job name searched: " + getProperty("jobName"));

        accionTrabajoPage.orderByDate();

        List<String> listOfJobs = accionTrabajoPage.getAllTheJobsAfterScrolling(getProperty("jobName"));

        for (String job: listOfJobs){
            logger.info(job);
        }

        //Shutdown logger
        LogManager.shutdown();

    }

    }
