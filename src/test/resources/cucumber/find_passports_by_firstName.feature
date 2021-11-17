Feature: Find passports by firstName
  You should be able to find a list of person's passports by their first name

  Scenario: Find all 4 passports for Franklin D. Roosevelt
    Given firstName is Franklin
    When I ask for Franklin's passports
    Then I should get 4