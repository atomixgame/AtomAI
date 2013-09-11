package sg.atom.cinematic

import sg.atom.cinematic.builder.CineBuilder
import sg.atom.cinematic.stage.cam.*
import sg.atom.cinematic.stage.*
/**
 *
 * @author cuong.nguyenmanh2
 */
class CinematicSystem{
    def app
    def builder
    def cam
    CineScriptEngine cse;
    def scriptPath = []
    
    // Concurency problem?
    def updateList = []
    def updateIterator
    def updateCam = []
    CinematicSystem(app){
        this.app = app
        this.builder = new CineBuilder(app,this)
        this.cam = new CineCamera(app.camera)
        this.cse = new CineScriptEngine(this);
    }

    def displayText(c){
        app.setOnScreenText(c.currentLine,c.currentLinePassed)
    }
    def debugInfos(){
                
        println "============================================"
        println "Scenes"
        builder.scenes.each{sc->
            println sc.name
        }
        println "Characters"
        builder.characters.each{cr->
            println cr.name
        }
    }
    def build(scriptName){
        cse.runScript(scriptName)
    }
    def play(){
      
        println "=================="
        println "Start"
        // The cinematic system have to keep track on things... like Characters
        builder.characters.each{cr->
            updateList << cr
        }
        Thread.start{
            builder.scenes.each{sc->
                sc.shots.each{shot->
                    println "Shot " + shot.name
                
                    shot.actions.each{action->
                        if (action instanceof Closure){
                            action.call()
                            sleep 400
                        } else if (action instanceof CamSetting){
                            action.change(this,cam)
                        }
                    }
                    // update if need
                    updateIterator = updateList.iterator()
                    while (updateList.any{it.updateNeed}){
                        def tpf = app.timer.timePerFrame * app.speed
                        for (def u:updateList){
                            println "in while loop"
                            if (u.updateNeed){
                                u.update((float)tpf)
                            }
                        }
                        
                        for (def cs:updateCam){
                            println "in while loop"
                            if (cs.updateNeed){
                                cs.update((float)tpf,cam)
                            }
                        }
                    }
                    // Remove all CamSetting
                    // println "Out loop"
                    updateCam.clear()
                }
            }
        }

    }
    
    def markUpdate(u){
        if (u instanceof CamSetting){
            updateCam << u
        } else {
            updateList << u
        }
    }
    
    def markNoUpdate(u){
        updateIterator.remove u
    }
}

