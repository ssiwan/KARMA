Feature: Add a Customer

  In order to manage customers
  As a user of the application
  I want to be able to add a customer

 @After_DeleteWebCustomer
  Scenario: Add a Customer on the add customer web page
    Given I am at the view customers page
    And I also navigate to the add customer page  
    When I add a customer 
    And I also navigate back to the View Customers page from Add Customer Page  
    Then The customer exists  
