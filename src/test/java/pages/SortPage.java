package pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class SortPage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    private static final String APP_PACKAGE = "com.saucelabs.mydemoapp.android";

    // Products page
    private final By productsTitle = By.id(APP_PACKAGE + ":id/productTV");

    // Sort button
    private final By sortButton = By.id(APP_PACKAGE + ":id/sortIV");

    // Sort popup title
    private final By sortPopupTitle = By.id(APP_PACKAGE + ":id/sortTV");

    // Product data
    private final By productNames = By.id(APP_PACKAGE + ":id/titleTV");
    private final By productPrices = By.id(APP_PACKAGE + ":id/priceTV");

    public SortPage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public boolean isProductsPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productsTitle)
        ).isDisplayed();
    }

    public void selectSortOption(String sortOption) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(sortPopupTitle)
        );

        By optionLocator = getSortOptionLocator(sortOption);

        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(optionLocator)
        );

        option.click();

        wait.until(
                ExpectedConditions.invisibilityOfElementLocated(sortPopupTitle)
        );
    }

    public void tapSortButton() {
        wait.until(
                ExpectedConditions.elementToBeClickable(sortButton)
        ).click();
    }

    private By getSortOptionLocator(String sortOption) {
        switch (sortOption) {

            case "Name - Descending":
                return AppiumBy.xpath(
                        "//*[@content-desc='Descending order by name' " +
                                "or @text='Name - Descending' " +
                                "or @resource-id='" + APP_PACKAGE + ":id/nameDescCL']"
                );

            case "Price - Ascending":
                return AppiumBy.xpath(
                        "//*[@content-desc='Ascending order by price' " +
                                "or @text='Price - Ascending' " +
                                "or @resource-id='" + APP_PACKAGE + ":id/priceAscCL']"
                );

            default:
                throw new IllegalArgumentException("Sort option is not supported: " + sortOption);
        }
    }

    public List<String> getProductNames() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(productNames)
        );

        return driver.findElements(productNames)
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(productPrices)
        );

        return driver.findElements(productPrices)
                .stream()
                .map(WebElement::getText)
                .map(this::convertPriceToDouble)
                .collect(Collectors.toList());
    }

    private double convertPriceToDouble(String priceText) {
        return Double.parseDouble(
                priceText.replaceAll("[^0-9.]", "")
        );
    }
}