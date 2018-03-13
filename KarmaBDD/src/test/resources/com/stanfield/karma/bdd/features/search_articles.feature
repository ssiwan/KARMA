Feature: Search Articles

  As a user,
  I want to search for KAs by keyword in title
  so I can easily find information

  Scenario: Search Title keyword - tech
    Given I am logged in
    When I search for the keyword "tech"
    Then I should see at least one article in the list of articles