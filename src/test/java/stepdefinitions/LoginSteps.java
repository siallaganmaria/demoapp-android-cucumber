package stepdefinitions;

import hooks.MobileHooks;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import pages.LoginPage;

public class LoginSteps {

    private LoginPage loginPage;

    @Given("User is on login page")
    public void userIsOnLoginPage() {
        loginPage = new LoginPage(MobileHooks.driver);
        loginPage.openLoginPage();

        Assert.assertTrue(
                "Login page is not displayed",
                loginPage.isLoginPageDisplayed()
        );
    }

    @When("User inputs username")
    public void userInputsUsername() {
        loginPage.enterUsername("bod@example.com");
    }

    @And("User inputs password")
    public void userInputsPassword() {
        loginPage.enterPassword("10203040");
    }

    @When("User inputs valid username")
    public void userInputsValidUsername() {
        loginPage.enterUsername("bod@example.com");
    }

    @When("User inputs valid password")
    public void userInputsValidPassword() {
        loginPage.enterPassword("10203040");
    }

    @When("User inputs invalid username")
    public void userInputsInvalidUsername() {
        loginPage.enterUsername("wrong@example.com");
    }

    @And("User inputs invalid password")
    public void userInputsInvalidPassword() {
        loginPage.enterPassword("wrongpassword");
    }

    @And("User clicks login button")
    public void userClicksLoginButton() {
        loginPage.tapLogin();
    }

    @Then("User success to access Homepage")
    public void userSuccessToAccessHomepage() {
        Assert.assertTrue(
                "Homepage is not displayed after login",
                loginPage.isHomepageDisplayed()
        );
    }

    @Then("User failed to login because password is required")
    public void userFailedToLoginBecausePasswordIsRequired() {
        Assert.assertTrue(
                "Password required error message is not displayed",
                loginPage.isPasswordRequiredErrorDisplayed()
        );

        Assert.assertTrue(
                "User should stay on login page after password validation failed",
                loginPage.isLoginPageDisplayed()
        );
    }

    @Then("User failed to login because username is required")
    public void userFailedToLoginBecauseUsernameIsRequired() {
        Assert.assertTrue(
                "Username required error message is not displayed",
                loginPage.isUsernameRequiredErrorDisplayed()
        );

        Assert.assertTrue(
                "User should stay on login page after username validation failed",
                loginPage.isLoginPageDisplayed()
        );
    }

    @Then("User failed to login")
    public void userFailedToLogin() {
        Assert.assertTrue(
                "Login error message is not displayed",
                loginPage.isLoginErrorDisplayed()
        );

        Assert.assertTrue(
                "User should stay on login page after failed login",
                loginPage.isLoginPageDisplayed()
        );
    }
}