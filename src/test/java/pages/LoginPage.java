package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class LoginPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);
    private static final Duration SHORT_TIMEOUT = Duration.ofSeconds(3);

    // Android system popup
    private final By androidDontShowAgainButton = By.id("android:id/button1");

    // Home / Product page
    private final By productTitle = By.id("com.saucelabs.mydemoapp.android:id/productTV");

    // Menu
    private final By menuButton = By.id("com.saucelabs.mydemoapp.android:id/menuIV");

    private final List<By> loginMenuItemLocators = Arrays.asList(
            AppiumBy.accessibilityId("Login Menu Item"),
            AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Login\")"),
            AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Log In\")")
    );

    // Login form
    private final By usernameField = By.id("com.saucelabs.mydemoapp.android:id/nameET");
    private final By passwordField = By.id("com.saucelabs.mydemoapp.android:id/passwordET");
    private final By loginButton = By.id("com.saucelabs.mydemoapp.android:id/loginBtn");

    // Validation error messages
    private final By usernameRequiredError = By.id("com.saucelabs.mydemoapp.android:id/nameErrorTV");
    private final By passwordRequiredError = By.id("com.saucelabs.mydemoapp.android:id/passwordErrorTV");

    // General login error fallback locators
    private final List<By> loginErrorLocators = Arrays.asList(
            By.id("com.saucelabs.mydemoapp.android:id/errorTV"),
            By.id("com.saucelabs.mydemoapp.android:id/loginErrorTV"),
            By.id("com.saucelabs.mydemoapp.android:id/nameErrorTV"),
            By.id("com.saucelabs.mydemoapp.android:id/passwordErrorTV"),
            AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Provided credentials\")"),
            AppiumBy.androidUIAutomator("new UiSelector().textContains(\"do not match\")"),
            AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Username\")"),
            AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Password\")"),
            AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Enter Password\")"),
            AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Username is required\")")
    );

    public LoginPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    public void openLoginPage() {
        dismissAndroidPopupIfPresent();

        // Case 1: App already opens on Login page
        if (isLoginPageDisplayedWithShortWait()) {
            return;
        }

        // Case 2: App opens on Home/Product page or another screen where menu exists
        if (isDisplayedWithShortWait(menuButton)) {
            tap(menuButton);
            tapFirstAvailable(loginMenuItemLocators);
            waitForLoginPageToBeDisplayed();
            return;
        }

        // Case 3: App is in an unexpected screen/state
        throw new TimeoutException(
                "Unable to open Login page. Menu button and Login form were not found. Current activity: "
                        + driver.currentActivity()
        );
    }

    public boolean isLoginPageDisplayed() {
        return isDisplayed(usernameField)
                && isDisplayed(passwordField)
                && isDisplayed(loginButton);
    }

    public void enterUsername(String username) {
        WebElement element = waitUntilVisible(usernameField);
        element.clear();
        element.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement element = waitUntilVisible(passwordField);
        element.clear();
        element.sendKeys(password);
    }

    public void tapLogin() {
        tap(loginButton);
    }

    public boolean isHomepageDisplayed() {
        return isDisplayed(productTitle);
    }

    public boolean isPasswordRequiredErrorDisplayed() {
        try {
            WebElement errorElement = waitUntilVisible(passwordRequiredError);
            String errorText = errorElement.getText().trim();

            return errorElement.isDisplayed()
                    && errorText.equalsIgnoreCase("Enter Password");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isUsernameRequiredErrorDisplayed() {
        try {
            WebElement errorElement = waitUntilVisible(usernameRequiredError);
            String errorText = errorElement.getText().trim();

            return errorElement.isDisplayed()
                    && errorText.equalsIgnoreCase("Username is required");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isLoginErrorDisplayed() {
        for (By locator : loginErrorLocators) {
            if (isDisplayedWithShortWait(locator)) {
                return true;
            }
        }

        return false;
    }

    private void waitForLoginPageToBeDisplayed() {
        waitUntilVisible(usernameField);
        waitUntilVisible(passwordField);
        waitUntilVisible(loginButton);
    }

    private void dismissAndroidPopupIfPresent() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, SHORT_TIMEOUT);
            WebElement button = shortWait.until(
                    ExpectedConditions.elementToBeClickable(androidDontShowAgainButton)
            );
            button.click();
        } catch (TimeoutException ignored) {
            // Android popup does not appear, continue test.
        }
    }

    private WebElement waitUntilVisible(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    private void tap(By locator) {
        wait.until(
                ExpectedConditions.elementToBeClickable(locator)
        ).click();
    }

    private void tapFirstAvailable(List<By> locators) {
        for (By locator : locators) {
            try {
                WebDriverWait shortWait = new WebDriverWait(driver, SHORT_TIMEOUT);
                WebElement element = shortWait.until(
                        ExpectedConditions.elementToBeClickable(locator)
                );
                element.click();
                return;
            } catch (TimeoutException ignored) {
                // Try next locator
            }
        }

        throw new TimeoutException("Login menu item was not found after opening menu.");
    }

    private boolean isDisplayed(By locator) {
        try {
            return waitUntilVisible(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private boolean isDisplayedWithShortWait(By locator) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, SHORT_TIMEOUT);
            return shortWait.until(
                    ExpectedConditions.visibilityOfElementLocated(locator)
            ).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private boolean isLoginPageDisplayedWithShortWait() {
        return isDisplayedWithShortWait(usernameField)
                && isDisplayedWithShortWait(passwordField)
                && isDisplayedWithShortWait(loginButton);
    }
}