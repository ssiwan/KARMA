Feature: Invalid Log In

  In order to prevent unauthorized access
  As a user of the application
  I want to be displayed an that I have entered invalid log in if I enter incorrect login credentials

  Scenario: Invalid Log In Attempt
    Given I am on the login page
    When I click on the Account button
    And I click on the Sign In button
    And I enter "dfsfdfd" in the Username field
    And I enter "dfsfdfd" in the Password field
    And I click the Submit button
    Then I should see an error message with the text "Failed to sign in!"
