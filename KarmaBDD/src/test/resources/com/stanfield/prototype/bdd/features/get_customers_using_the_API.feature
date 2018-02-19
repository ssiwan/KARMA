Feature: Get customers using the API
    In order to control customers in the application
    As an api user
    I want to be able to view customers

@Before_InsertCustomer
@After_DeleteCustomer
Scenario: View Customer by Id using the GET HTTP request in the API
    Given I have a link "customer"
	And I have parameters
    | id | id |
    When I perform the action "get"
    Then I receive message "OK"
#	And The fetched entity "Customer" also matches
#    | firstName | lastName | age |
#    | Patrick   | Willis   | 44  |
    And The response contains the entity "Customer"
     | firstName | lastName | age |
     | Patrick   | Willis   | 44  |
         
 @Before_InsertCustomer
 @After_DeleteCustomer
 Scenario: Get all Customers using the GET HTTP request in the API
    Given I have a link "customer"
    When I perform the action "get"
    Then I receive message "OK"
	And The fetched entity "Customer" also matches
    | firstName | lastName | age |
    | Patrick   | Willis   | 44  |    