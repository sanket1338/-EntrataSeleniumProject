package utils;

import org.openqa.selenium.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.StandardCopyOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScreenshotUtil {
    private static final Logger logger = LogManager.getLogger(ScreenshotUtil.class);

    public static void captureScreenshot(WebDriver driver, String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);

            // Create screenshots directory if it doesn't exist
            Files.createDirectories(Paths.get("screenshots"));

            // Append timestamp to avoid overwriting
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String filePath = "screenshots/" + name + "_" + timestamp + ".png";

            // Save screenshot
            Files.copy(src.toPath(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot captured: " + filePath);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: ", e);
        }
    }
}
