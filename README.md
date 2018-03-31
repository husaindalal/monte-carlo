# monte-carlo
Monte carlo simulation

```
./gradle bootRun
```


#Start URL
http://localhost:8080/swagger-ui.html#/monte-carlo-controller/simulateUsingPOST
```
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

#Run tests
```
./gradle test
```