package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Map;

public class PaymentPage extends BasePage {

    // Title
    private final By checkoutTitle =
            By.id("com.saucelabs.mydemoapp.android:id/enterPaymentTitleTV");

    // Containers
    private final By nameContainer =
            By.id("com.saucelabs.mydemoapp.android:id/nameRL");

    private final By cardNumberContainer =
            By.id("com.saucelabs.mydemoapp.android:id/cardNumberRL");

    private final By expirationDateContainer =
            By.id("com.saucelabs.mydemoapp.android:id/expirationDateRL");

    private final By securityCodeContainer =
            By.id("com.saucelabs.mydemoapp.android:id/securityCodeRL");

    private final By paymentCard =
            By.id("com.saucelabs.mydemoapp.android:id/paymentCV");

    // Input fields
    private final By fullNameField =
            By.id("com.saucelabs.mydemoapp.android:id/nameET");

    private final By cardNumberField =
            By.id("com.saucelabs.mydemoapp.android:id/cardNumberET");

    private final By expirationDateField =
            By.id("com.saucelabs.mydemoapp.android:id/expirationDateET");

    private final By securityCodeField =
            By.id("com.saucelabs.mydemoapp.android:id/securityCodeET");

    // Checkbox
    private final By billingAddressSameAsShippingCheckbox =
            By.id("com.saucelabs.mydemoapp.android:id/billingAddressCB");

    // Button
    private final By reviewOrderButton =
            By.id("com.saucelabs.mydemoapp.android:id/paymentBtn");

    public PaymentPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isPaymentMethodPageDisplayed() {
        return isVisible(checkoutTitle)
                && isVisible(nameContainer)
                && isVisible(cardNumberContainer)
                && isVisible(expirationDateContainer)
                && isVisible(securityCodeContainer)
                && isVisible(billingAddressSameAsShippingCheckbox)
                && isVisible(paymentCard)
                && isVisible(reviewOrderButton);
    }

    public void fillPaymentMethodForm(Map<String, String> formData) {
        typeFullName(formData.get("Full Name"));
        typeCardNumber(formData.get("Card Number"));
        typeExpirationDate(formData.get("Expiration Date"));
        typeSecurityCode(formData.get("Security Code"));
    }

    public void typeFullName(String fullName) {
        type(fullNameField, fullName);
    }

    public void typeCardNumber(String cardNumber) {
        type(cardNumberField, cardNumber);
    }

    public void typeExpirationDate(String expirationDate) {
        type(expirationDateField, expirationDate);
    }

    public void typeSecurityCode(String securityCode) {
        type(securityCodeField, securityCode);
    }

    public void selectBillingAddressSameAsShippingAddress() {
        if (!isBillingAddressSameAsShippingSelected()) {
            click(billingAddressSameAsShippingCheckbox);
        }

        if (!isBillingAddressSameAsShippingSelected()) {
            throw new AssertionError("Billing address checkbox was clicked but still not checked");
        }
    }

    public boolean isBillingAddressSameAsShippingSelected() {
        WebElement checkbox = waitVisible(billingAddressSameAsShippingCheckbox);

        String checked = checkbox.getAttribute("checked");

        return checked != null && checked.equalsIgnoreCase("true");
    }

    public boolean isReviewOrderButtonDisplayed() {
        return isVisible(reviewOrderButton);
    }

    public void clickReviewOrderButton() {
        click(reviewOrderButton);
    }
}