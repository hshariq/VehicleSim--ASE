### Task 1

## Changes Made- Task 1:

- cleansed the entire folder strucutre
- remove path finder testsd from main of path finder into a seperate test class
- converted manual testing to proper JUnit4 tests
- added assertions
- test coverage added
- automated testing using Maven

## How to run:

- mvn compile # Compile everything
- mvn test # Run all tests

## If Path not working- manually set:

- $env:Path -split ';' | Select-String "maven" | Select-Object -First 5
- $env:Path = [Environment]::GetEnvironmentVariable("Path", "Machine") + ";" + [Environment]::GetEnvironmentVariable("Path", "User")
- mvn test

## Known Limitations / Future Improvements:

- GUI lacks input validation (empty fields cause NumberFormatException)
- Could add dropdown for manual pathfinder selection
- Could implement more advanced pathfinding algorithms
