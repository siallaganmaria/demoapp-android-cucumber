@login
Feature: Login Functionality

  Scenario: User login with valid username and password
    Given User is on login page
    When User inputs username
    And User inputs password
    And User clicks login button
    Then User success to access Homepage

  Scenario: User login with valid username and without password
    Given User is on login page
    When User inputs valid username
    And User clicks login button
    Then User failed to login because password is required

  Scenario: User login without username and valid password
    Given User is on login page
    When User inputs valid password
    And User clicks login button
    Then User failed to login because username is required