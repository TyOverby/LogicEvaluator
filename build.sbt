name := "Logic Parser"

version := "0.0.1"

scalaVersion := "2.10.1"

libraryDependencies <+= scalaVersion { "org.scala-lang" % "scala-swing" % _ }
