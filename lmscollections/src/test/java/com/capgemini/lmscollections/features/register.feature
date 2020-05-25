Feature: User Registration Feature

  Scenario Outline: User Registration Test Scenario
    Given User is On Registration Page
    When User enters <userId>,<userName>,<userEmailId>,<userPassword>,<userRole>
    Then User is Registered <status>

    Examples: 
      | userId | userName  | userEmailId           | userPassword | userRole  | status |
      |    500 | "aravind" | "aravind11@gmail.com" | "aravind"    | "ADMIN"   | true   |
      |    501 | "anil"    | "anil11@gmail.com"    | "anil"       | "STUDENT" | true   |
