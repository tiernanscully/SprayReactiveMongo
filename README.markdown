## **Requirements:** ##

1. Download the Java JDK8, version 8u91: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

2. Download the Scala Build Tool (SBT), version 13.11: http://www.scala-sbt.org/download.html. Make sure to add SBT to your system path.

3. Add the following line to ~/.sbt/0.13/global.sbt (If the file doesn't already exist, just create it.)

        resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

4. Download the Scala SDK, version 2.11.8: http://www.scala-lang.org/download/2.11.8.html

## **Development IDE:** ##

1. Download the IntelliJ IDEA with the Scala plugin: https://www.jetbrains.com/idea/

## **Configure application:** ##

Change the url for the backend server to the ip address of your machine.
Edit the constant serverIpAddress in the file com/lemur/common.Constants.scala


## **Run the application:** ##
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