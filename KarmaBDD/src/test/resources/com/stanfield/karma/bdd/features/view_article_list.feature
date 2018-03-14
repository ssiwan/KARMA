Feature: View Article List

  As a user,
  I want to be able to view all KAs
  so I can see all KAs available for me to view

  Scenario: View Article List
    Given I am logged in
    When I view all knowledge articles
    Then I should see at least one article in the list of articles