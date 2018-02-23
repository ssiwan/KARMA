Feature: Logout from the application
  As a user, I want to be able to log out of the application 
  so that I no longer have access to the applications functionality.


  Scenario: Logout from the application
    Given I am logged in
   	When I click on the Account button
   	And I click on the Sign out button
   	Then I should see the warning message "If you want to sign in"
   	And I should "not see" the Settings link
   

  