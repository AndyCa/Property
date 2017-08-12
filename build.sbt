name := "property"

version := "1.0"

scalaVersion := "2.12.3"

libraryDependencies += "org.scalaj" % "scalaj-http_2.12" % "2.3.0"

val circeVersion = "0.8.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)