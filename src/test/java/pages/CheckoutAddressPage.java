package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

import java.util.Map;

public class CheckoutAddressPage extends BasePage {

    // Label / container
    private final By fullNameLabel =
            By.id("com.saucelabs.mydemoapp.android:id/fullNameTV");

    private final By fullNameContainer =
            By.id("com.saucelabs.mydemoapp.android:id/fullNameRL");

    private final By address1Container =
            By.id("com.saucelabs.mydemoapp.android:id/address1RL");

    private final By address2Container =
            By.id("com.saucelabs.mydemoapp.android:id/address2RL");

    private final By cityContainer =
            By.id("com.saucelabs.mydemoapp.android:id/cityRL");

    private final By stateContainer =
            By.id("com.saucelabs.mydemoapp.android:id/stateRL");

    private final By zipContainer =
            By.id("com.saucelabs.mydemoapp.android:id/zipRL");

    private final By countryContainer =
            By.id("com.saucelabs.mydemoapp.android:id/countryRL");

    private final By paymentCard =
            By.id("com.saucelabs.mydemoapp.android:id/paymentCV");

    // Input fields
    private final By fullNameField =
            By.xpath("//android.widget.RelativeLayout[@resource-id='com.saucelabs.mydemoapp.android:id/fullNameRL']//android.widget.EditText");

    private final By addressLine1Field =
            By.id("com.saucelabs.mydemoapp.android:id/address1ET");

    private final By addressLine2Field =
            By.id("com.saucelabs.mydemoapp.android:id/address2ET");

    private final By cityField =
            By.id("com.saucelabs.mydemoapp.android:id/cityET");

    private final By stateRegionField =
            By.id("com.saucelabs.mydemoapp.android:id/stateET");

    private final By zipCodeField =
            By.id("com.saucelabs.mydemoapp.android:id/zipET");

    private final By countryField =
            By.id("com.saucelabs.mydemoapp.android:id/countryET");

    // Button
    private final By toPaymentButton =
            By.id("com.saucelabs.mydemoapp.android:id/paymentBtn");

    public CheckoutAddressPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isShippingAddressPageDisplayed() {
        return isVisible(fullNameLabel)
                && isVisible(fullNameContainer)
                && isVisible(address1Container)
                && isVisible(address2Container)
                && isVisible(cityContainer)
                && isVisible(stateContainer)
                && isVisible(zipContainer)
                && isVisible(countryContainer);
    }

    public void fillShippingAddressForm(Map<String, String> formData) {
        typeFullName(formData.get("Full Name"));
        typeAddressLine1(formData.get("Address Line 1"));
        typeAddressLine2(formData.get("Address Line 2"));
        typeCity(formData.get("City"));
        typeStateRegion(formData.get("State/Region"));
        typeZipCode(formData.get("Zip Code"));
        typeCountry(formData.get("Country"));
    }

    public void typeFullName(String fullName) {
        type(fullNameField, fullName);
    }

    public void typeAddressLine1(String addressLine1) {
        type(addressLine1Field, addressLine1);
    }

    public void typeAddressLine2(String addressLine2) {
        if (addressLine2 != null && !addressLine2.trim().isEmpty()) {
            type(addressLine2Field, addressLine2);
        }
    }

    public void typeCity(String city) {
        type(cityField, city);
    }

    public void typeStateRegion(String stateRegion) {
        type(stateRegionField, stateRegion);
    }

    public void typeZipCode(String zipCode) {
        type(zipCodeField, zipCode);
    }

    public void typeCountry(String country) {
        type(countryField, country);
    }

    public boolean isToPaymentButtonDisplayed() {
        return isVisible(toPaymentButton);
    }

    public void clickToPaymentButton() {
        click(toPaymentButton);
    }
}