@student
Feature: Student can request,return and able to view books

  Background: 
    Given Student is on Login page
    When Student enters "aravindk@gmail.com","aravind"
    Then Student should be logged in

  Scenario: Student should be able to request books
    Given Student is on request book page
    When Student clicks on Request Button
    Then Book Requested Successfully

  Scenario: Student should be able to return books
    Given Student is on returning book page
    When Student clicks on return button
    Then Book Returned Successfully

  Scenario: Student should be able to view books based on names
    Given Student is on  viewing books page based on names
    When Student enters "Signals and Systems"
    Then show books based on name

  Scenario: Student should be able to view books based on author name
    Given Student is on  viewing books page based on author Name
    When Student enters author name "James"
    Then show books based on  author name

  #Scenario: Student able to login and logout
    #Given Student is on home page
    #When Student click on logout
    #Then Student loggedout
