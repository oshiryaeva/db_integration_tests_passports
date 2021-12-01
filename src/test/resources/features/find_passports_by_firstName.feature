Feature: Find passports by firstName
  I should be able to find a list of person's passports by their first name

  Scenario Outline: Find all passports for a person by first name
    Given firstName is "<firstName>"
    When I ask for passports with first name as given
    Then I receive the list of passports
    And The list size equals <number>
    Examples:
      | firstName | number |
      | Franklin  | 4      |
      | Barack    | 3      |
