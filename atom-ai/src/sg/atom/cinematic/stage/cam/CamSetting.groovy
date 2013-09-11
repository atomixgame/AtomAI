package sg.atom.cinematic.stage.cam


import com.jme3.math.Vector3f
import com.jme3.math.Transform
import sg.atom.cinematic.stage.cam.constraint.*

/**
 *
 * @author cuong.nguyenmanh2
 */
class CamSetting {
    def move
    def constraints = []
    Vector3f pos
    Transform transform
    boolean immediate
    boolean updateNeed = false
    
    def change(cine,cam){
        cam.currentSetting = this
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
            ct.type = "Talk"
            ct.focus = who
        }
    }
}

