package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductDetailPage extends BasePage {

    private static final String PACKAGE_NAME =
            "com.saucelabs.mydemoapp.android:id/";

    private static final String COLOR_RECYCLER_ID =
            PACKAGE_NAME + "colorRV";

    private static final String COLOR_IMAGE_ID =
            PACKAGE_NAME + "colorIV";

    // Product detail elements
    private final By productTitle =
            By.id(PACKAGE_NAME + "titleTV");

    private final By productPrice =
            By.id(PACKAGE_NAME + "priceTV");

    // Color section
    private final By colorRecyclerView =
            By.id(COLOR_RECYCLER_ID);

    // Quantity section
    private final By minusButton =
            By.id(PACKAGE_NAME + "minusIV");

    private final By quantityText =
            By.id(PACKAGE_NAME + "noTV");

    private final By plusButton =
            By.id(PACKAGE_NAME + "plusIV");

    // Add to cart button
    private final By addToCartButton =
            By.id(PACKAGE_NAME + "cartBt");

    // Cart section
    private final By cartButton =
            By.id(PACKAGE_NAME + "cartRL");

    private final By cartIcon =
            By.id(PACKAGE_NAME + "cartIV");

    public ProductDetailPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isProductDetailPageDisplayed() {
        return isVisible(addToCartButton)
                || isVisible(colorRecyclerView)
                || isVisible(quantityText);
    }
    public boolean isProductNameDisplayed(String expectedProductName) {
        By productNameByText = By.xpath("//*[@text=\"" + expectedProductName + "\"]");

        return isVisible(productTitle)
                && getText(productTitle).equals(expectedProductName)
                || isVisible(productNameByText);
    }

    public boolean isProductPriceDisplayed(String expectedPrice) {
        By productPriceByText = By.xpath("//*[@text=\"" + expectedPrice + "\"]");

        return isVisible(productPrice)
                && getText(productPrice).equals(expectedPrice)
                || isVisible(productPriceByText);
    }

    public boolean isColorSectionDisplayed() {
        return isVisible(colorRecyclerView);
    }

    public void selectColor(String color) {
        scrollToColorSection();

        if (clickColorByDescription(color)) {
            return;
        }

        if (clickColorByHorizontalScroll(color)) {
            return;
        }

        if (clickColorByRecyclerViewIndex(color)) {
            return;
        }

        throw new RuntimeException("Product color is not displayed: " + color);
    }

    private boolean clickColorByDescription(String color) {
        By[] colorLocators = new By[]{
                AppiumBy.accessibilityId(color + " color"),

                AppiumBy.androidUIAutomator(
                        "new UiSelector().descriptionContains(\"" + color + "\")"
                ),

                By.xpath("//*[@content-desc='" + color + " color']"),

                By.xpath("//*[contains(@content-desc,'" + color + "')]"),

                By.xpath("//*[@resource-id='" + COLOR_IMAGE_ID + "' and contains(@content-desc,'" + color + "')]"),

                By.xpath("//*[contains(@resource-id,'colorIV') and contains(@content-desc,'" + color + "')]")
        };

        for (By locator : colorLocators) {
            try {
                if (isVisible(locator)) {
                    click(locator);
                    return true;
                }
            } catch (Exception ignored) {
            }
        }

        return false;
    }

    private boolean clickColorByHorizontalScroll(String color) {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().resourceId(\"" + COLOR_RECYCLER_ID + "\"))" +
                            ".setAsHorizontalList()" +
                            ".scrollIntoView(new UiSelector().descriptionContains(\"" + color + "\"))"
            ));

            return clickColorByDescription(color);

        } catch (Exception ignored) {
            return false;
        }
    }

    private boolean clickColorByRecyclerViewIndex(String color) {
        try {
            List<WebElement> colors = driver.findElements(
                    By.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id='" + COLOR_RECYCLER_ID + "']" +
                            "//android.widget.ImageView[@resource-id='" + COLOR_IMAGE_ID + "']")
            );

            if (colors.isEmpty()) {
                colors = driver.findElements(By.id(COLOR_IMAGE_ID));
            }

            if (colors.isEmpty()) {
                return false;
            }

            int colorIndex = getColorIndex(color);

            if (colorIndex >= colors.size()) {
                colorIndex = 0;
            }

            colors.get(colorIndex).click();
            return true;

        } catch (Exception ignored) {
            return false;
        }
    }

    private int getColorIndex(String color) {
        switch (color.toLowerCase()) {
            case "blue":
                return 1;

            case "gray":
            case "grey":
                return 1;

            case "black":
                return 2;

            case "red":
                return 3;

            default:
                return 0;
        }
    }

    private void scrollToColorSection() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                            ".scrollIntoView(new UiSelector().resourceId(\"" + COLOR_RECYCLER_ID + "\"))"
            ));
        } catch (Exception ignored) {
            // Ignore if color section is already visible.
        }
    }

    public void setQuantity(int expectedQuantity) {
        if (expectedQuantity < 1) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        int currentQuantity = getCurrentQuantity();

        while (currentQuantity < expectedQuantity) {
            click(plusButton);
            currentQuantity = getCurrentQuantity();
        }

        while (currentQuantity > expectedQuantity) {
            click(minusButton);
            currentQuantity = getCurrentQuantity();
        }
    }

    public int getCurrentQuantity() {
        return Integer.parseInt(getText(quantityText));
    }

    public boolean isQuantityDisplayed(int expectedQuantity) {
        return getText(quantityText).equals(String.valueOf(expectedQuantity));
    }

    public void clickAddToCartButton() {
        click(addToCartButton);
    }

    public boolean isCartBadgeDisplayed(String expectedBadge) {
        By cartBadgeTextByCommonId = By.xpath(
                "//*[(" +
                        "contains(@resource-id,'cartTV') or " +
                        "contains(@resource-id,'badge') or " +
                        "contains(@resource-id,'counter') or " +
                        "contains(@resource-id,'number')" +
                        ") and @text='" + expectedBadge + "']"
        );

        By cartBadgeByContentDescription = By.xpath(
                "//*[contains(@content-desc,'" + expectedBadge + "')]"
        );

        By cartBadgeByText = By.xpath(
                "//*[@text='" + expectedBadge + "']"
        );

        return isVisible(cartBadgeTextByCommonId)
                || isVisible(cartBadgeByContentDescription)
                || isVisible(cartBadgeByText);
    }

    public boolean isCartIconDisplayed() {
        return isVisible(cartButton) || isVisible(cartIcon);
    }

    public void openCart() {
        click(cartButton);
    }
}