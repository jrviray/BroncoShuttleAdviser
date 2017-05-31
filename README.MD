# Bronco Shuttle Adviser

## Easy information at your fingertips

The Bronco Shuttle Adviser takes the information originally available on the Bronco Shutle website and conveys it as easily accesible info wihtout the hassle of clicking around and missing out on routes near you.

## Features
* Free
* Open-Source
* Integratable

## Application Specifications

The Bronco Shuttle Adviser is essentially an information aggregator that displays easy to read information about arrival times. The application is composed of three main components:
* Main Activity
* Asynchronous Task Executor
* JSON Parser

### Main Activity

The Main Activity is the container of the application and displays meaningful output to the user. It is also responsible for taking in any user input in order to determine the information flow of the application

### Asynchronous Task Executor

The Asynchronous Task Executor contains the JSON Parser and sets any on-going process as a background thread to avoid exceptions that would otherwise caused if the JSON Parser was in the main UI thread.

### JSON Parser

Taking user input from the Main Activity, the JSON Parser refrences a JSON file holding relevant url's for arrival times, executes HTTPS calls throug the Asynchronous Task Executor, and collects JSON trees based on the call result. Through a series of JSON handlers used to parse the JSON trees, a message is created, collected into an aray list, and adapted to a list view in the Main Activity.

## Issues and Improvemntments

### Issues Encountered:
* creating a properly structured JSON tree for url refrences
* creating proper JSON handlers
* considering the best location for using an array adapter 
* accounting for empty JSON trees when no arrival times are available
* tesiting and debugging was dependent on the availibity of arrival times

### Improvents to Consider:
* use of Retrofit service to replace JSON url references
* real time shuttle GPS intergration

## Intergration

To intergrate the features of the application, create a handler for the Asynchronous Task Executor (which contains the JSON Parser) and collect the information as an array list. 

Files to include:
* GetBusInfo.java Class
* JSONParser.java Class
* locations.json Asset 

## Copyright

Copyright Joshua R. Viray 2017 
