# Pet Store - Spring Boot Application

## Overview

This repository contains a Spring Boot application built using Maven. The application is designed to run locally on your machine.

## Prerequisites

Before running the application, ensure you have the following installed:

- Java Development Kit (JDK): Version 11 or higher.
- Maven: Version 3.6.0 or higher.
- IntelliJ IDEA (optional): For development and running the application.

## Getting Started

Follow these steps to set up and run the application:

1. ### Clone the Repository
Clone this repository to your local machine using the following command:
```bash
git clone https://github.com/tomegavazovv/Pet-Store
```

2. ### Navigate to the Project Directory
Change directory to the project folder:
```bash
cd <project_name>
```

3. ### Build the Project
Use Maven to build the project:
```bash  
mvn clean install
```

4. ### Run the Application
You can run the application using Maven:
```bash
mvn spring-boot:run
```

Alternatively, you can run it directly from IntelliJ IDEA by running the main class (usually named Application or YourApplication).

5. ### Access the Application

Once the application is running, you can access it at _http://localhost:8080_ 

## Configuration

You can configure application properties by modifying the _src/main/resources/application.properties_ file. 
For example, you can change the server port or database connection settings here.

## Testing

To run unit tests, use Maven:
```bash
mvn test
```
