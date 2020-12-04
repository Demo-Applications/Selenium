#Author: Sayantan Dey
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@YouTube
Feature: Youtube Video Select and Search

  @video-click @home-page
  Scenario: Clicking any video from the homepage
    Given I have youtube homepage open
    When I choose a video
    And open it in a new tab
    And switch to that tab
    Then i can see video page opened
    And video starts playing

  @video-search
  Scenario Outline: Searching for a specific video
    Given I have youtube web page open
    When I search for this <query>
    Then I verify the <webpage_title> in page title
    And I see <query> is present in the searchbox of new page
    And I see webpage url is modified with <query_string>

    Examples: 
      | query       | webpage_title | query_string |
      | 'word'      | 'word'        | 'word'       |
      | 'two words' | 'two words'   | 'two+words'  |
