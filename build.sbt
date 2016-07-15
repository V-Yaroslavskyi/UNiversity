name := """University"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
    cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick"      % "2.1.0",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.postgresql"     %  "postgresql" % "9.3-1102-jdbc41"
)

