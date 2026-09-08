## About the assignment

You will find the tasks of this assignment on [CODE_ASSIGNMENT](assignment/CODE_ASSIGNMENT.md) file

## About the code base

Some of this code here is based on https://github.com/quarkusio/quarkus-quickstarts

## Note on Bonus Task
The bonus task is implemented in the `com.fulfilment.application.monolith.fulfillment` package. 
It exposes a REST API at `/fulfillment` which accepts `POST` requests to associate a Store, Product, and Warehouse.

## Note on Testing
A GitHub Actions CI/CD pipeline is configured in `.github/workflows/ci.yml`. It runs tests and uses JaCoCo to ensure code coverage is maintained at or above 80%.
