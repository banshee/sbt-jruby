import sbt._

object SbtJRuby extends Plugin {
  object SbtJRubySettings {
    lazy val settings = Seq(Keys.commands += jirb, tx, jrubyFile := file("fnord.rb"))
  }

  def jirb = Command.args("jirb", "<launch jirb>") { (state, args) =>
    org.jruby.Main.main(List("-S", "jirb").toArray[String])
    state
  }

  val jruby = TaskKey[Unit]("jruby", "run a jruby file")
  val jrubyFile = SettingKey[File]("jruby-file", "path to file to run with JRuby")

  val tx = jruby <<= (jrubyFile, Keys.baseDirectory) map { (f: File, b: File) =>
    val rb = (b / f.toString).toString
    //    println("jruby with " + rb)
    org.jruby.Main.main(List(rb).toArray[String])
  }
}
