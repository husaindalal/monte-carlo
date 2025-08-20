# Monte Carlo Simulation API

This is a Spring Boot application that provides a RESTful API for running Monte Carlo simulations on investment portfolios. The application is a Java-based implementation of a stock portfolio simulation that was originally demonstrated in an Excel spreadsheet in [this YouTube video](https://www.youtube.com/watch?v=Q5Fw2IRMjPQ).

## Project Overview

The purpose of this project is to simulate the future value of an investment based on a set of input parameters. The user can define one or more investment portfolios, each with its own expected average return and risk (standard deviation). The simulation then projects the potential outcomes for each portfolio over a specified number of years, taking into account a given inflation rate.

The simulation uses a normal distribution to model the volatility of returns and provides the 10th, 50th (median), and 90th percentile of the outcomes. This allows the user to understand the range of potential outcomes and the likelihood of achieving certain investment goals.

## Technologies Used

*   **Java 8**
*   **Spring Boot 2.0**
*   **Spring MVC Web**
*   **Gradle** for dependency management
*   **Apache Commons Math3** for statistical functions
*   **Spock** for testing
*   **Swagger** for API documentation
*   **Lombok** for reducing boilerplate code

## Getting Started

To get the application running locally, follow these steps:

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/husaind/monte-carlo.git
    ```
2.  **Build the application:**
    ```bash
    ./gradlew build
    ```
3.  **Run the application:**
    ```bash
    ./gradlew bootRun
    ```
The application will start on `http://localhost:8080`.

## API Usage

The application exposes a single API endpoint for running the simulation.

### Endpoint

`POST /monte-carlo`

This endpoint accepts a JSON request body with the simulation parameters and returns the simulation results.

### Request Body

The request body should be a JSON object with the following fields:

| Field                 | Type      | Description                                                                                             |
| --------------------- | --------- | ------------------------------------------------------------------------------------------------------- |
| `riskReturns`         | `Array`   | An array of `RiskReturn` objects, each representing an investment portfolio.                            |
| `investedAmountInitial` | `Number`  | The initial amount of money invested.                                                                   |
| `investedYears`       | `Integer` | The number of years the money is invested for.                                                          |
| `inflationRate`       | `Number`  | The annual inflation rate (as a percentage, e.g., 3.5 for 3.5%).                                        |
| `simulationCount`     | `Integer` | The number of simulations to run for each portfolio. A higher number will produce more accurate results. |

A `RiskReturn` object has the following structure:

| Field           | Type     | Description                                                               |
| --------------- | -------- | ------------------------------------------------------------------------- |
| `averageReturn` | `Number` | The expected average annual return (mean) as a percentage.                |
| `risk`          | `Number` | The risk (standard deviation) of the annual return as a percentage.       |

### Example Request

```json
{
  "inflationRate": 3.5,
  "investedAmountInitial": 100000,
  "investedYears": 20,
  "riskReturns": [
    {
      "averageReturn": 9.4324,
      "risk": 15.675
    },
    {
      "averageReturn": 6.189,
      "risk": 6.3438
    }
  ],
  "simulationCount": 10000
}
```

### Response Body

The response will be a JSON object containing a list of `Percentiles` objects, one for each portfolio in the request.

| Field          | Type     | Description                                               |
| -------------- | -------- | --------------------------------------------------------- |
| `percentile10` | `Number` | The 10th percentile of the simulated outcomes.            |
| `percentile50` | `Number` | The 50th percentile (median) of the simulated outcomes.   |
| `percentile90` | `Number` | The 90th percentile of the simulated outcomes.            |

### Example Response

```json
{
  "percentiles": [
    {
      "percentile90": 632385.93,
      "percentile50": 268275.98,
      "percentile10": 113488.56
    },
    {
      "percentile90": 343275.14,
      "percentile50": 236894.25,
      "percentile10": 163484.58
    }
  ]
}
```

### Swagger Documentation

You can access the Swagger UI for interactive API documentation at:
`http://localhost:8080/swagger-ui.html`

## Running Tests

To run the test suite, use the following command:

```bash
./gradlew test
```
