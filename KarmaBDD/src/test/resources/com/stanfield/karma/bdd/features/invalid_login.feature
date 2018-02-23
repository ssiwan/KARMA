Feature: Invalid Log In

  As a user, 
  when I am presented with the welcoming page,
  I do not want to be able to log into the application if I do not have proper authorization

  Scenario: Invalid Log In Attempt
    Given I am on the login page
    When I click on the Account button
    And I click on the Sign In button
    And I enter "dfsfdfd" in the Username field
    And I enter "dfsfdfd" in the Password field
    And I click the Submit button
    Then I should see an error message with the text "Failed to sign in!"
