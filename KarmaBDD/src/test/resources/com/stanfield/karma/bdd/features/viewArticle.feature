Feature: View Article

  As a user,
  I want to view an KAs
  so I can read it's content

  Scenario: View Article
    Given I am logged in
    When I search for the keyword "Technical Architecture"
    And I click on the first Article
    Then I should see the Article Content "Technical Architecture"