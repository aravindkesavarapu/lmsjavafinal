Feature: User Wants To Register and Login Feature

  Scenario Outline: User Register Scenario
    Given User is On Register Page
    When User enters <firstName>,<lastName>,<emailId>,<mobileNo>,<password>,<role>
    Then User should be registered

    Examples: 
      | firstName | lastName | emailId          | mobileNo   | password | role    |
      | "anil"    | "kumar"  | "anil@gmail.com" | 9876543210 | "Ara@12" | "Admin" |

  Scenario Outline: User Login Scenario
    Given User is on Login page
    When User enters <emailId>,<password>
    Then User should be <status>

    Examples: 
      | emailId             | password |
      | "aravind@gmail.com" | "Ara@12" |
