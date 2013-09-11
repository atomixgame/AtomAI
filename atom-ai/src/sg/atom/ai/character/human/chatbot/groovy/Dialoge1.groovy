package sg.atom.ai.character.human.groovy.chatbot

class Dialoge1{
    def builder = NodeBuilder.newInstance()

    def root=builder.tree(kind:'dialogue') {
        c(id:1,  name:'Start game', text:'Start game', condition:'i==1') {
            c(id:3,  name:'Say Hi',text:'Say Hi', condition:'i==3')
            c(id:4,  name:'Say Hello',text:'Say Hello', condition:'i==4')
        }
        c(id:2,  name:'Goodbye',text:'Goodbye', condition:'i==3') {
            c(id:5,  name:'Say Goodbye',text:'Say Goodbye', condition:'i==5')
            c(id:6,  name:'Say Goodluck',text:'Say Goodluck', condition:'i==6')
        }
    }
}
/*
listDown (root,0)  

println "Depth First ====================================="
root.depthFirst().each{ println it.attribute('name')}
println "Breadth First ====================================="
root.breadthFirst().each{ println it.attribute('name')}
 */
    