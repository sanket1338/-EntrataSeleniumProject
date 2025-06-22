Entrata.com Selenium Automation Framework

This is a TestNG-based Selenium test automation framework designed to validate key functionality of [Entrata.com](https://www.entrata.com). The framework follows Page Object Model (POM) structure with utility-driven enhancements for better maintainability and reporting.



Features

- Language: Java  
- Frameworks: TestNG, Maven  
- Design Pattern**: Page Object Model (POM)  
- Build Tool: Maven  
- Logging: Log4j2  
- Reporting: ExtentReports  
- Utilities: Dynamic waits, Screenshot capture, Config reader  
- Browser Support: Chrome, Edge (via WebDriverManager)



Project Structure
src/main/java/pages/ – Contains all the Page Object classes like HomePage.java that abstract UI interactions.

src/main/java/utils/ – Includes utility classes such as WaitUtils, ConfigReader, ScreenshotUtil, and ExtentManager.

src/main/resources/ – Holds the config.properties file for test configurations and log4j.xml for logging setup.

src/test/java/base/ – Contains the BaseTest class which handles WebDriver setup, teardown, logging, and Extent Reports integration.

src/test/java/tests/ – TestNG test classes are placed here, such as EntrataTests.java.

src/test/resources/ – Stores the testng.xml test suite definition file.

screenshots/ – This folder stores screenshots captured during test.

test-output/ – TestNG automatically generates reports in this directory after each test run.

pom.xml – Manages project dependencies, plugins, and build configuration.

.gitignore – Specifies files and folders to be ignored by Git.

README.md – Contains project documentation and usage instructions.




Prerequisites

- Java 17 or higher
- Maven
- Chrome or Edge browser
- Internet access



Configuration

`src/main/resources/config.properties`


browser=chrome
baseUrl=https://www.entrata.com

Demo Form Data
demo.firstname=John
demo.lastname=Doe
demo.email=john.doe@example.com
demo.phone=9999999999




Running Tests

Using TestNG (Default Suite)

bash
mvn clean test


Using specific test class from IDE

Right-click on any `@Test` method in `EntrataTests.java` > Run as TestNG Test.



Reports & Logs

- HTML Report: `test-output/ExtentReport.html`
- Screenshots: `screenshots/`
- Logs: Console + File (optional)



Test Coverage
1) Navigation to ResidentPay
Verifies that hovering over the "Products" menu and selecting "ResidentPay" redirects to the correct page and checks for the expected page title.

2) Schedule a Demo Form Interaction
Clicks on the "Watch Demo" button, waits for the demo form to load, and fills in basic form fields like First Name, Last Name, Email, and Phone. Although the form isn't submitted, all field interactions are validated.

3) Homepage Slider Auto-Transition
Confirms that the homepage slider automatically transitions from the "Homebody RXP" section to the "Property Management" section after a few seconds. This ensures that dynamic UI components behave as expected.

4) Careers Page Navigation
Scrolls to the footer and clicks the "Careers" link under the "Company" section. Validates that the redirected page title includes the term “careers”.

5) Video Presence and Playability
Scrolls to the embedded Wistia video section and checks whether the video container and play button are both visible and interactable.

| Test Case ID | Description                                       | Method                                |
|--------------|---------------------------------------------------|-----------------------------------    |
| TC01         | Navigate to ResidentPay from Products menu        | `testResidentPayNavigation()`|
| TC02         | Fill out demo request form (no submit)            | `testDemoFormInteraction()`   |
| TC03         | Validate auto-transition of hero slider           | `testHomeSliderTransition()`  |
| TC04         | Navigate to Careers page from footer              | `testCareersNavigation()`      |
| TC05         | Validate presence and playability of video        | `testVideoPlaybackPresence()` |



Clean Up

To delete reports and screenshots:

bash
mvn clean



