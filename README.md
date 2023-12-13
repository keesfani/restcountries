# RESTCountries Solution

## Overview
This Java project, analyzes country data from the REST Countries API (`https://restcountries.com/v3.1/all`). The core functionality is implemented in the `Solution` class.

## Features
- **Data Parsing**: Uses Jackson's `ObjectMapper` for parsing JSON data from the API, including country names, area, population, and bordering countries.
- **Population Density Sorting**: Method `getCountryNamesSortedByDensity` calculates and sorts countries by population density in descending order.
- **Asian Country Analysis**: `getCountryInAsiaWithMostNeighboursFromDifferentRegion` finds the Asian country with the most neighbors from different regions.
- **Console Output**: Outputs analysis results to the command line.

## Usage
Designed for command-line execution, displaying requested country data.

## Installation

You can build and run the project using maven. The project uses Java 20.

## Usage

You can run the application using the following command:

``mvn clean install exec:java``

or by creating a jar using the following command:

``mvn package``

You can then run the jar using this command:

``java -jar target/restcountries-kees-1.0.0-SNAPSHOT.jar``

You can run the test cases using the following command:

``mvn test``
