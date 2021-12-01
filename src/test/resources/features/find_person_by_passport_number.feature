Feature: Find person by their passport's full number
  I should be able to find the person's name and date of birth by their passport full number

  Scenario Outline: Find information on a person by their passport number
    Given serial number is "<serialNumber>"
    And number is "<number>"
    When I ask for person with serial number and number as given
    Then I receive the person
    And the person's first name is "<firstName>"
    And the person's last name is "<lastName>"
    And the person's birth date is "<birthDate>"


    Examples:
      | serialNumber | number | firstName   | lastName  | birthDate  |
      | 3524         | 849279 | Abraham     | Lincoln   | 2011-02-24 |
      | 9999         | 951113 | Thomas      | Jefferson | 1985-08-02 |
      | 6477         | 736724 | John Quincy | Adams     | 1992-08-15 |
    
