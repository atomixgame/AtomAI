package sg.atom.testbed.chatbot

import groovy.swing.SwingBuilder
import javax.swing.*
import java.awt.*

import javax.swing.tree.DefaultMutableTreeNode as TreeNode
import groovy.swing.SwingBuilder
import java.awt.BorderLayout as BL
import groovy.beans.Bindable
import sg.atom.ai.character.human.chatbot.ChatBot
import sg.atom.ai.character.human.chatbot.Dialoge1

//String path="F://JGE//OTHER MODULE//Scripting/Groovy/Builder//"
//String path=""
//Class botClass = new GroovyShell().parse(new File(path+"builderTest.groovy"))
//Class botClass = this.class.classLoader.parseClass(new File(path + "ChatBot.groovy"))
//bot1 = botClass.newInstance();

bot1 = new ChatBot(new Dialoge1());
def node = bot1.getRootNode()

// Make a window for the UI
Font bigfont = new Font("Serif", Font.BOLD, 26) 
swing = new SwingBuilder()
mboxTree = null
chatTexts  = null
chatModel = new ChatModel();

class ChatModel {
    @Bindable String currentSentence;
    @Bindable ObservableList options=new ObservableList();
}

// An option panel
def optionPanel = {index->
    swing.panel() {
        borderLayout()

        textlabel = label(text: bind(source:chatModel, 
                sourceProperty:'options',
                sourceEvent: 'propertyChange',
                converter: { it.getAt(index) }), constraints: BL.WEST)
    
        button(text:'Click Me',
            actionPerformed: {
                nextChat(index);
                if (bot1.isCurrentLeaf()){
                    enable = false;
                }
            },
            constraints:BL.EAST)
    }
}


count=0;
chatModel.options = new String[3];

void getOptions(){
    String[] str=bot1.getChoices();
    chatModel.options.clear(); 
    println "Num of choices: "+str.size()
    for ( int i=0;i<str.size();i++) {
        chatModel.options[i] = str[i];// + i + count;
    }
    
    chatModel.options.eachWithIndex{op,oi->
        println oi + " : " + op
    }
    println "Num of options: "+chatModel.options.size()
}

void nextChat(index){
    println " Turn $count";
    if (index!=null){
        println "NextChat! "+index;
        //chatModel.currentSentence = "currentSentence" + count + ".Choice is:"+index ;
        //chatModel.options = new String[3];
        if (!bot1.nextChat(index)) return;
    }
    
    chatModel.currentSentence = bot1.getCurrentText();
    chatTexts.text += "BOT: "+bot1.getCurrentText()+"\n";

    getOptions()
    count++;
}



def sharedPanel = {aText->
    swing.panel() { 
        label(aText)
    }
}

getOptions();
swing.frame(title: 'Dialoge Editor', defaultCloseOperation: JFrame.DISPOSE_ON_CLOSE,
    size: [800, 600], show: true, locationRelativeTo: null, pack:true) {

    menuBar() {
        menu(text: "File", mnemonic: 'F') {
            menuItem(text: "Exit", mnemonic: 'X', actionPerformed: {dispose() })
        }
    }

    splitPane {
        scrollPane(constraints: "left", preferredSize: [160, -1]) {
            mboxTree = tree(rootVisible: false)
        }
        splitPane(orientation:JSplitPane.VERTICAL_SPLIT, dividerLocation:320) {
            splitPane(orientation:JSplitPane.VERTICAL_SPLIT, dividerLocation:160) {
                scrollPane(constraints: "top") { 
                    mailTable = table() 
                }
                scrollPane(constraints: "bottom") { 
                    chatTexts = textArea()
                }
            }
            splitPane(orientation:JSplitPane.VERTICAL_SPLIT, dividerLocation:80) {
                scrollPane(constraints: "top") { 
                    label(text: bind(source:chatModel, sourceProperty:'currentSentence') , font:bigfont )
                }
                scrollPane(constraints: "bottom") { 
                    panel(constraints:BL.CENTER){
                    
                        // 
                        gridLayout(rows: 3, cols: 1)
                        widget(optionPanel(0));
                        widget(optionPanel(1));
                        widget(optionPanel(2));
                    }
                }
            }
        }
    }
    ["From", "Date", "Subject"].each { mailTable.model.addColumn(it) }
         
}

// A tree model for display Tree Node
// Convert tree model


mboxTree.model.root.removeAllChildren()

void addToTreeNode(node,treeNode){
    node.children().each{
        c-> 
        newTreeNode = new TreeNode(c.attribute('name'))
        treeNode.add(newTreeNode)
        if (c.children().size()>0){
            addToTreeNode(c,newTreeNode)
        }
    }
}

chatTexts.text += "BOT: "+bot1.getCurrentText()+"\n";


def treeNode = new TreeNode(node.attribute('kind'))
addToTreeNode(node,treeNode)
mboxTree.model.root.add(treeNode)
mboxTree.model.reload(mboxTree.model.root)