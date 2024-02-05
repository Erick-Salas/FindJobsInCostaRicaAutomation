package page_configuration;

import static page_configuration.BaseProperties.getProperty;

public class SetupPage extends BasePage{

    //Methods to open the job web pages in the web browser
    public SetupPage openAccionTrabajoPage(){
        driver.get(getProperty("acciontrabajo.page.url"));
        waitForPage();
        waitFewSec(3);
        return this;
    }

    public SetupPage openBuscoJobsPage(){
        driver.get(getProperty("buscojobs.page.url"));
        waitForPage();
        waitFewSec(3);
        return this;
    }

    public SetupPage openElempleoPage(){
        driver.get(getProperty("elempleo.page.url"));
        waitForPage();
        waitFewSec(3);
        return this;
    }

    public SetupPage openIndeedPage(){
        driver.get(getProperty("indeed.page.url"));
        waitForPage();
        waitFewSec(3);
        return this;
    }

    public SetupPage openJooblePage(){
        driver.get(getProperty("jooble.page.url"));
        waitForPage();
        waitFewSec(3);
        return this;
    }

    public SetupPage openOpcionEmpleoPage(){
        driver.get(getProperty("opcionempleo.page.url"));
        waitForPage();
        waitFewSec(3);
        return this;
    }

    public SetupPage openTecolocoPage(){
        driver.get(getProperty("tecoloco.page.url"));
        waitForPage();
        waitFewSec(3);
        return this;
    }

    public SetupPage openLinkedinPage(){
        driver.get(getProperty("linkedin.page.url"));
        waitForPage();
        waitFewSec(3);
        return this;
    }

}
