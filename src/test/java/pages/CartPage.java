package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class CartPage extends BasePage {

    private final By productImage = By.id("com.saucelabs.mydemoapp.android:id/productIV");
    private final By productTitle = By.id("com.saucelabs.mydemoapp.android:id/titleTV");
    private final By productPrice = By.id("com.saucelabs.mydemoapp.android:id/priceTV");

    private final By colorTitle = By.id("com.saucelabs.mydemoapp.android:id/colorTitleTV");
    private final By selectedColor = By.id("com.saucelabs.mydemoapp.android:id/colorIV");

    private final By minusButton = By.id("com.saucelabs.mydemoapp.android:id/minusIV");
    private final By quantityText = By.id("com.saucelabs.mydemoapp.android:id/noTV");

    private final By totalItemCount = By.id("com.saucelabs.mydemoapp.android:id/itemsTV");
    private final By totalPrice = By.id("com.saucelabs.mydemoapp.android:id/totalPriceTV");

    private final By proceedToCheckoutButton = By.id("com.saucelabs.mydemoapp.android:id/cartBt");

    public CartPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isCartPageDisplayed() {
        return isVisible(productImage, productTitle, totalItemCount, totalPrice, proceedToCheckoutButton);
    }

    public boolean isProductDisplayedInCart(String expectedProductName) {
        return getText(productTitle).equals(expectedProductName);
    }

    public boolean isProductPriceDisplayed(String expectedPrice) {
        return getText(productPrice).equals(expectedPrice);
    }

    public boolean isSelectedColorDisplayed(String expectedColor) {
        /*
         * [Certain]
         * Based on your element dump, selected color is ImageView:
         * content-desc="Displays color of selected product"
         *
         * [Risk]
         * It does not expose the actual color name "Blue".
         * So this can only verify that selected color element exists,
         * unless your app exposes color name in content-desc after selection.
         */
        return isVisible(selectedColor);
    }

    public boolean isColorTitleDisplayed() {
        return getText(colorTitle).equals("Color:");
    }

    public boolean isQuantityDisplayed(int expectedQuantity) {
        return getText(quantityText).equals(String.valueOf(expectedQuantity));
    }

    public boolean isTotalItemCountDisplayed(String expectedItemCount) {
        return getText(totalItemCount).equals(expectedItemCount);
    }

    public boolean isCartTotalPriceDisplayed(String expectedTotalPrice) {
        return getText(totalPrice).equals(expectedTotalPrice);
    }

    public void clickProceedToCheckoutButton() {
        click(proceedToCheckoutButton);
    }
}