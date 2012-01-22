name := "handson-lift-record"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
    "com.foursquare" %% "rogue" % "1.0.29" intransitive(),
    "net.liftweb" %% "lift-mongodb-record" % "2.4",
    "joda-time" % "joda-time" % "2.0",
    "org.joda" % "joda-convert" % "1.1",
    "org.scalatest" %% "scalatest" % "1.6.1" % "test"
)
