package demo.wrappers;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Wrappers {
    public static void click(WebElement elementToClick, WebDriver driver) {
        if (elementToClick.isDisplayed()) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("var rect = arguments[0].getBoundingClientRect();" +
                    "window.scrollTo({ top: rect.top + window.pageYOffset - (window.innerHeight / 2), behavior: 'smooth' });",
                    elementToClick);
            elementToClick.click();
        }
    }

    public static void sendKeys(WebDriver driver, WebElement inputBox, String keysToSend) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("var rect = arguments[0].getBoundingClientRect();" +
                "window.scrollTo({ top: rect.top + window.pageYOffset - (window.innerHeight / 2), behavior: 'smooth' });",
                inputBox);
        inputBox.clear();
        inputBox.sendKeys(keysToSend);
    }

    public static void navigate(WebDriver driver, String url) {
        if (!driver.getCurrentUrl().equals(url)) {
            driver.get(url);
        }
    }

    public static WebElement findElementWithRetry(WebDriver driver, By by, int retryCount) {
        return driver.findElement(by);
    }

    public static String capture(WebDriver driver) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(
                System.getProperty("user.dir") + File.separator + "reports" + System.currentTimeMillis() + ".png");
        String errflPath = dest.getAbsolutePath();
        FileUtils.copyFile(srcFile, dest);
        return errflPath;
    }
}
