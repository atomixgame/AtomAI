package sg.atom.cinematic.stage.cam.planer

import com.jme3.math.*
import static com.jme3.math.FastMath.*
import static com.jme3.math.Vector3f.*
class CamMoveHelper{
    
    def cam
    
    // Postion -------------------------------
    // Front
    def frontOfCharacter(character,dis){
        
        cam.setLocation(character.getPos() + character.getFrontVec() * dis + vec3(0,character.getHeight(),0))
        cam.lookAt(character.getPos(),vec3(0,1,0))
    }
    
    // Sightline
    
    // Range
    
    // Face
    // Rotation -----------------------------
    
    // Character analysing ------------------
    def characterInfo(){
        return [pos:vec3(1,1,1),rot:quat(),height:vec3(1,1,1),head:vec3(1,1,1)]
    }
    
    // Short cuts for math ------------------
    def quat(){
        return new Quaternion();
    }
    def midLineAction(chars,fov){
        def wide = 3f
        def la = lineAction(chars)
        def m = (la.start + la.end) / 2
        def d = la.start.distance(la.end) / 2 * wide
        def exl = d / Math.tan(fov/2)
        def yaw90 = new Quaternion(); 
        yaw90.fromAngleAxis((float) (PI / 2f) , new Vector3f(0,1,0)); 
        
        def ex = ((m - la.start) * yaw90).normalize() * exl + m
        cam.location = ex + vec3(0,chars[0].height,0)
        cam.lookAt m , UNIT_Y
    }
    def lineAction(chars){
        assert chars.size >= 2
        return [start:chars[0].getPos(),end:chars[1].getPos()]
    }
    def vec3(x,y,z){
        return new Vector3f(x,y,z)
    }
}
