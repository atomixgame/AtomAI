package sg.atom.ai.testbed.decision.goalbase

/**
 *
 * @author cuong.nguyenmanh2
 * A small sim game
 */
class Character{
    enum Gender{Male,Female}
    def name
    
    // attributes
    def gender = 0
    def food = 0
    def love = 0
    def sex = 0
    def health = 0
    def muscle = 0
    def look = 0
    def money = 0
    def fun = 0

    def desires = []
    
    // visual & simulation
    def pos
    def size
    def anim
    
    def update(){
        
    }
    def think(){
        if (food ==0){
            "I'm starving"
        }
    }
    
    def feel(){
        
    }
    
    def find(){
        
    }
    
    def act(){
        
    }
    
}
class Trend{
    
}
class Smell{
    
}
class World{
    def currentTrends = []
    def characters = []
    def smells = []
}
class Goal{
    def name
    def value
    def enable = true
    def hints = [:]
    def insistence
}

class Action{
    def name
    def fullfil
    def change
    def script
}

class Event{
    def name
    def description
    def change = [:]
    Event(n,d,c){
        this.name = m
        this.description = d
        this.change = c
    }
}
