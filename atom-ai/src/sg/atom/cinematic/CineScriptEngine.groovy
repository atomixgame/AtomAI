package sg.atom.cinematic

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import sg.testbed.game2d.graphics.*

class CineScriptEngine{
    String[] roots = [ "src/sg/atom/cinematic/script" ];
    GroovyScriptEngine gse = new GroovyScriptEngine(roots);
    Binding binding = new Binding();
    
    CineScriptEngine(cine){
        binding.setVariable("cine",cine)
    }
    def runScript(path){
        return gse.run(path, binding);
    }
}




