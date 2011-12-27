sbtPlugin := true

name := "jruby"

version := "0.1"

organization := "com.restphone"

libraryDependencies ++= Seq(
  "org.jruby" % "jruby-complete" % "1.6.5",
  "org.mirah" % "mirah-complete" % "0.0.7"
)

unmanagedBase <<= baseDirectory { base => base / "lib" }
