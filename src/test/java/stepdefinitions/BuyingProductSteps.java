package stepdefinitions;

import hooks.MobileHooks;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyingProductSteps {

    private AndroidDriver driver;

    private ProductListPage productListPage;
    private ProductDetailPage productDetailPage;
    private CartPage cartPage;
    private CheckoutAddressPage checkoutAddressPage;
    private PaymentPage paymentPage;
    private ReviewOrderPage reviewOrderPage;
    private CheckoutCompletePage checkoutCompletePage;

    private void initPages() {
        if (driver == null) {
            driver = MobileHooks.driver;

            productListPage = new ProductListPage(driver);
            productDetailPage = new ProductDetailPage(driver);
            cartPage = new CartPage(driver);
            checkoutAddressPage = new CheckoutAddressPage(driver);
            paymentPage = new PaymentPage(driver);
            reviewOrderPage = new ReviewOrderPage(driver);
            checkoutCompletePage = new CheckoutCompletePage(driver);
        }
    }

    @When("User opens the product detail page for {string}")
    public void userOpensTheProductDetailPageFor(String productName) {
        initPages();

        productListPage.openProductDetail(productName);

        Assert.assertTrue(
                "Product detail page is not opened after selecting product: " + productName,
                productDetailPage.isProductDetailPageDisplayed()
        );
    }
    @Then("User should see the product name {string}")
    public void userShouldSeeTheProductName(String productName) {
        initPages();

        boolean isDisplayed;

        if (reviewOrderPage.isReviewOrderPageDisplayedFast()) {
            isDisplayed = reviewOrderPage.isProductNameDisplayed(productName);
        } else {
            isDisplayed = productDetailPage.isProductNameDisplayed(productName);
        }

        Assert.assertTrue(
                "Product name is not displayed: " + productName,
                isDisplayed
        );
    }

    @And("User should see the product price {string}")
    public void userShouldSeeTheProductPrice(String expectedPrice) {
        initPages();

        Assert.assertTrue(
                "Product price is not displayed: " + expectedPrice,
                productDetailPage.isProductPriceDisplayed(expectedPrice)
        );
    }

    @When("User selects product color {string}")
    public void userSelectsProductColor(String color) {
        initPages();

        productDetailPage.selectColor(color);
    }

    @And("User sets product quantity to {int}")
    public void userSetsProductQuantityTo(int quantity) {
        initPages();

        productDetailPage.setQuantity(quantity);
    }

    @And("User clicks the {string} button")
    public void userClicksTheButton(String buttonName) {
        initPages();

        switch (buttonName.trim().toLowerCase()) {
            case "add to cart":
                productDetailPage.clickAddToCartButton();
                break;

            case "proceed to checkout":
                cartPage.clickProceedToCheckoutButton();
                break;

            case "to payment":
                checkoutAddressPage.clickToPaymentButton();
                break;

            case "review order":
                paymentPage.clickReviewOrderButton();
                break;

            case "place order":
                reviewOrderPage.clickPlaceOrderButton();
                break;

            default:
                throw new IllegalArgumentException("Unsupported button name: " + buttonName);
        }
    }

    @Then("The cart badge should display {string}")
    public void theCartBadgeShouldDisplay(String expectedBadge) {
        initPages();

        Assert.assertTrue(
                "Cart badge is not displayed: " + expectedBadge,
                productDetailPage.isCartBadgeDisplayed(expectedBadge)
        );
    }

    @When("User opens the shopping cart page")
    public void userOpensTheShoppingCartPage() {
        initPages();

        productDetailPage.openCart();
    }

    @Then("User should see {string} in the cart")
    public void userShouldSeeProductInTheCart(String productName) {
        initPages();

        Assert.assertTrue(
                "Product is not displayed in cart: " + productName,
                cartPage.isProductDisplayedInCart(productName)
        );
    }

    @And("User should see the selected color as blue")
    public void userShouldSeeTheSelectedColorAsBlue() {
        initPages();

        Assert.assertTrue(
                "Selected color blue is not displayed in cart",
                cartPage.isSelectedColorDisplayed("Blue")
        );
    }

    @And("User should see the product quantity as {int}")
    public void userShouldSeeTheProductQuantityAs(int quantity) {
        initPages();

        Assert.assertTrue(
                "Product quantity is not displayed: " + quantity,
                cartPage.isQuantityDisplayed(quantity)
        );
    }

    @And("User should see the total item count as {string}")
    public void userShouldSeeTheTotalItemCountAs(String expectedItemCount) {
        initPages();

        boolean isDisplayed;

        if (reviewOrderPage.isReviewOrderPageDisplayedFast()) {
            isDisplayed = reviewOrderPage.isTotalItemCountDisplayed(expectedItemCount);
        } else {
            isDisplayed = cartPage.isTotalItemCountDisplayed(expectedItemCount);
        }

        Assert.assertTrue(
                "Total item count is not displayed: " + expectedItemCount,
                isDisplayed
        );
    }

    @And("User should see the cart total price {string}")
    public void userShouldSeeTheCartTotalPrice(String expectedTotalPrice) {
        initPages();

        Assert.assertTrue(
                "Cart total price is not displayed: " + expectedTotalPrice,
                cartPage.isCartTotalPriceDisplayed(expectedTotalPrice)
        );
    }

    @Then("User should be redirected to the checkout shipping address page")
    public void userShouldBeRedirectedToTheCheckoutShippingAddressPage() {
        initPages();

        Assert.assertTrue(
                "Checkout shipping address page is not displayed",
                checkoutAddressPage.isShippingAddressPageDisplayed()
        );
    }

    @When("User fills the shipping address form with:")
    public void userFillsTheShippingAddressFormWith(DataTable dataTable) {
        initPages();

        Map<String, String> formData = convertDataTableToMap(dataTable);
        checkoutAddressPage.fillShippingAddressForm(formData);
    }

    @Then("User should be redirected to the payment method page")
    public void userShouldBeRedirectedToThePaymentMethodPage() {
        initPages();

        Assert.assertTrue(
                "Payment method page is not displayed",
                paymentPage.isPaymentMethodPageDisplayed()
        );
    }

    @When("User fills the payment method form with:")
    public void userFillsThePaymentMethodFormWith(DataTable dataTable) {
        initPages();

        Map<String, String> formData = convertDataTableToMap(dataTable);
        paymentPage.fillPaymentMethodForm(formData);
    }

    @And("User selects the billing address same as shipping address option")
    public void userSelectsTheBillingAddressSameAsShippingAddressOption() {
        initPages();

        paymentPage.selectBillingAddressSameAsShippingAddress();

        Assert.assertTrue(
                "Billing address same as shipping checkbox is not selected",
                paymentPage.isBillingAddressSameAsShippingSelected()
        );
    }

    @Then("User should be redirected to the review order page")
    public void userShouldBeRedirectedToTheReviewOrderPage() {
        initPages();

        Assert.assertTrue(
                "Review order page is not displayed",
                reviewOrderPage.isReviewOrderPageDisplayed()
        );
    }

    @And("User should see the selected delivery method {string}")
    public void userShouldSeeTheSelectedDeliveryMethod(String deliveryMethod) {
        initPages();

        Assert.assertTrue(
                "Delivery method is not displayed: " + deliveryMethod,
                reviewOrderPage.isDeliveryMethodDisplayed(deliveryMethod)
        );
    }

    @And("User should see the delivery cost {string}")
    public void userShouldSeeTheDeliveryCost(String deliveryCost) {
        initPages();

        Assert.assertTrue(
                "Delivery cost is not displayed: " + deliveryCost,
                reviewOrderPage.isDeliveryCostDisplayed(deliveryCost)
        );
    }

    @And("User should see the order total price {string}")
    public void userShouldSeeTheOrderTotalPrice(String orderTotalPrice) {
        initPages();

        Assert.assertTrue(
                "Order total price is not displayed: " + orderTotalPrice,
                reviewOrderPage.isOrderTotalPriceDisplayed(orderTotalPrice)
        );
    }

    @Then("User should be redirected to the checkout complete page")
    public void userShouldBeRedirectedToTheCheckoutCompletePage() {
        initPages();

        Assert.assertTrue(
                "Checkout complete page is not displayed",
                checkoutCompletePage.isCheckoutCompletePageDisplayed()
        );
    }

    @And("User should see the title {string}")
    public void userShouldSeeTheTitle(String title) {
        initPages();

        Assert.assertTrue(
                "Title is not displayed: " + title,
                checkoutCompletePage.isTitleDisplayed(title)
        );
    }

    @And("User should see the message {string}")
    public void userShouldSeeTheMessage(String message) {
        initPages();

        Assert.assertTrue(
                "Message is not displayed: " + message,
                checkoutCompletePage.isThankYouMessageDisplayed(message)
        );
    }

    @And("User should see the confirmation message {string}")
    public void userShouldSeeTheConfirmationMessage(String confirmationMessage) {
        initPages();

        Assert.assertTrue(
                "Confirmation message is not displayed: " + confirmationMessage,
                checkoutCompletePage.isSwagMessageDisplayed(confirmationMessage)
        );
    }

    @And("User should see the delivery message {string}")
    public void userShouldSeeTheDeliveryMessage(String deliveryMessage) {
        initPages();

        Assert.assertTrue(
                "Delivery message is not displayed: " + deliveryMessage,
                checkoutCompletePage.isOrderMessageDisplayed(deliveryMessage)
        );
    }

    @And("User should see the {string} button")
    public void userShouldSeeTheButton(String buttonName) {
        initPages();

        if (buttonName.equalsIgnoreCase("Continue Shopping")) {
            Assert.assertTrue(
                    "Continue Shopping button is not displayed",
                    checkoutCompletePage.isContinueShoppingButtonDisplayed()
            );
        } else {
            throw new IllegalArgumentException("Unsupported button assertion: " + buttonName);
        }
    }

    private Map<String, String> convertDataTableToMap(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> result = new HashMap<>();

        for (Map<String, String> row : rows) {
            String field = row.get("Field");
            String value = row.get("Value");

            result.put(field, value == null ? "" : value);
        }

        return result;
    }
}