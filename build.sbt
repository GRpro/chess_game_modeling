
scalaVersion     := "2.12.7"
version          := "0.1.0-SNAPSHOT"
organization     := "com.example"
organizationName := "example"

name := "ProgTest"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.3.0-SNAP2" % Test


unmanagedBase := baseDirectory.value / "libs"

// com.sun.tools.javac.resources.version? (multiple choices...) Alt+Enter
// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
