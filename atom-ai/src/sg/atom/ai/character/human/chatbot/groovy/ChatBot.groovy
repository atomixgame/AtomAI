package sg.atom.ai.character.human.chatbot.groovy

import groovy.util.*
import groovy.lang.*

public class ChatBot{
    Binding binding = new Binding();
    GroovyShell shell = new GroovyShell(binding);
    //String path="F://JGE//OTHER MODULE//Scripting/Groovy/Builder//";
    Script aScript;
    Node currentNode;
    Node rootNode;
    
    public ChatBot(aScript){
        binding.setVariable("i", new Integer(2));
        /*
        aScript=shell.parse(new File(path+"builderTest2.groovy"));
        aScript.run();
         */
        rootNode = aScript.root;
        currentNode = rootNode;
    }
    void talk(what){
        println "Say:"+what;
    }
    
    void smile(){
        println ":)";
    }

    void chat(String text){
        // 
    }

    void doAct(what){
        
        // Go to the Node 
        
        // Eval the conditional
        def isTrue=eval(what)
        println('Condition :'+isTrue);
        if (isTrue){
           
        }
        // Do the task
        // What if emotion and Memory involed
         
    }
    
    Object eval(what){
        return shell.evaluate(what);
    }
    void pEval(what){
        println eval(what);
    }
    void goTo(where){
        currentNode = rootNode.breadthFirst().find{(it.name==where)||(it.id==where)};
    
    }
    
    String[] getChoices(){
        String[] s=new String[currentNode.children().size()];
        int i=0;
        currentNode.children().each{ c->
            s[i]= c.attribute('text');
            i++;
            // println c.attribute('text');
        }
        //println "Number of items:"+ s.length;
        return s;
    }
    
    boolean nextChat(index){
        if (!isCurrentLeaf()){
            currentNode = currentNode.children()[index];
            return true;
        } else{
            return false;
        }
    }
    
    String getCurrentText(){
        if (currentNode != rootNode){
            return currentNode.attribute('text');
        } else {
            return "Hello , my name is ChatBot!";
        }
    }
    boolean isCurrentLeaf(){
        return currentNode.children().size()==0;
    }
    
    Node getRootNode(){
        return rootNode;
    }
}

/*
bot1 = new Bot()

//bot1.talk("a")
//bot1.pEval('i+1+8*16')
//bot1.doAct('i==2')
//bot1.getSentences().each(){println it};
bot1.getChoices().each(){println it};
bot1.nextChat(1);
bot1.getChoices().each(){println it};
bot1.nextChat(0);
bot1.getChoices().each(){println it};
println(bot1.isCurrentLeaf());
 */