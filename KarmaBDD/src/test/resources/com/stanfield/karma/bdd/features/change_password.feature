Feature: Change user password
  As a user, when I log into the application, I can change my password 
  so that the password is meaningful and secure

  @ignore
  Scenario: Change user password
    Given I am logged in 
   	When I click on the Account button
    And I select the password link
    And I enter a new password
    And I enter a confirm password
    And I save the password
    Then I should see the success message "Password changed!"