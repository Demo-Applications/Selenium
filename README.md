# SELENIUM TEST [With Cucumber and J-Unit]

[TOC]

### Author: Sayantan Dey

### Introduction

* Selenium test project for automating the user behavior and testing using behaviors mentioned in features
* Check below to see list of features implemented till now.
* The tags that are ignored do not have their respective output JSON file, but they might appear as child of other tags if it occurs (we can't ignore the child of tags that are NOT ignored).

### Requirements

- Java 8;

- Maven 3.x;

- Dependencies : The dependencies are mentioned in   [pom]: selenium_test/pom.xml  file

  ><img src="./docs/logos/selenium.png" alt="Selenium" style="zoom:20%;" />     <img src="./docs/logos/cucumber.png" alt="cucumber" style="zoom:25%;" />     <img src="C:\Users\Sayantan\Documents\Git Repos\Selenium\docs\logos\JUnit.png" style="zoom: 33%;" />

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
├───docs
│   └───logos
└───selenium_test
    ├───.settings
    ├───src
    │   ├───main
    │   │   └───java
    │   │       └───dey
    │   │           └───sayantan
    │   │               └───selenium
    │   └───test
    │       ├───java
    │       │   └───dey
    │       │       └───sayantan
    │       │           └───selenium
    │       │               └───youtube
    │       │                   ├───features
    │       │                   ├───runner
    │       │                   ├───steps
    │       │                   └───utils
    │       └───resources
    └───target
        ├───classes
        │   ├───dey
        │   │   └───sayantan
        │   │       └───selenium
        │   └───META-INF
        │       └───maven
        │           └───sayantan
        │               └───selenium_java
        ├───generated-sources
        │   └───annotations
        ├───generated-test-sources
        │   └───test-annotations
        ├───maven-status
        │   └───maven-compiler-plugin
        │       ├───compile
        │       │   └───default-compile
        │       └───testCompile
        │           └───default-testCompile
        ├───surefire-reports
        └───test-classes
            ├───dey
            │   └───sayantan
            │       └───selenium
            │           └───youtube
            │               ├───features
            │               ├───runner
            │               ├───steps
            │               └───utils
            └───features
```
