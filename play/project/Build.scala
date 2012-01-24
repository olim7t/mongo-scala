import sbt._
import Keys._

object ApplicationBuild extends Build {
    val appName         = "MongoDB with Play 2.0-RC1-SNAPSHOT"
    val appVersion      = "1.0"

    val appDependencies = Nil

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = "SCALA")
}
