resolvers ++= Seq(
    DefaultMavenRepository,
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Play local Repository" at "file:///Users/olivier/projects/Play20/repository/local"
)

addSbtPlugin("play" % "sbt-plugin" % "2.0-RC1-SNAPSHOT")
