Feature: View a list of customers

  In order to manage customers in the Angular prototype application
  As a user of the application
  I want to be able view a list of customers
  on the main page

  @LaunchApplication
  Scenario: View a list of customers on the view customers web page
    Given I am at the view customers page 
    When I view the page content
    Then The title "SSISA OS - Angular Prototype" should be present
    And The add button should also be visible
    And The customers button should also be visible