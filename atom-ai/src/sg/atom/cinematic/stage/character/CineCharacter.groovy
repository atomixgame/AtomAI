package sg.atom.cinematic.stage.character

import com.jme3.scene.Spatial
import com.jme3.scene.Node
import com.jme3.animation.*
import com.jme3.scene.SceneGraphVisitor
import com.jme3.scene.SceneGraphVisitorAdapter
import com.jme3.scene.Geometry
import com.jme3.math.*
import com.jme3.bounding.*

/**
 *
 * @author cuong.nguyenmanh2
 */
class CineCharacter {
    enum Gender{male,female}
    
    String name
    String modelPath
    Gender gender
    Spatial spatial
    Node charNode
    def animControl
    def channelControl
    def cine
    boolean updateNeed = false
    //talk 
    String currentLine
    int currentLinePassed
    
    // steering 
    def speed = 0
    def maxSpeed = 1
    def target = null
    def energy = 1
    def vectology
    def safeDis = 1
    
    boolean stopMove = false;
    boolean stopRot = false;
    Vector3f oldPos;
    Quaternion oldRot;
    Vector3f targetPos;
    Quaternion targetRot;
    float timeRot = 0;
    float speedRot = 0.3f;
    float speedPos = 0.04f;
    // locomotion
    
    
    // brain and feeling
    def mood
    def state
    def task = null
    def brain = [:]
    
    CineCharacter(cine,name){
        this.name = name
        this.cine = cine
        
    }
    def debugMethods(){
        println("============================================")
        metaClass.methods.each{
            println it.name
        }
        println("============================================")
    }
    public void setModelPath(String modelPath,String aniRoot){

        this.modelPath = modelPath
        //println "Called"

        spatial = cine.builder.assetManager.loadModel(modelPath)
        cine.builder.rootNode.attachChild(spatial)
        spatial.scale(0.2f)
        charNode = spatial.getChild(aniRoot)
        //findCharNode()
        animControl = charNode.getControl(AnimControl.class)
        channelControl = animControl.createChannel()
        
    }
    def findCharNode(){
        println "Find char node"
        
        spatial.breadthFirstTraversal(new SceneGraphVisitorAdapter(){
                
                void visit(n){
                    println n
                    charNode = n
                    
                }
            }) 
         
        
    }
    // SIMPLE UPDATE
    def update(float tpf){
        println name +"update!"
        if (task == "walkTo"){
            lookAt(target)
            def d = getPos().distance(target.getPos());
            def safePos = target.getPos() + (getPos() - target.getPos()).normalize() * (safeDis + target.speed)
            targetPos.set(safePos)
            updatePos(tpf)
        }
    }
    
    void updatePos(float tpf){
        if (targetPos!=null){
            Vector3f newPos;
            Vector3f currentPos = spatial.getLocalTranslation();
            float dis = currentPos.distance(targetPos)
            if (!stopMove){
                if ( dis> speedPos){
                    Vector3f force = targetPos.subtract(currentPos).normalize().mult(speedPos)
                    newPos = currentPos.add(force)
                } else {
                    newPos = targetPos.clone();
                    reachTarget()
                }
                this.pos(newPos)
            }
            
        }
    }
    def reachTarget(){
        println name +" Reach!"
        stopMove = true;
        speed = 0
        updateNeed = false;
        task = ""
        //cine.markNoUpdate(this)
    }
    void updateRot(float tpf){
        if (targetRot!=null&&oldRot!=null){
            if (!stopRot){
                Quaternion newRot = new Quaternion();
                if ( timeRot <speedRot){
                    newRot.slerp(oldRot,targetRot,(float)(timeRot/speedRot))
                    timeRot += tpf;
                } else {
                    stopRot = true;
                    newRot.set(targetRot)
                }
                spatial.setLocalRotation(newRot);
            }
        }
    }
    // STEERING
    def pos(x,y,z){
        cine.app.enqueue{              
            spatial.setLocalTranslation(x,y,z)
        }
    }
    def pos(vec){
        cine.app.enqueue{              
            spatial.setLocalTranslation(vec)
        }
    }
    def walkTo(who,dis){
        task = "walkTo"
        target = who
        safeDis = dis
        this.targetPos = target.getPos().clone();;
        this.oldPos = getPos().clone();
        speed = maxSpeed
        stopMove = false;
        updateNeed = true;
        anim "walk"
        //cine.markUpdate(this)
    }
    

    /*
    def invokeMethod(String name, args) {
    if (animControl!=null && (name in animControl.animationNames)){
    animControl.setAnim(name);
    } else {
    println ">> Through invokeMethod "+name
    }
    }
     */
    def talk(text,who){
        println name+" say to "+who+": " +text
    }
    def say(text){
        println name+" say: " +text
        this.currentLine = text
        this.currentLinePassed = 0
        cine.cam.focus this
        while (this.currentLinePassed < this.currentLine.length()){
            this.currentLinePassed ++
            cine.displayText this
            sleep 100
        }
        
    }
    def wave(){
        println "__Wave"
    }
    def sad(){
        println "__Mood : Sad "
    }
    def hug(who){
        println "__Hug" + who.name
    }

    def anim(animName){
        if (channelControl!=null){
            channelControl.setAnim(animName)
        }
    }
    public String toString(){
        return this.name
    }
    /*
    def getProperty(String name) { 
        
    }
     */
    
    def getFrontVec(){
        def unitX = new Vector3f(0,0,1)
        return spatial.localRotation.mult(unitX);
    }
    
    def lookAt(what){
        cine.app.enqueue{
            if (what instanceof CineCharacter){
                //println "Try to rotate" + spatial.localRotation + spatial.localTranslation + " to " +what.getPos()

                spatial.lookAt(what.getPos(),getUp())
                //println "Result" + spatial.localRotation
            } else if (what instanceof Spatial){
                spatial.lookAt(what.localTranslation,getUp())
            } else{
                // do nothing
            }
        }
    }
    def getUp(){
        return new Vector3f(0,1,0);
    }
    def getPos(){
        spatial.localTranslation
    }
    def getHeight(){
        def bound = spatial.getWorldBound()
        if (bound instanceof BoundingBox){
            return bound.getYExtent() 
        } else if (bound instanceof BoundingBox){
            return bound.getRadius() * 2
        }
    }
}

