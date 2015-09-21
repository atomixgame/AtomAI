package sg.atom.cinematic.stage.cam


import com.jme3.math.Vector3f
import com.jme3.math.Transform
import sg.atom.cinematic.stage.cam.constraint.*

/**
 * CamSetting is a preset (descripted version) of moves and constrainsts. 
 * <p>Consider CamSetting is a premade version beside of CamMove which is a runtime version. A CamSetting can be decomposed into several of CamMoves</p>
 * <p><ul><li>CamSetting always have an initial state (pos,move,configs..).</li>
 * <li>A CamSetting come with (optional) location, timing, duration or trigger of where, when, how the settings will take place in the cinematic timeline or gameplay.</li>
 * <li>A setting is imperative, it completely replace current setting but the underlying move will be blended to retrieve a smooth move.</li>
 * <li>A CamSetting can also be applied to a spatial, only move are applied accordingly.</li>
 * <li>A CamSetting is the original unit of Cam movement system and can be descripted in scripts(groovy,xml..)</li></ul></p>
 * @author cuong.nguyenmanh2
 */
class CamSetting {
    def move
    def constraints = []
    
    // Monitoring
    Vector3f pos
    Transform transform
    boolean immediate = true
    boolean updateNeed = false
    
    def change(cine,cam){
        cam.currentSetting = this
        //cam.
        constraints.each{ct->
            ct.change(cam)
        }
        
        if (constraints.any{ct->isNeedUpdateConstraintType(ct)}){
            updateNeed = true
            cine.markUpdate(this)
        }
    }
    
    def update(float tpf,cam){
        constraints.findAll{ct->isNeedUpdateConstraintType(ct)
        }.each{ct->
            ct.change(cam)
        }
    }
    
    def isNeedUpdateConstraintType(ct){
        ct instanceof DialogueConstraint
    }
       
    def focus(who){
        constraints.findAll{ct->ct instanceof DialogueConstraint
        }.each{ct->
            ct.style = "Talk1"
            ct.focus = who
        }
    }
}

