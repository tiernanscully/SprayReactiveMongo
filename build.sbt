organization  := "com.lemur"

version       := "0.1"

scalaVersion  := "2.11.8"
autoScalaLibrary := false
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")
ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) }
libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  val scalaTestV = "2.2.6"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayV,
    "io.spray"            %%  "spray-json"    % "1.3.2",
    "io.spray"            %%  "spray-routing" % sprayV,
    "io.spray"            %%  "spray-testkit" % sprayV  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"   % "2.3.11" % "test",
    "org.scalamock"       %% "scalamock-scalatest-support" % "3.2.2" % "test",
    "org.mockito" % "mockito-core" % "1.9.5" % "test",
    "org.scalactic" %% "scalactic" % scalaTestV,
    "org.scalatest" %% "scalatest" % scalaTestV % "test",
    "com.github.simplyscala" %% "scalatest-embedmongo" % "0.2.2" % "test",
    "com.google.api-client" % "google-api-client" % "1.20.0",
    "com.gettyimages" %% "spray-swagger" % "0.5.1",
    "org.reactivemongo" %% "reactivemongo" % "0.11.14"
  )
}
Revolver.settings
