Configure application:

Change the url for the backend server to the ip address of your machine.
Edit the constant serverIpAddress in the file com/lemur/common.Constants.scala


Run the application:

1. Change directory into your clone:

        $ cd lemur-backend

2. Launch SBT:

        $ sbt

3. Compile everything and run all tests:

        > test

4. Start the application:

        > re-start

5. Browse to [http://localhost:8080](http://localhost:8080/) to view the backend API

6. Stop the application:

        > re-stop
