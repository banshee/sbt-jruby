class ScriptWrapper
#  java_signature 'void run(String, String)'
  def self.run file, arg
    load file
  end
end
