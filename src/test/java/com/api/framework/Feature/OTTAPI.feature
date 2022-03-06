@IntigralAPI
Feature: Intigral API Automation

  @IntigralAPI
  Scenario: User Lunch OTT api and check validations of StatusCode
    Given user hits the api url
    When user get the status code
    Then user validate the status code as "200"

  @IntigralAPI
  Scenario: User hit the URL and get valid Response without Headers
    Given user hits the api url
    When user get the Response
    Then user get All the Header without Headertype

  @IntigralAPI
  Scenario: User hit the URL and get valid Response with Headers
    Given user hits the api url
    When user get the Response
    Then user get All the Response Header with Headertype

  @IntigralAPI
  Scenario: User hit the URL and get valid Response for each json path
    Given user hits the api url
    When user get the Response
    Then user get path


  @IntigralAPI
  Scenario: User hit the URL and get valid Response for each json path
    Given user hits the api url
    When user get the Response
    Then user get All details for first


  @IntigralAPI
  Scenario: User hit the URL and get valid Response for each json path
    Given user hits the api url
    When user get the Response
    Then user get details from array path
