name := """app-2"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
   "uk.co.panaxiom" %% "play-jongo" % "0.9.0-jongo1.2",
  cache,
  javaWs
)

//the contents of conf directory are added to the classpath by default
PlayKeys.externalizeResources := false

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

