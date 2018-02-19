Feature: Delete a Customer

  In order to manage customers
  As a user of the application
  I want to be able to delete a customer

 @Before_InsertCustomerUsingAPI
  Scenario: Delete a Customer from the edit customer web page
   Given I am at the view customers page
   And I also select a customer to edit or delete 
   When I delete the customer
   Then The customer does not exist