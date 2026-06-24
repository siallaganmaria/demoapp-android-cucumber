package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class ProductListPage extends BasePage {

    private static final String PACKAGE_NAME =
            "com.saucelabs.mydemoapp.android:id/";

    private static final String PRODUCT_TITLE_ID =
            PACKAGE_NAME + "titleTV";

    private static final String PRODUCT_IMAGE_ID =
            PACKAGE_NAME + "productIV";

    private final By productRecyclerView =
            By.id(PACKAGE_NAME + "productRV");

    /*
     * Marker halaman Product Detail.
     * Tombol Add to cart hanya muncul setelah masuk detail product.
     */
    private final By addToCartButton =
            By.id(PACKAGE_NAME + "cartBt");

    public ProductListPage(AndroidDriver driver) {
        super(driver);
    }

    public boolean isProductListDisplayed() {
        return isVisible(productRecyclerView);
    }

    public void openProductDetail(String productName) {
        scrollToText(productName);

        By productCardGrandParent = By.xpath(
                "//android.widget.TextView[@resource-id='" + PRODUCT_TITLE_ID + "' and @text=\"" + productName + "\"]" +
                        "/parent::android.view.ViewGroup/parent::android.view.ViewGroup"
        );

        By productCardParent = By.xpath(
                "//android.widget.TextView[@resource-id='" + PRODUCT_TITLE_ID + "' and @text=\"" + productName + "\"]" +
                        "/parent::android.view.ViewGroup"
        );

        By productImageInsideCard = By.xpath(
                "//android.view.ViewGroup[.//android.widget.TextView[@resource-id='" + PRODUCT_TITLE_ID + "' and @text=\"" + productName + "\"]]" +
                        "//android.widget.ImageView[@resource-id='" + PRODUCT_IMAGE_ID + "']"
        );

        By productTitle = By.xpath(
                "//android.widget.TextView[@resource-id='" + PRODUCT_TITLE_ID + "' and @text=\"" + productName + "\"]"
        );

        if (tryClickAndWaitProductDetail(productCardGrandParent)) {
            return;
        }

        if (tryClickAndWaitProductDetail(productCardParent)) {
            return;
        }

        if (tryClickAndWaitProductDetail(productImageInsideCard)) {
            return;
        }

        if (tryClickAndWaitProductDetail(productTitle)) {
            return;
        }

        throw new RuntimeException("Failed to open product detail page for product: " + productName);
    }

    private boolean tryClickAndWaitProductDetail(By locator) {
        try {
            click(locator);

            if (isVisible(addToCartButton)) {
                return true;
            }

        } catch (Exception ignored) {
        }

        return false;
    }
}