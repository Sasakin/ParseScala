name := "scala_hw"

version := "0.1"

scalaVersion := "2.11.6"

val common = Seq(version:= "0.1", scalaVersion:= "2.11.6")

compile / mainClass := Some("Application")
assembly / mainClass := Some("Application")

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}


lazy val root = (project in file("."))
  .settings(common)
  .settings(
    publishArtifact := true
  )


assembly / assemblyJarName := "scala_hw.jar"

