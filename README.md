# Congestion Tax Calculator

Welcome the Volvo Cars Congestion Tax Calculator assignment.

This repository contains a developer [assignment](ASSIGNMENT.md) used as a basis for candidate intervew and evaluation.

Clone this repository to get started. Due to a number of reasons, not least privacy, you will be asked to zip your solution and mail it in, instead of submitting a pull-request.

## Installation

### Build the Project

```bash
mvn clean install
```
### Run the Application

You can run the project using command line like below and also can checkout to any IDE and run the application.

```bash
mvn spring-boot:run
```

## Sample Request

```
curl --location --request POST 'http://localhost:8080/tax/calculate' \
--header 'Content-Type: application/json' \
--data-raw '{
   "city":"GB",
   "vehicle":"car",
   "time":["2013-06-10 06:31:59", "2013-06-10 07:10:59"]    
}'
```

## Sample Output

```
{
    "tax": 18.0,
    "currency": "SEK"
}
```