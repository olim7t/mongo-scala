name := "demo-console"

version := "1.0"

scalaVersion := "2.9.1"

resolvers += "repo.novus snaps" at "http://repo.novus.com/snapshots/"

excludeFilter in unmanagedSources := new sbt.FileFilter {
  def accept(f: File) = f.getName()(0).isLowerCase
}

libraryDependencies ++= Seq(
    "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
    "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT",
    "com.foursquare" %% "rogue" % "1.0.29" intransitive(),
    "net.liftweb" %% "lift-mongodb-record" % "2.4"
)
