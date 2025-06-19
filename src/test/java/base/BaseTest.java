package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.ExtentManager;
import utils.ScreenshotUtil;
import com.aventstack.extentreports.testng.listener.ExtentITestListenerClassAdapter;

import io.github.bonigarcia.wdm.WebDriverManager;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.logging.log4j.*;
@Listeners({ExtentITestListenerClassAdapter.class})
public class BaseTest {
    protected WebDriver driver;
    protected Logger logger = LogManager.getLogger();
    protected static ExtentReports extent;
    protected static ExtentTest test;

    @BeforeSuite
    public void setupExtent() {
        extent = ExtentManager.getExtentReports();
    }

    @BeforeClass
    public void setup() {
        logger.info("Reading configuration...");
        String browser = ConfigReader.getProperty("browser");
        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
        }
        driver.manage().window().maximize();
        logger.info("Browser launched.");
    }

    @AfterMethod
    public void captureFailure(org.testng.ITestResult result) {
        if (!result.isSuccess()) {
            ScreenshotUtil.captureScreenshot(driver, result.getName());
            logger.error("Test failed: " + result.getName());
        }
    }

    @AfterSuite
    public void tearDownExtent() {
        extent.flush();
    }

    @AfterClass
    public void tearDown() {
        logger.info("Closing browser.");
        if (driver != null) driver.quit();
    }
}
