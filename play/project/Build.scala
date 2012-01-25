import sbt._
import Keys._

object ApplicationBuild extends Build {
    val appName         = "MongoDB with Play 2.0-RC1-SNAPSHOT"
    val appVersion      = "1.0"

    val appDependencies = Seq(
      "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
      "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT",
      "com.foursquare" %% "rogue" % "1.0.29" intransitive(),
      "net.liftweb" %% "lift-mongodb-record" % "2.4"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = "SCALA")
}
