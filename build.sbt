name := "LoveIsInTheAir"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-streaming" % "1.6.0",
  "org.apache.spark" %% "spark-streaming-twitter" % "1.6.0"
)
