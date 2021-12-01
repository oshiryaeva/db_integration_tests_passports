Feature: Perform CRUD operations on Person
  I should be able to create, update and remove Person

  Background:
    Given http baseUri is "http://localhost:8080/person"

  Scenario: Create person
    When I POST a person
      | id | firstName | lastName | birthDate  |
      | 47 | Sarah     | Palin    | 1990-09-09 |
      | 48 | Super     | Mario    | 1980-08-08 |
    Then http response code should be 201

  Scenario Outline: Read person
    When I GET a person with id <id>
    Then http response code should be 200
    And http response body should contain first name "<firstName>"
    And http response body should contain last name "<lastName>"
    And http response body should contain birth date "<birthDate>"
    Examples:
      | id | firstName | lastName | birthDate  |
      | 47 | Sarah     | Palin    | 1990-09-09 |
      | 48 | Super     | Mario    | 1980-08-08 |

  Scenario Outline: Update person
    When I PUT a person
      | id | firstName | lastName | birthDate  |
      | 47 | Sarah     | Connor   | 1990-09-09 |
      | 48 | Super     | Man      | 1980-08-08 |
    Then http response code should be 202
    And person with id <id> should contain first name "<firstName>"
    And person with id <id> should contain last name "<lastName>"
    Examples:
      | id | firstName | lastName |
      | 47 | Sarah     | Connor   |
      | 48 | Super     | Man      |

  @ignore
  Scenario Outline: Delete person
    When I DELETE a person with <id>
    Then http response code should be 200
    And person with id <id> should not be found
    Examples:
      | id |
      | 47 |
      | 48 |
