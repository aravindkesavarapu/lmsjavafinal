Feature: Admin can add,delete,update,issue books

  Background: 
    
    Given User is on Login page
    
    When User enters "aravind@gmail.com","aravind"
    
    Then Admin should be logged in

  Scenario Outline: Admin should be able to add books
    
    Given Admin is on adding book page
    
    When Admin enters <bookName>,<authorName>,<publisherName>,<copies>,<bookCategory>
    
    Then Book <status>

    Examples: 
    
      | bookName         | authorName | publisherName | copies | bookCategory | status               |
      | "Modern Physics" | "HCVerma"  | "Arihant"     |     12 | "GEN"        | "Added Successfully" |

  Scenario: Admin should be able to remove books
    
    Given Admin is on removing book page
    
    When Admin enters 13
    
    Then Book deleted successfully

  Scenario: Admin should be able to issue books
    
    Given Admin is on issuing book page
    
    When Admin enters 10402,12
    
    Then Book issued successfully
