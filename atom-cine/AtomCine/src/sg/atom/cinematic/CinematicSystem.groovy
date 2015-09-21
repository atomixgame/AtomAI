package sg.atom.cinematic

import sg.atom.cinematic.builder.CineBuilder
import sg.atom.cinematic.stage.cam.*
import sg.atom.cinematic.stage.*
import com.jme3.input.controls.ActionListener
/**
 * Prototype for interactive cinematic system.
 * 
 * FIXME: Should be switchable to timeline mode.
 * 
 * FIXME: Use AtomCinematic instead!
 * @author cuong.nguyenmanh2
 */
public class CinematicSystem{
    def app
    def builder
    //Facilities
    def subject
    def cam
    //Mode and settings
    def mode
    def defaultMode= "interactive"
    
    // Scripts
    CineScriptEngine cse;
    def scriptPath = []
    
    //Timeline
    def timeline
    def eventBus
    def speed
    int defaultFPS = 60
    int defaultTPF = 1000/defaultFPS
    //Managing. Concurent problem?
    def updateList = []
    def updateIterator
    def updateCam = []
    
    // Executors
    def playThread
    
    public CinematicSystem(app){
        this.app = app
        this.builder = new CineBuilder(app,this)
        this.cam = new CineCamera(app.camera)
        this.cse = new CineScriptEngine(this);
        
        this.app.inputManager.addListener(actionListener, "SIMPLEAPP_Exit");
    }
    def actionListener = new ActionListener() {
        public void onAction(String name, boolean value, float tpf) {
            if (!value)
            return;

            if (name.equals("SIMPLEAPP_Exit")){
                stop();
            }
        }
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
    def stop(){
        playThread.interrupt()
    }
    def pause(){
        
    }
    def play(){
        //FIXME: Use more sotistiphicated : closure or Runabale / Action (Task) system - Executor to schedule instead of Threads.
        println "=================="
        println "Start"
        // The cinematic system have to keep track on things... like Characters
        builder.characters.each{cr->
            updateList << cr
        }
        
        //FIXME: Run cinematic in different thread. Add better mechanism!
        playThread= Thread.start{
            builder.scenes.each{sc->
                sc.shots.each{shot->
                    println "Shot " + shot.name
                
                    for (def action:shot.actions){
                        
                        if (Thread.currentThread().isInterrupted()){
                            break;
                        }
                        if (action instanceof Closure){
                            // Interaction node
                            action.call()
                            //FIXME: Sleep between each action
                            
                        } else if (action instanceof CamSetting){
                            // Cam module node
                            action.change(this,cam)
                        }
                        sleep 400
                        updateChanges()
                    }

                    // Remove all CamSetting
                    // println "Out loop"
                    updateCam.clear()
                }
            }
        }

    }
    def updateChanges(){
        // update if need
        updateIterator = updateList.iterator()
        while (updateList.any{it.updateNeed}){
            if (Thread.currentThread().isInterrupted()){
                break;
            }
                        
            def tpf = app.timer.timePerFrame * app.speed
            for (def u:updateList){
                //println "in while loop update others"
                if (u.updateNeed){
                    u.update((float)tpf)
                }

            }
                        
            for (def cs:updateCam){
                //println "in while loop update Cam"
                if (cs.updateNeed){
                    cs.update((float)tpf,cam)
                }
            }
        }
        sleep defaultTPF * 5
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

