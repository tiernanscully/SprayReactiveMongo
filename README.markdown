## **Requirements:** ##

1. Download the Java JDK8, version 8u91: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

2. Download the Scala Build Tool (SBT), version 13.11: http://www.scala-sbt.org/download.html. Make sure to add SBT to your system path.

3. Download the Scala SDK, version 2.11.8: http://www.scala-lang.org/download/2.11.8.html

## **Development IDE:** ##

1. Download the IntelliJ IDEA with the Scala plugin: https://www.jetbrains.com/idea/

## **Setting up Mongodb:** ##

The version of mongodb we're using is 3.2, setup guide for relevant OS is here https://docs.mongodb.com/manual/installation/


## **Configure application:** ##

Create file ~/.sbt/0.13/global.sbt and add the following line to the file:

```
#!scala

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
```

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