# Xpress Payment Platform

Xpress Payment Platform is a Java Spring Boot project that provides REST APIs for Authentication/Authorization using JWT and implements Airtime VTU Bill Payment functionalities.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
    - [API Endpoints](#api-endpoints)
    - [Unit Testing](#unit-testing)
    - [Docker](#docker)
    - [Code Coverage](#code-coverage)
    - [Continuous Integration](#continuous-integration)
- [Contributing](#contributing)
- [License](#license)

## Overview

The project aims to provide a robust platform for users to sign up, log in, and make authenticated calls to purchase airtime using REST APIs. It leverages Java Spring Boot, JWT for secure authentication, and Docker for easy deployment.

## Features

- **User Authentication:**
    - Signup, Login, Logout, getLoggedinuser endpoints for user authentication.
    - JWT implementation for secure authorization.

- **Airtime VTU Bill Payment:**
    - API for making authenticated calls to purchase airtime on the platform.

- **Unit Testing:**
    - Comprehensive unit testing for every major component, including controllers.

- **Dockerized Services:**
    - Docker-compose file for dependent backend services (MySQL, Redis, SonarQube).

- **Code Quality Analysis:**
    - Integration with SonarQube for code quality analysis.

- **Continuous Integration:**
    - GitHub Actions set up for Continuous Integration (CI) to build, test, and deploy the application.

## Prerequisites

Ensure you have the following installed on your system:

- Java 17
- Maven 3

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/Patrick5455/xpress-payment-biller-tha
    ```

2. Navigate to the project directory:

    ```bash
    cd https://github.com/Patrick5455/xpress-payment-biller-tha
    ```

3. Build the project:

    ```bash
    mvn clean install
    ```

4. Start the docker containers:

    ```bash
    docker-compose up -d
    ```
5. Run the application:

    ```bash
    mvn spring-boot:run
    ```
   
6. The application should now be running on http://localhost:8081.

7. You can now test the endpoints using Postman or any other API testing tool.

8. To setup SonarQube locally, follow this [guide](https://docs.sonarsource.com/sonarqube/9.9/try-out-sonarqube/)


## Usage

### API Endpoints

Detailed API documentation, including sample requests and responses, can be found in the [API Docs](https://documenter.getpostman.com/view/10629518/2s9YsJCtAw).

### Unit Testing

Execute unit tests:

```bash
mvn test
```
#### Test Coverage with JaCoCo

```bash
./jacoco-test-coverage.sh
```

#### Code analysis with SonarQube

```bash
./sonarqube-analysis.sh
```



