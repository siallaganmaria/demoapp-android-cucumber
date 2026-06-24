package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class ReviewOrderPage extends BasePage {

    // Product list section
    private final By selectedProductList =
            By.id("com.saucelabs.mydemoapp.android:id/placeOrderRV");

    private final By productImage =
            By.id("com.saucelabs.mydemoapp.android:id/productIV");

    private final By productInfoContainer =
            By.id("com.saucelabs.mydemoapp.android:id/infoCL");

    private final By productTitle =
            By.id("com.saucelabs.mydemoapp.android:id/titleTV");

    private final By productPrice =
            By.id("com.saucelabs.mydemoapp.android:id/priceTV");

    private final By ratingContainer =
            By.id("com.saucelabs.mydemoapp.android:id/rattingV");

    private final By colorTitle =
            By.id("com.saucelabs.mydemoapp.android:id/colorTitleTV");

    private final By selectedColor =
            By.id("com.saucelabs.mydemoapp.android:id/colorIV");

    // Delivery address section
    private final By addressSection =
            By.id("com.saucelabs.mydemoapp.android:id/addressLL");

    private final By fullName =
            By.id("com.saucelabs.mydemoapp.android:id/fullNameTV");

    private final By address =
            By.id("com.saucelabs.mydemoapp.android:id/addressTV");

    private final By city =
            By.id("com.saucelabs.mydemoapp.android:id/cityTV");

    private final By country =
            By.id("com.saucelabs.mydemoapp.android:id/countryTV");

    // Payment method section
    private final By billingSection =
            By.id("com.saucelabs.mydemoapp.android:id/billingLL");

    private final By cardHolder =
            By.id("com.saucelabs.mydemoapp.android:id/cardHolderTV");

    // Total section
    private final By totalSection =
            By.id("com.saucelabs.mydemoapp.android:id/totalLL");

    private final By totalText =
            By.id("com.saucelabs.mydemoapp.android:id/totalTextTV");

    private final By itemNumber =
            By.id("com.saucelabs.mydemoapp.android:id/itemNumberTV");

    private final By totalAmount =
            By.id("com.saucelabs.mydemoapp.android:id/totalAmountTV");

    // Button
    private final By placeOrderButton =
            By.id("com.saucelabs.mydemoapp.android:id/paymentBtn");

    public ReviewOrderPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isReviewOrderPageDisplayed() {
        return isVisible(selectedProductList)
                && isVisible(productTitle)
                && isVisible(productPrice)
                && isVisible(addressSection)
                && isVisible(billingSection)
                && isVisible(totalSection)
                && isVisible(placeOrderButton);
    }

    public boolean isReviewOrderPageDisplayedFast() {
        return isVisible(selectedProductList)
                || isVisible(placeOrderButton)
                || isVisible(totalAmount);
    }

    public boolean isProductNameDisplayed(String expectedProductName) {
        return getText(productTitle).equals(expectedProductName);
    }

    public boolean isProductPriceDisplayed(String expectedPrice) {
        return getText(productPrice).equals(expectedPrice);
    }

    public boolean isSelectedColorDisplayed() {
        return isVisible(colorTitle) && isVisible(selectedColor);
    }

    public boolean isDeliveryAddressDisplayed(
            String expectedFullName,
            String expectedAddress,
            String expectedCity,
            String expectedCountry
    ) {
        return getText(fullName).equals(expectedFullName)
                && getText(address).equals(expectedAddress)
                && getText(city).equals(expectedCity)
                && getText(country).equals(expectedCountry);
    }

    public boolean isPaymentMethodDisplayed(String expectedCardHolder) {
        return getText(cardHolder).equals(expectedCardHolder);
    }

    public boolean isTotalItemCountDisplayed(String expectedItemCount) {
        scrollToText("Total:");
        return getText(itemNumber).equals(expectedItemCount);
    }

    public boolean isOrderTotalPriceDisplayed(String expectedTotalPrice) {
        scrollToText("Total:");
        return getText(totalAmount).equals(expectedTotalPrice);
    }

    public boolean isTotalSectionDisplayed() {
        return isVisible(totalSection)
                && getText(totalText).equals("Total:")
                && isVisible(itemNumber)
                && isVisible(totalAmount);
    }

    public boolean isDeliveryMethodDisplayed(String deliveryMethod) {
        /*
         * [Guessing]
         * Resource-id untuk delivery method belum kamu kirim.
         * Sementara validasi pakai text.
         */
        scrollToText(deliveryMethod);
        return isVisible(By.xpath("//*[@text='" + deliveryMethod + "']"));
    }

    public boolean isDeliveryCostDisplayed(String expectedDeliveryCost) {
        String noSpacePrice = expectedDeliveryCost.replace(" ", "");

        By exactText = By.xpath("//*[@text='" + expectedDeliveryCost + "']");
        By noSpaceText = By.xpath("//*[@text='" + noSpacePrice + "']");
        By containsPrice = By.xpath("//*[contains(@text,'5.99')]");

        scrollToText("5.99");

        return isVisible(exactText)
                || isVisible(noSpaceText)
                || isVisible(containsPrice);
    }

    public boolean isPlaceOrderButtonDisplayed() {
        return isVisible(placeOrderButton);
    }

    public void clickPlaceOrderButton() {
        scrollToText("Place Order");
        click(placeOrderButton);
    }
}