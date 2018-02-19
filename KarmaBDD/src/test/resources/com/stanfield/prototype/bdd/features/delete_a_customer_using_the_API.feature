Feature: Delete a customer using the API
    In order to control customers in the application
    As an api user
    I want to be able to delete customers

@Before_InsertCustomer
Scenario: Delete Customer a customer using the HTTP Delete Request
    Given I have a link "customer"
    And I have parameters
    | id | id |
    When I perform the action "delete"
    Then I receive message "200"
    
#Scenario: Try to delete a Customer that does not exist
#    Given I have a link "customer"
#     And I have parameters
#     | id | id |
#     When I perform the action "delete"
#     Then I receive message "Not Found"    