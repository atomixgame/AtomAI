package sg.atom.cinematic.builder

import com.jme3.asset.AssetManager
import com.jme3.scene.Node
import sg.atom.cinematic.stage.character.CineCharacter
import sg.atom.cinematic.stage.sound.CineSoundManager
import sg.atom.cinematic.stage.sound.SoundClip
import sg.atom.cinematic.CinematicSystem
import com.jme3.math.Vector3f
import sg.atom.cinematic.stage.*
import sg.atom.cinematic.stage.cam.*
import sg.atom.cinematic.stage.cam.move.*
import sg.atom.cinematic.stage.cam.constraint.*
import com.jme3.math.Transform


/**
 *
 * @author cuong.nguyenmanh2
 */
class CineBuilder extends BuilderSupport{
    AssetManager assetManager;
    Node rootNode
    CineSoundManager soundMng
    CinematicSystem cine
    
    def loop
    def outdoor,indoor
    def smooth
    def rootModelPath = "Scenes/Sim/Characters/"
    
    def characters=[]
    def scenes = []
    def sounds=[]
    
    class Scene{
        String name
        def shots =[]
        def sounds =[]
    }
    class Shot{
        String name
        def loc = [:]
        def camMoves =[]
        def actions=[]
    }

    public CineBuilder() {
        //this.assetManager = app.assetManager;
    }
    
    public CineBuilder(app,cine) {
        this.assetManager = app.assetManager;
        this.rootNode = app.rootNode;
        this.soundMng = new CineSoundManager(app);
        this.cine = cine
    }

    protected void setParent(Object parent, Object child){

        if (parent instanceof Scene){
            if (child instanceof Shot){
                parent.shots<<child
            } else if (child instanceof String){
                parent.name = child
            } else if (child instanceof SoundClip){
                soundMng.play(child)
            }
        } else if (parent instanceof Shot){
            if (child instanceof String){
                parent.name = child
            } else if (child instanceof Closure){
                //println "Add a closure"
                parent.actions<<child
            }else if (child instanceof CamSetting){
                parent.actions<<child
            }
        } else if (parent instanceof CamSetting){
            if (child instanceof CamConstraint){
                parent.constraints << child
            } else if (child instanceof CamMove){
                parent.move = child
            }  else if (child instanceof Transform){
                parent.transform = child
            } else if (child instanceof Vector3f){
                parent.pos = child
            }

        } else if (parent == this.characters){
            if (child instanceof CineCharacter){
                characters<<child
            }
        } else if (parent == this.scenes){
            if (child instanceof Scene){
                scenes<<child
            }
        } else if (parent == this.soundMng){

        }
    }
    protected Object createNode(Object name){
        println "CreateNode(1)"
        println name
        switch (name){
        case "cam": 
            return new CamSetting()
        case "shot": 
            return new Shot()
        case "scene": 
            return new Scene()
        case "characters": 
            return this.characters   
        case "scenes": 
            return this.scenes   
        case "sounds": 
            return this.soundMng   
        }
        
    }
    protected Object createNode(Object name, Object value){
        println "CreateNode(2)"
        println name
        println value
        switch (name){
        case "male":
        case"female": 
            return new CineCharacter(cine,value)
        case "name": 
            return value
        }
    }
    protected Object createNode(Object name, Map attributes){
        println "CreateNode(2-2)"
        println name
        println attributes
        switch (name){
        case "male":
        case"female": 
            def c = new CineCharacter(cine,attributes.name)
            c.setModelPath(rootModelPath + attributes.who,attributes.aniRoot)
            c.gender = name
            return c
        case"scene": 
            return new Scene(name:attributes.name)
        case"act": 
            return attributes.do
        case"music": 
            return soundMng.loadMusic(attributes.path)
        case"soundfx":
            return soundMng.loadMusic(attributes.path)
        case"dialog": 
            return new DialogueConstraint(characters:attributes.with,focus:attributes.focus)
        case "fixed":
            return new FixedConstraint()
        case "pan":
            return new PanMove()
        case "transform":
            return new Transform(attributes.loc,null,null)
        }
        return null
    }
    protected Object createNode(Object name, Map attributes, Object value){
        println "CreateNode(3)"
        println name
        println attributes
        println value      
    }
    
    def vec3(x,y,z){
        return new Vector3f(x,y,z)
    }
    /*
    def act(c){
    //c
    println "Call Act" + c.class.name
    }
     */

}

