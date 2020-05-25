Feature: Login feature

Background:



  Scenario Outline: Login Scenario
    Given User is On Login Page
    When User enters <userMailId>,<userPassword>,<userRole>
    Then User <status>

    Examples: 
      | userMailId            | userPassword | userRole  | status |
      | "aravind@gmail.com" | "aravind"    | "ADMIN"   | true   |
      | "karavind@gmail.com"    | "karavind"       | "STUDENT" | true   |
