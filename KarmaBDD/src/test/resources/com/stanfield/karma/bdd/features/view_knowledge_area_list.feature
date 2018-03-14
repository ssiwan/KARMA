Feature: View Knowledge Area List

  As a user,
  I want to be able to view all KAs
  so I can see all KAs available for me to view

  Scenario: View Knowledge Area List
    Given I am logged in
    When I view all knowledge areas
    Then I should see at least one knowledge area in the list of areas