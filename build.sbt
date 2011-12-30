sbtPlugin := true

name := "jruby"

version := "0.2"

organization := "com.restphone"

libraryDependencies ++= Seq(
  "org.jruby" % "jruby-complete" % "1.6.5.1"
)

// unmanagedBase <<= baseDirectory { base => base / "lib" }

// unmanagedJars in Compile += file("lib/yetch.jar")

unmanagedResourceDirectories in Compile <+= baseDirectory(_ / "lib")
