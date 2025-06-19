package tests;
import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import utils.ConfigReader;
import utils.ScreenshotUtil;
import utils.WaitUtils;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class EntrataTests extends BaseTest {
	private static final Logger logger = LogManager.getLogger(EntrataTests.class);

    @Test(priority = 1)
    public void testResidentPayNavigation() {
    	 logger.info("Test: Navigate to ResidentPay");
    	    test = extent.createTest("ResidentPay Navigation");

    	    HomePage home = new HomePage(driver);
    	    home.open(ConfigReader.getProperty("baseUrl"));

    	    home.navigateToResidentPay();

    	    // Wait for page to load completely
    	    WaitUtils.waitForPageToLoad(driver, 10);

    	    // Updated: Wait until page title contains 'ResidentPay' instead of a DOM element
    	    new WebDriverWait(driver, Duration.ofSeconds(10))
    	        .until(ExpectedConditions.titleContains("ResidentPay"));

    	    ScreenshotUtil.captureScreenshot(driver, "ResidentPay_Navigation");

    	    String title = driver.getTitle();
    	    logger.info("Page title after navigation: " + title);
    	    Assert.assertTrue(title.contains("ResidentPay"), "Page title should contain 'ResidentPay'");
    }

    @Test(priority = 2)
    public void testDemoFormInteraction() {
        logger.info("Test: Interact with Schedule a Demo form");
        test = extent.createTest("Demo Form Interaction");

        HomePage home = new HomePage(driver);
        home.open(ConfigReader.getProperty("baseUrl"));
        home.clickDemoButton();

        // Wait for page to load
        WaitUtils.waitForPageToLoad(driver, 10);
        Assert.assertTrue(driver.getCurrentUrl().contains("demo"), "User should be on demo page");

        try {
            // Wait for the form to fully render
            WaitUtils.waitForVisibility(driver, By.id("mktoForm_1108"), 15);

            // Now interact with fields
            WebElement firstName = WaitUtils.waitForVisibility(driver, By.name("FirstName"), 10);
            firstName.sendKeys(ConfigReader.getProperty("demo.firstname"));

            driver.findElement(By.name("LastName")).sendKeys(ConfigReader.getProperty("demo.lastname"));
            driver.findElement(By.name("Email")).sendKeys(ConfigReader.getProperty("demo.email"));
            driver.findElement(By.name("Phone")).sendKeys(ConfigReader.getProperty("demo.phone"));

            ScreenshotUtil.captureScreenshot(driver, "DemoFormFilled");
            logger.info("Demo form filled successfully.");

        } catch (Exception e) {
            logger.error("Error interacting with demo form: ", e);
            Assert.fail("Failed to interact with the demo form: " + e.getMessage());
        }
    }

    @Test(priority = 3)
    public void testHomeSliderTransition() {
        logger.info("Test: Verify homepage hero slider auto-transition");
        test = extent.createTest("Homepage Slider Transition");

        HomePage home = new HomePage(driver);
        home.open(ConfigReader.getProperty("baseUrl"));

        boolean result = home.waitForHomeSliderChange();
        WaitUtils.waitForPageToLoad(driver, 10);
        WaitUtils.waitForVisibility(driver, By.tagName("h1"), 10);
        ScreenshotUtil.captureScreenshot(driver, "HomeSlider_Transition");
        Assert.assertTrue(result, "Expected second slider section did not appear.");
    }

    @Test(priority = 4)
    public void testCareersNavigation() {
    	logger.info("Test: Navigate to Careers page");
        test = extent.createTest("Careers Page Navigation");

        HomePage home = new HomePage(driver);
        home.open(ConfigReader.getProperty("baseUrl"));
        home.navigateToCareers();
        ScreenshotUtil.captureScreenshot(driver, "Careers_Navigation");


        String title = driver.getTitle().toLowerCase();
        String url = driver.getCurrentUrl();

        logger.info("Current title: " + title);
        logger.info("Current URL: " + url);

        Assert.assertTrue(
            title.contains("career") || url.contains("/careers"),
            "Page title or URL should indicate Careers page");
    }

    @Test(priority = 5)
    public void testVideoPlaybackPresence() {
    	logger.info("Test: Verify presence and playability of Homebody RXP video");
        test = extent.createTest("Video Presence and Playability Test");

        HomePage home = new HomePage(driver);
        home.open(ConfigReader.getProperty("baseUrl"));
        boolean playable = home.isVideoPresentAndPlayable();
        By playButton = By.xpath("//div[contains(@id,'big_play_button_graphic')]");
        WaitUtils.waitForVisibility(driver, playButton, 10);
        ScreenshotUtil.captureScreenshot(driver, "Video_Section_Visible");

        Assert.assertTrue(playable, "Video should be visible and play button should be clickable");
    }
   }