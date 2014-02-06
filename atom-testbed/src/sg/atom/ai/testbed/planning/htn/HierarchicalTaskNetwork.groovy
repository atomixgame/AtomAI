package sg.atom.ai.testbed.planning.htn

/**
 *
 * @author cuong.nguyenmanh2
 */

class Task{
    def precond
    def effect
    def decomp
    def type
    def value
    def status
}
/*
class Operator{
    
}

class Action{
    def type
    
}

class Effect{
    
}

class Context{
    
}
*/
class Plan{
    def graph
    def startNode
    def endNode
    
    def travel(){
        
    }
}

class Planer{
    def planLib = []

    def decompose(){

    }
    def plan(){
        if (!isPrimitive(op)){
            def result = decompose(op);
            
            result.out()
            
        }
    }
    
    
    boolean isPrimitive(op){
        op.decomp = null
    }
    
    def build(){
        
    }
}

