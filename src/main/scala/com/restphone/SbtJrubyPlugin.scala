import sbt._

object SbtJRuby extends Plugin {
  object SbtJRubySettings {
    lazy val settings = Seq(Keys.commands += jirb, jrubyCmd, jrubyFile := file("you must set jruby-file"))
  }

  def jirb = Command.args("jirb", "<launch jirb>") { (state, args) =>
    org.jruby.Main.main(List("-S", "jirb").toArray[String])
    state
  }

  val jruby = TaskKey[Unit]("jruby", "run a jruby file")
  val jrubyFile = SettingKey[File]("jruby-file", "path to file to run with JRuby")

  val jrubyCmd = jruby <<= (jrubyFile, Keys.baseDirectory) map { (localRubyFile: File, baseDir: File) =>
    val rf = (baseDir / localRubyFile.toString).toString
    org.jruby.Main.main(List(rf).toArray[String])
  }
}
