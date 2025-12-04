### Task 1

## Changes Made- Task 1:

- cleansed the entire folder strucutre
- remove path finder testsd from main of path finder into a seperate test class
- converted manual testing to proper JUnit4 tests
- added assertions
- test coverage added
- automated testing using Maven

### Task 2

## Changes Made- Task 2:

- identified 3 SOLID violations
- Fixed them using a PathFinder interface defining calcuatePath()
- Updated all path classes to implement interface logic
- created PathFinderFactroy (Factory Design Pattern) with path selection logic
- modified GridScenarioGenerator to use factory class instead of a hardcoded path
- updated vehicle constructorrs to accept the PathFinder interface
- updated GUILauncher to use this new Factory Design Patterm

## How to run:

- cd VSim
- mvn test # Run all tests
- mvn compile # Compile everything
- java -cp target/classes GUILauncher # Run GUI

## If Path not working- manually set:

- $env:Path -split ';' | Select-String "maven" | Select-Object -First 5
- $env:Path = [Environment]::GetEnvironmentVariable("Path", "Machine") + ";" + [Environment]::GetEnvironmentVariable("Path", "User")
- mvn test
- java -cp target/classes GUILauncher
