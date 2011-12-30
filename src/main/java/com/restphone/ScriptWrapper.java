package com.restphone;

import org.jruby.Ruby;
import org.jruby.RubyObject;
import org.jruby.javasupport.util.RuntimeHelpers;
import org.jruby.runtime.builtin.IRubyObject;
import org.jruby.javasupport.JavaUtil;
import org.jruby.RubyClass;


public class ScriptWrapper extends RubyObject  {
    private static final Ruby __ruby__ = Ruby.getGlobalRuntime();
    private static final RubyClass __metaclass__;

    static {
        String source = new StringBuilder("class ScriptWrapper\n" +
            "#  java_signature 'void run(String, String)'\n" +
            "  def self.run file, arg\n" +
            "    load file\n" +
            "  end\n" +
            "end\n" +
            "").toString();
        __ruby__.executeScript(source, "src/main/jruby/script_wrapper.rb");
        RubyClass metaclass = __ruby__.getClass("ScriptWrapper");
        metaclass.setRubyStaticAllocator(ScriptWrapper.class);
        if (metaclass == null) throw new NoClassDefFoundError("Could not load Ruby class: ScriptWrapper");
        __metaclass__ = metaclass;
    }

    /**
     * Standard Ruby object constructor, for construction-from-Ruby purposes.
     * Generally not for user consumption.
     *
     * @param ruby The JRuby instance this object will belong to
     * @param metaclass The RubyClass representing the Ruby class of this object
     */
    private ScriptWrapper(Ruby ruby, RubyClass metaclass) {
        super(ruby, metaclass);
    }

    /**
     * A static method used by JRuby for allocating instances of this object
     * from Ruby. Generally not for user comsumption.
     *
     * @param ruby The JRuby instance this object will belong to
     * @param metaclass The RubyClass representing the Ruby class of this object
     */
    public static IRubyObject __allocate__(Ruby ruby, RubyClass metaClass) {
        return new ScriptWrapper(ruby, metaClass);
    }
        
    /**
     * Default constructor. Invokes this(Ruby, RubyClass) with the classloader-static
     * Ruby and RubyClass instances assocated with this class, and then invokes the
     * no-argument 'initialize' method in Ruby.
     *
     * @param ruby The JRuby instance this object will belong to
     * @param metaclass The RubyClass representing the Ruby class of this object
     */
    public ScriptWrapper() {
        this(__ruby__, __metaclass__);
        RuntimeHelpers.invoke(__ruby__.getCurrentContext(), this, "initialize");
    }

    
    public static Object run(Object file, Object arg) {
        IRubyObject ruby_file = JavaUtil.convertJavaToRuby(__ruby__, file);
        IRubyObject ruby_arg = JavaUtil.convertJavaToRuby(__ruby__, arg);
        IRubyObject ruby_result = RuntimeHelpers.invoke(__ruby__.getCurrentContext(), __metaclass__, "run", ruby_file, ruby_arg);
        return (Object)ruby_result.toJava(Object.class);

    }

}
