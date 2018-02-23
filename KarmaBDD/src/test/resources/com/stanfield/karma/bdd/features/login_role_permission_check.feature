Feature: Login Role Permission Check
  As a user, I want to be able to log out of the application 
  so that I no longer have access to the applications functionality.


  Scenario: Admin Permission Check
    Given I am logged in as Admin
   	When I look for the Administration Tab
   	Then whether I should see the Tab is "true"

   
   Scenario: User Permission Check
   Given I am logged in
   	When I look for the Administration Tab
   	Then whether I should see the Tab is "false"
   
