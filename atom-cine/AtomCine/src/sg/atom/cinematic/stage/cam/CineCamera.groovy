package sg.atom.cinematic.stage.cam

import com.jme3.renderer.Camera
import com.jme3.math.Vector3f
/**
 * Warper for a 3D cam.
 * 
 * @author cuong.nguyenmanh2
 * 
 */
class CineCamera {
    Camera cam;
    def currentSetting
    
    CineCamera(Camera cam){
        this.cam = cam;
    }

    /*
    Level at:  The camera should offset at a certain 
    height relative to the object. 
     */
    def levelAt(){
        
    }
    /*
    Angle to line-of-action:  This constraint describes 
    the angle relative to the line-of-action. 
     */
    def angleToAction(){
       
    }
    /*
    Facing: Each object has a vector which defines 
    the front of the object. By setting the desired 
    viewing angle relative to object’s front direction 
    can create many useful shots. 
     */
    def facing(){
       
    }
    /*
    Size: This constraint controls the camera’s 
    distance to the target. 
     */
    def size(){
       
    }
    /*
    Height angle:  It instructs the camera to watch the 
    target at a specifi ed height angle. 
     */
    def heightAngle(){
       
    }
    /*
    View at angle (X and Y): This one determines 
    the position of the target on the screen. X angles, 
    other than 0.0, move the projection of the target 
    to the left or right side of screen; the Y angle is 
    used to push the target toward the top or the 
    bottom.
     */

    /* Visibility : The target should be visible to the 
    viewer, such that obstructions are avoided. */
    def visibility(){
    }
    

    def setLoc(v){
        cam.setLocation v
    }
    
    def focus(subject){
        currentSetting.focus subject
    }
}

