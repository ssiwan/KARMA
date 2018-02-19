Feature: Create a customer using the API
    In order to control customers in the application
    As an api user
    I want to be able to create Customers

@After_DeleteCustomer
Scenario: Create a customer using post HTTP request in the API
    Given I have a link "customer"
	And I also have an entity for a "Customer"
    | firstName | lastName | age |
    | Robert    | Marley   | 12  |
    When I perform the action "post"
    Then I receive message "201"
    And The response contains the entity "Customer"
    | firstName | lastName | age |
    | Robert    | Marley   | 12  |       