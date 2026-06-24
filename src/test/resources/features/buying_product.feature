@buying
Feature: Buying Product Functionality

  Scenario: Add product to the cart after successful login
    Given User is on login page
    When User inputs username
    And User inputs password
    And User clicks login button
    Then User success to access Homepage

    When User opens the product detail page for "Sauce Labs Backpack"
    Then User should see the product name "Sauce Labs Backpack"
    And User should see the product price "$ 29.99"

    When User selects product color "Blue"
    And User sets product quantity to 2
    And User clicks the "Add to cart" button
    Then The cart badge should display "2"

    When User opens the shopping cart page
    Then User should see "Sauce Labs Backpack" in the cart
    And User should see the selected color as blue
    And User should see the product quantity as 2
    And User should see the total item count as "2 Items"
    And User should see the cart total price "$ 59.98"

    When User clicks the "Proceed To Checkout" button
    Then User should be redirected to the checkout shipping address page

    When User fills the shipping address form with:
      | Field          | Value             |
      | Full Name      | Maria             |
      | Address Line 1 | jln lebak bulus 1 |
      | Address Line 2 |                   |
      | City           | Jakarta           |
      | State/Region   | Jakarta selatan   |
      | Zip Code       | 21312             |
      | Country        | indonesia         |
    And User clicks the "To Payment" button
    Then User should be redirected to the payment method page

    When User fills the payment method form with:
      | Field           | Value             |
      | Full Name       | MMaria            |
      | Card Number     | 2343242342323423  |
      | Expiration Date | 12/32             |
      | Security Code   | 343               |
    And User selects the billing address same as shipping address option
    And User clicks the "Review Order" button
    Then User should be redirected to the review order page

    And User should see the product name "Sauce Labs Backpack"
    And User should see the total item count as "2 Items"
    And User should see the order total price "$ 65.97"

    When User clicks the "Place Order" button
    Then User should be redirected to the checkout complete page
    And User should see the title "Checkout Complete"
    And User should see the message "Thank you for your order"
    And User should see the confirmation message "Your new swag is on its way"
    And User should see the delivery message "Your order has been dispatched and will arrive as fast as the pony gallops!"
    And User should see the "Continue Shopping" button