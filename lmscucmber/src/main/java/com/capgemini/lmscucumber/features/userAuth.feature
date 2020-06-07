#Feature: User Wants To Register and Login Feature
#
  #Scenario Outline: User Register Scenario
    #Given User is On Register Page
    #When User enters <firstName>,<lastName>,<emailId>,<mobileNo>,<password>,<role>
    #Then User should be <status>
#
    #Examples: 
      #| firstName | lastName | emailId              | mobileNo   | password  | role      | status |
      #| "admin"   | "Test"   | "aravind@gmail.in"   | 9876543210 | "aravind" | "admin"   | true   |
      #| "studnet" | "Test"   | "aravindk@gmail.com" | 9876543210 | "aravind" | "student" | true   |
#
  #Scenario Outline: User Login Scenario
    #Given User is on Login page
    #When User enters <emailId>,<password>
    #Then User should be <status>
#
    #Examples: 
      #| emailId              | password  |
      #| "aravind@gmail.com"  | "aravind" |
      #| "aravindk@gmail.com" | "aravind" |
