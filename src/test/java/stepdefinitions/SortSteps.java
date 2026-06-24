package stepdefinitions;

import hooks.MobileHooks;
import io.cucumber.java.en.*;
import org.junit.Assert;
import pages.SortPage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortSteps {

    SortPage sortPage;

    @Given("User is on the Products page")
    public void userIsOnTheProductsPage() {
        sortPage = new SortPage(MobileHooks.driver);

        Assert.assertTrue(
                "User is not on Products page",
                sortPage.isProductsPageDisplayed()
        );
    }

    @When("User taps the sort button")
    public void userTapsTheSortButton() {
        sortPage.tapSortButton();
    }

    @When("User selects sort option {string}")
    public void userSelectsSortOption(String sortOption) {
        sortPage.selectSortOption(sortOption);
    }

    @Then("Products should be sorted by {string} in {string} order")
    public void productsShouldBeSortedByInOrder(String field, String order) {

        if (field.equalsIgnoreCase("Name") && order.equalsIgnoreCase("Descending")) {

            List<String> actualProductNames = sortPage.getProductNames();
            List<String> expectedProductNames = new ArrayList<>(actualProductNames);

            expectedProductNames.sort(String.CASE_INSENSITIVE_ORDER.reversed());
            System.out.println("Actual product names: " + actualProductNames);
            System.out.println("Expected product names: " + expectedProductNames);

            Assert.assertEquals(
                    "Products are not sorted by name descending",
                    expectedProductNames,
                    actualProductNames
            );

        } else if (field.equalsIgnoreCase("Price") && order.equalsIgnoreCase("Ascending")) {

            List<Double> actualProductPrices = sortPage.getProductPrices();
            List<Double> expectedProductPrices = new ArrayList<>(actualProductPrices);

            expectedProductPrices.sort(Comparator.naturalOrder());

            Assert.assertEquals(
                    "Products are not sorted by price ascending",
                    expectedProductPrices,
                    actualProductPrices
            );

        } else {
            Assert.fail("Unsupported sorting condition: " + field + " - " + order);
        }
    }
}