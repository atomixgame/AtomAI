/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sg.atom.core.proxy

class RPClass{
    //String data = "foo"
    def data = [:]
    
    def set(name,value){
        println "invoke data set $name -> $value"
        data[name] = value
    }
    
    def get(name){
        println "invoke data get $name"
        data[name]
    }
}

class GClass{
    static void wrap(obj){
        def delegate = new RPClass()
        obj.metaClass.delegate = delegate
        obj.metaClass.methods.each{
            println " - " + it.name
        }
        obj.metaClass.properties.each{
            def name = it.name
            println " () " + name
            if (name!="delegate" && name!="class" && name!=null){
                println " (?) " + name
                def value = obj."$name";
                println " result :" + value
                delegate.set(name,value)
            }
        }
        obj.metaClass.getProperty= {name->delegate.get(name)}
        obj.metaClass.setProperty= {name,value-> delegate.set(name,value)}
    }

}

class Person{
    def hello = "helloText"
}

def me = new Person()
GClass.wrap(me)
println "----------------"
me.haha = "seen"
println me.haha