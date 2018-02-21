Feature: View Welcome page

  In order to know that I have launched the application successfully
  As a user of the application
  I want to be able view the welcome page

  Scenario: View welcome web page
    Given I am at the welcome page 
    When I view the page content
    Then The title "Welcome to Karma" should be present
