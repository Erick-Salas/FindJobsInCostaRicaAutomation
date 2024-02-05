Are you tired of spending to much time trying to find a job for a role that you want in different Costa Rica job websites? 

With this project you will be able to generate a report with a list of jobs from different Costa Rica job websites based on a job name that you choose. 

You just have to run the program and let your computer do the search work without you having to spend time doing it manually.

The report contains the name of the job found, the name of the company and the URL of the page where the job was found.

# Instructions:
- Clone this project and open it using IntelliJ IDE (since this project was made using this IDE)
- Go to the following project path: FindJobsInCostaRicaAutomation > config
- Open data.properties file and on the line that says 'jobName' you can put the name of the job you are searching for after the '=' character

  <img width="649" alt="image" src="https://github.com/Erick-Salas/FindJobsInCostaRicaAutomation/assets/63444171/e3a177a8-c655-4966-adc6-da026da7b9c7">
  
- Go to the following project path: FindJobsInCostaRicaAutomation > src > test > resources
- Rigth click on suite.xml file then click on Run option.

  <img width="388" alt="image" src="https://github.com/Erick-Salas/FindJobsInCostaRicaAutomation/assets/63444171/35a5d918-1a01-4d09-97f5-f8d5106ab642">

- The automations will start to run to search the job name in different job websites from Costa Rica, wait until the automations finish.
- Once the web automations have finished, go to the following project path: FindJobsInCostaRicaAutomation > html_report
- Here you will find a HTML file with all the information called 'report', you can open it with right click then click on > Open In > Browser > Chrome
  
# Important Notes:
- These automations need the Google Chrome web browser to be able to run, it must be installed in your computer.
- The chromedriver.exe file must be updated accordingly to the version of your Chrome browser. You can download the chromedriver.exe from this website: https://googlechromelabs.github.io/chrome-for-testing/. To know what version you need to download you can open your Chrome browser, go to Help > About Google Chrome. Once the chromedriver.exe is downloaded you need to placed it in the following project path: FindJobsInCostaRicaAutomation > web_driver > chrome_driver.  
- To be able to run all the automations effectively you should close all Google Chrome windows before running the automations.
- The Costa Rica jobsites used for these automations are:
  - https://acciontrabajo.co.cr/
  - https://www.buscojobs.cr/
  - https://www.elempleo.com/cr/cross
  - https://cr.indeed.com/
  - https://cr.jooble.org/
  - https://www.opcionempleo.co.cr/
  - https://www.tecoloco.co.cr/
  - https://www.linkedin.com/feed/
- To run the LinkedIn automation effectively you must first login with your account after running the automation. When the automation opens the website link, it must enter your LinkedIn session.
- The automations can fail if the user interface of the websites were modified, normally for UI automations is necessary maintenance of the automation scripts when this happens.
- If the automations fail can be because the chromedriver.exe is not updated or because the websites were modified.

# Technologies used:
- Java
- Selenium
- TestNG
- Log4j
