package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import utils.WaitUtils;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private By productsMenu = By.xpath("//div[contains(@class,'nav_link') and contains(@class,'w-dropdown-toggle') and .//div[text()='Products']]");
    private By residentPayLink = By.xpath("//div[@class='dropdown_link-heading' and text()='ResidentPay']");
    private By demoButton = By.xpath("//a[contains(@class,'w-button') and contains(text(),'Watch demo')]");
    private By careersMenu = By.xpath("//a[text()='Company']");
    private By careersLink = By.xpath("//div[@class='footer_column']//a[contains(@href, '/careers') and text()='Careers']");
    private By homebodyHeading = By.xpath("//h1[contains(text(),'Introducing Homebody RXP')]");;
    private By propertyManagementHeading = By.xpath("//h2[contains(text(),\"Today’s property\") or contains(text(),'property management built for tomorrow')]");;
    private By videoIframe = By.cssSelector("iframe[src*='youtube']");
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String url) {
        driver.get(url);
    }

    public void navigateToResidentPay() {
        new Actions(driver).moveToElement(WaitUtils.waitForVisibility(driver, productsMenu, 10)).perform();
        WaitUtils.waitForClickability(driver, residentPayLink, 10).click();
    }

    public void clickDemoButton() {
        WaitUtils.waitForClickability(driver, demoButton, 10).click();
    }
    public void navigateToCareers() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            logger.info("Scrolling to footer for Careers link...");

            // Scroll in steps to trigger lazy-loading if any
            for (int i = 0; i < 5; i++) {
                js.executeScript("window.scrollBy(0, 400);");
                Thread.sleep(500); // small delay to allow rendering
            }

            WebElement link = WaitUtils.waitForClickability(driver, careersLink, 15);
            link.click();
            logger.info("Clicked on Careers link.");
            WaitUtils.waitForPageToLoad(driver, 10);
        } catch (Exception e) {
            logger.error("Failed to navigate to Careers: " + e.getMessage());
            throw new RuntimeException("Careers navigation failed", e);
        }
    }

    public boolean waitForHomeSliderChange() {
        try {
            By homebodyHeading = By.xpath("//h1[contains(text(),'Introducing Homebody RXP')]");
            By propertyManagementHeading = By.xpath("//h2[contains(text(),\"Today’s property\") or contains(text(),'property management built for tomorrow')]");

            // Wait for first section to be visible
            WaitUtils.waitForVisibility(driver, homebodyHeading, 10);

            // Now wait for the second section to become visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement secondSlide = wait.until(ExpectedConditions.visibilityOfElementLocated(propertyManagementHeading));

            return secondSlide.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isVideoPresentAndPlayable() {
        try {
            logger.info("Scrolling to video section...");
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Scroll to the video section container
            WebElement section = driver.findElement(By.xpath("//section[contains(@class,'section_home-video')]"));
            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", section);
            Thread.sleep(2000); // Allow animation

            // Locate the play button using more general but safe xpath
            By playButton = By.xpath("//div[contains(@id,'big_play_button_graphic') and @role='button']");
            WebElement button = WaitUtils.waitForVisibility(driver, playButton, 10);

            logger.info("Attempting to click play button via JavaScript...");
            js.executeScript("arguments[0].click();", button);

            Thread.sleep(2000); // Wait for video to start playing (simulate observation)

            // If we reached here, all was successful
            return true;

        } catch (Exception e) {
            logger.error("Video not interactable or JS click failed: " + e.getMessage());
            return false;
        }
    }




}