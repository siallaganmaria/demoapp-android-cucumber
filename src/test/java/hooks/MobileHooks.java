package hooks;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class MobileHooks {

    public static AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();

        options.setPlatformName("Android");
        options.setDeviceName("Android Emulator");
        options.setAutomationName("UiAutomator2");

        options.setApp("C:/Users/1368/Downloads/Stock Bibit/mda-2.2.0-25.apk");

        options.setAutoGrantPermissions(true);
        options.setNoReset(true);
        options.setFullReset(false);

        options.setCapability("appium:adbExecTimeout", 60000);
        options.setCapability("appium:androidInstallTimeout", 120000);
        options.setCapability("appium:appWaitDuration", 30000);
        options.setCapability("appium:appWaitActivity", "*");

        URL appiumServerUrl = URI.create("http://127.0.0.1:4723").toURL();

        driver = new AndroidDriver(appiumServerUrl, options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));

        handleAndroidCompatibilityPopup();
    }

    private void handleAndroidCompatibilityPopup() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(8));

        try {
            By popupTitle = AppiumBy.xpath("//*[@text='Android App Compatibility']");

            shortWait.until(
                    ExpectedConditions.visibilityOfElementLocated(popupTitle)
            );

            List<WebElement> dontShowAgainButton = driver.findElements(
                    AppiumBy.xpath("//*[contains(@text, 'Show Again')]")
            );

            if (!dontShowAgainButton.isEmpty()) {
                dontShowAgainButton.get(0).click();
                return;
            }

            List<WebElement> okButton = driver.findElements(
                    AppiumBy.xpath("//*[@text='OK']")
            );

            if (!okButton.isEmpty()) {
                okButton.get(0).click();
            }

        } catch (TimeoutException ignored) {
            // Popup tidak muncul, lanjut test
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}