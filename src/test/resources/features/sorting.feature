@sort
Feature: Sorting Functionality

  Background:
    Given User is on the Products page

  Scenario: User changes sorting from name descending to price ascending
    When User taps the sort button
    And User selects sort option "Name - Descending"
    Then Products should be sorted by "Name" in "Descending" order

    When User taps the sort button
    And User selects sort option "Price - Ascending"
    Then Products should be sorted by "Price" in "Ascending" order