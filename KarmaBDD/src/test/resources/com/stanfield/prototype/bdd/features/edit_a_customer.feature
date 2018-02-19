Feature: Edit a Customer

  In order to manage customers
  As a user of the application
  I want to be able to edit a customer

 @Before_InsertCustomerUsingAPI
 @After_DeleteCustomerUsingAPI
  Scenario: Edit a Customer on the edit customer page
    Given I am at the view customers page
    And I also select a customer to edit or delete 
    When I edit the customer 
    And I also navigate back to the View Customers page from Edit Customer Page
    Then The customer exists
