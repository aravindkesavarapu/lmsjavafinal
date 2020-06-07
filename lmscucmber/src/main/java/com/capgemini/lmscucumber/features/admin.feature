@admin
Feature: Admin can add,delete,update,issue books and able to view books

  Background: 
    Given Admin is on Login page
    When Admin enters "aravind@gmail.com","Ara@12"
    And Admin click on login button
    Then Admin should be logged in

  Scenario Outline: Admin should be able to add books
    Given Admin navigate to Add Book Page
    When Admin enters <bookName>,<authorName>,<publisherName>,<copies>,<bookCategory>
    Then Book Added status

    Examples: 
      | bookName              | authorName   | publisherName | copies | bookCategory |
      | "Signals and Systems" | "NagoorKani" | "Arihant"     |     12 | "EEE"        |

  Scenario: Admin should able to View Requested Books and Issue the Books
    Given Admin  navigates to Requested books page
    When Admin click on Issue button
    Then Admin can Issue the Book to the Student

  Scenario: Admin should be able to View the issued books
    Given Admin navigates to Issued Books
    Then Admin can view Issued Books

  Scenario: Admin can Update Book Details
    Given Admin is on Search Books Page
    When Admin click on Update button
    Then Admin navigates to Update Book Page
    And Admin  wants to Update "Core Java",11
    Then Admin click on Update Book Button

  Scenario: Admin can Delete Book From Library
    Given Admin is on Search Book Page
    When Admin click on Delete button
    Then Book Deleted successfully

  Scenario: Admin should be able to view book based on names
    Given Admin is on viewing book page based on names
    When Admin enters book Name as "Half Girl"
    Then show book based on book name

  Scenario: Admin should be able to view book based on author name
    Given Admin is on viewing book page based on author Name
    When Admin enters book author Name as "James"
    Then Show books based on book author name
