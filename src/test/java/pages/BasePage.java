package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected AndroidDriver driver;
    protected WebDriverWait wait;
    protected WebDriverWait shortWait;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected boolean isVisible(By locator) {
        try {
            shortWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isVisible(By... locators) {
        if (locators == null || locators.length == 0) {
            return false;
        }

        for (By locator : locators) {
            if (!isVisible(locator)) {
                return false;
            }
        }

        return true;
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String value) {
        WebElement element = waitVisible(locator);
        element.click();
        element.clear();
        element.sendKeys(value);
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText();
    }

    protected void scrollToText(String text) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().text(\"" + text + "\"))"
            ));
        } catch (Exception ignored) {
            // Kalau halaman tidak scrollable, abaikan.
        }
    }
}