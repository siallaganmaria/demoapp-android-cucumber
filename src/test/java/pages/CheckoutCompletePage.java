package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class CheckoutCompletePage extends BasePage {

    private final By checkoutCompleteTitle =
            By.id("com.saucelabs.mydemoapp.android:id/completeTV");

    private final By thankYouMessage =
            By.id("com.saucelabs.mydemoapp.android:id/thankYouTV");

    private final By swagMessage =
            By.id("com.saucelabs.mydemoapp.android:id/swagTV");

    private final By orderMessage =
            By.id("com.saucelabs.mydemoapp.android:id/orderTV");

    private final By continueShoppingButton =
            By.id("com.saucelabs.mydemoapp.android:id/shoopingBt");

    public CheckoutCompletePage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isCheckoutCompletePageDisplayed() {
        return isVisible(checkoutCompleteTitle)
                && isVisible(thankYouMessage)
                && isVisible(swagMessage)
                && isVisible(orderMessage)
                && isVisible(continueShoppingButton);
    }

    public boolean isTitleDisplayed(String expectedTitle) {
        return getText(checkoutCompleteTitle).equals(expectedTitle);
    }

    public boolean isThankYouMessageDisplayed(String expectedMessage) {
        return getText(thankYouMessage).equals(expectedMessage);
    }

    public boolean isSwagMessageDisplayed(String expectedMessage) {
        return getText(swagMessage).equals(expectedMessage);
    }

    public boolean isOrderMessageDisplayed(String expectedMessage) {
        return getText(orderMessage).equals(expectedMessage);
    }

    public boolean isMessageDisplayed(String expectedMessage) {
        return getText(thankYouMessage).equals(expectedMessage)
                || getText(swagMessage).equals(expectedMessage)
                || getText(orderMessage).equals(expectedMessage);
    }

    public boolean isContinueShoppingButtonDisplayed() {
        return isVisible(continueShoppingButton);
    }

    public void clickContinueShoppingButton() {
        click(continueShoppingButton);
    }
}