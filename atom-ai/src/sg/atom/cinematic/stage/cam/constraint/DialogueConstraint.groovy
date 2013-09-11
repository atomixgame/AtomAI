package sg.atom.cinematic.stage.cam.constraint

import sg.atom.cinematic.stage.cam.*
import sg.atom.cinematic.stage.cam.planer.CamMoveHelper
import com.jme3.math.*
import static com.jme3.math.FastMath.*
/**
 *
 * @author cuong.nguyenmanh2
 */
class DialogueConstraint extends CamConstraint {
    def type ="Normal"
    def characters = []
    def focus
    def camHelper = new CamMoveHelper();
    
    
    public void change(cam){
        camHelper.cam = cam.cam
        if (type=="Talk"){
            def fc = (focus!=null)?focus:characters[0];
            camHelper.frontOfCharacter(fc,4f)
        } else if (type=="Normal"){
            camHelper.midLineAction(characters,PI / 4)
        }
    }
        
    
    
    public void update(cam){
        
    }
}

