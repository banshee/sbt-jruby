class ScriptWrapper
#  java_signature 'void run(String, String)'
  def self.run file, arg
    ARGV.clear
    ARGV << arg
    load file
  end
end
