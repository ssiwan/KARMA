Feature: Valid Login
 As a user, when the welcoming page becomes available, I want a means to log into the application 
 so that the application can give me access based on my role.
 
  Scenario: Login into the application as general user
    Given I am on the login page
    When I click on the Account button
    And I click on the Sign In button
    And I enter "user" in the Username field
    And I enter "user" in the Password field
    And I click the Sign In button on the Sign In screen
    Then I should see the section "My Recent Articles"
    
 Scenario: Login into the application as an admin
    Given I am on the login page
    When I click on the Account button
    And I click on the Sign In button
    And I enter "admin" in the Username field
    And I enter "admin" in the Password field
    And I click the Sign In button on the Sign In screen
    Then I should see the section "My Recent Articles"

  
