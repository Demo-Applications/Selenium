# SELENIUM TEST [With Cucumber and J-Unit]

# Table of Contents
1. [Introduction](#Introduction)
2. [Requirements](#Requirements)
3. [Running The Test](#Running-The-Test)
4. [Existing Feature](#Existing-Feature)
5. [Project Structure](#Project-Structure)

### Author: Sayantan Dey

### Introduction

* Selenium test project for automating the user behavior and testing using behaviors mentioned in features
* Check below to see list of features implemented till now.
* The tags that are ignored do not have their respective output JSON file, but they might appear as child of other tags if it occurs (we can't ignore the child of tags that are NOT ignored).

### Requirements

- Java 8;

- Maven 3.x;

- Dependencies : The dependencies are mentioned in   [pom]: selenium_test/pom.xml  file

  > <img src="docs/logos/selenium.png" width="78">         <img src="docs/logos/cucumber.png" width="78">        <img src="docs/logos/JUnit.png" width="88">     

## Running The Test

Run the test using maven CLI

```bash
~$ mvn clean test
```

## Existing Feature

#### 1. YouTube :  

​       a. Randomly select a video on homepage and opening it new tab and verify it's played

​       b. Searching for a video and verify



## Project Structure

```
Selenium
│   README.md
│
├───docs
│   └───logos
│           cucumber.png
│           JUnit.png
│           selenium.png
│
└───selenium_test
    │   pom.xml
    │
    └───src
        ├───main
        │   └───java
        │       └───dey
        │           └───sayantan
        │               └───selenium
        │                       SeleniumMain.java
        │
        └───test
            └───java
                └───dey
                    └───sayantan
                        └───selenium
                            └───youtube
                                ├───features
                                │       Youtube.feature
                                │
                                ├───runner
                                │       JUnitRunner.java
                                │
                                ├───steps
                                │       YoutubeHomeTest.java
                                │       YoutubeSearchTest.java
                                │
                                └───utils
                                        YoutubeTestUtil.java
    
```
