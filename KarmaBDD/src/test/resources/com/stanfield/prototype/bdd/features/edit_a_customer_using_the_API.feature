Feature: Edit a customer using the API
    In order to manage customers in the application
    As an ap user
    I want to be able to edit Customers


@Before_InsertCustomer
@After_DeleteCustomer
Scenario: Edit a customer using the HTTP PUT request in the API
    Given I have a link "customer"
	And I also have an entity for a "Customer"
    | firstName | lastName | age |
    | Patrick   | Willis   | 32  |
    When I perform the action "put"
    Then I receive message "200"
    And The edited entity exists