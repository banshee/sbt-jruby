import sbt._
import Keys._

object SbtJRuby extends Plugin {
  object SbtJRubySettings {
    lazy val settings = jrubySettings
  }

  def jirb = Command.args("jirb", "<launch jirb>") { (state, args) =>
    org.jruby.Main.main(List("-S", "jirb").toArray[String])
    state
  }

  def jrubySetupCommandLine = Command.args("jruby-setup", "run jruby setup script") { (state, args) =>
    org.jruby.Main.main(List("-S", "jirb").toArray[String])
    state
  }

  val jruby = TaskKey[Unit]("jruby", "run a jruby file")
  val jrubySetup = TaskKey[Unit]("jruby-setup", "initial JRuby setup, install gems, etc")

  val jrubyFile = SettingKey[File]("jruby-file", "path to file to run with JRuby")
  val jrubySetupFile = SettingKey[File]("jruby-setup-file", "path to file to run with JRuby for setup step")

  lazy val jrubySettings = Seq(
    Keys.commands += jirb,
    jruby <<= (jrubyFile, Keys.baseDirectory) map runJRubyFile,
    jrubySetup <<= (jrubySetupFile, Keys.baseDirectory) map runJRubyFile,
    jrubyFile := file("you must set jruby-file"),
    jrubySetupFile := file("you must set jruby-setup-file"))

  // Call jruby files, passing the base directory as the first argument
  def runJRubyFile(localRubyFile: File, baseDir: File) = {
    val rf = (baseDir / localRubyFile.toString).toString

    // this is a workaround for an sbt/jruby classloader issue.  Without it, you'll get this error
    // if you do a require 'java' in jruby:
    //   org.jruby.exceptions.RaiseException: (LoadError) no such file to load -- builtin/javasupport
    //
    // Warning:  this workaround is an ugly hack that probably has unknown side effects.
    val oldContextClassLoader = Thread.currentThread().getContextClassLoader
    Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader())
    com.restphone.ScriptWrapper.run(rf, baseDir.toString)
    Thread.currentThread().setContextClassLoader(oldContextClassLoader)
  }
}
