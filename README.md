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
  
  ![automations running](https://github.com/Erick-Salas/FindJobsInCostaRicaAutomation/assets/63444171/057aadea-1834-42f2-9c25-2c92b8b458c3)

- Once the web automations have finished, go to the following project path: FindJobsInCostaRicaAutomation > html_report
- Here you will find a HTML file with all the information called 'report', you can open it with right click then click on > Open In > Browser > Chrome

  ![report](https://github.com/Erick-Salas/FindJobsInCostaRicaAutomation/assets/63444171/c743640f-bcf3-428d-8fb8-196ff8edfaaa)

  
  
# Important Notes:
- These automations need the Google Chrome web browser to be able to run, it must be installed in your computer.

- ~~The chromedriver.exe file must be updated accordingly to the version of your Chrome browser. You can download the chromedriver.exe from this website: https://googlechromelabs.github.io/chrome-for-testing/. To know what version you need to download you can open your Chrome browser, go to Help > About Google Chrome. Once the chromedriver.exe is downloaded you need to placed it in the following project path: FindJobsInCostaRicaAutomation > web_driver > chrome_driver.~~ The download of the web driver now is manage throw the bonigarcia library, so you don't have to download the web driver every time there is a new version.

- ~~To be able to run all the automations effectively you should close all Google Chrome windows before running the automations.~~ Due to new policy in google chrome now it is not posible to run the automation using your profile path, now you can run the automations without the necesity of closing the Chrome windows. 

- The Costa Rica jobsites used for these automations are:
  - ~~https://acciontrabajo.co.cr/~~ Due to anti bot captchas this website cannot be scrapped.
  - https://www.buscojobs.cr/
  - https://www.elempleo.com/cr/cross
  - ~~https://cr.indeed.com/~~ Due to anti bot captchas this website cannot be scrapped.
  - https://cr.jooble.org/
  - https://www.opcionempleo.co.cr/
  - https://www.tecoloco.co.cr/
  - ~~https://www.linkedin.com/feed/~~ Due to new Google Chrome policy that I refer to in the 3er bullet, now it is not possible to use your chrome profile and your cookies to be able to scrape data in your LinkedIn.

- ~~To run the LinkedIn automation effectively you must first login with your account after running the automation. When the automation opens the website link, it must enter your LinkedIn session.~~ This is not possible now.

- The automations can fail if the user interface of the websites were modified, normally for UI automations is necessary maintenance of the automation scripts when this happens.

- If the automations fail can be because the chromedriver.exe is not updated or because the websites were modified.

- ~~If your are using a different path to store the Default folder of Chrome, that can cause that LinkedIn automation fail (normally you can find it here: C:\Users\{your_user}\AppData\Local\Google\Chrome\User Data\)~~ This is not possible now.

- These automations were tested using Windows OS.

# Technologies used:
- Java
- Selenium
- TestNG
- Log4j
