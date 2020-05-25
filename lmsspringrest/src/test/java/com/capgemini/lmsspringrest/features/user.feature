Feature: User can return or request books

  Background: 
    
    Given User is on Login page
    
    When User enters "aravind@gmail.com","aravind"
    
    Then Admin should be logged in

  Scenario: Admin should be able to request books
    
    Given Admin is on request book page
    
    When BookId  and UserId are given 12, 10402
    
    Then Book Requested Successfully

  Scenario: Admin should be able to return books
    
    Given Admin is on returning book page
    
    When Book  and User are given 12, 10402
    
    Then Book Returned Successfully
