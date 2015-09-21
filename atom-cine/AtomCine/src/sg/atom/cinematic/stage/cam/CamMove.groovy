package sg.atom.cinematic.stage.cam

import com.jme3.math.Vector3f
import com.jme3.math.Transform
/**
 * A CamMove is like resulted steering behaviors for camera but including the change in the perspective, angle and point of interest.
 * <p>
 * <ul>
 * <li>CamMove is also an AtomCinematic Timing target and an JME's Cinematic Event to participate in a cut scene.</li>
 * <li>CamMove has its description come along with it, which can be decomposed to zero or more constraints (style,target,goal..) it have to sastify and behaviors.</li>
 * <li>CamMove can be blended together, by automatic blending constrainsts, or simple interpolate resulted positions and cam properties. Note that a heristic can be specific to choose how to blend or replace it completely.</li> 
 * <li>Some CamMove can be chained together to make a longer, smooth shot.</li>
 * <li>CamMove can also apply to an Spatial, only the transform are applied.</li>
 * <li>CamMove are unit of CamSetting, it can be pre-defined and customized via parameters but should not be scripted to protect the cleaness of the design.</li>
 * </ul>
 * </p>
 * @author cuong.nguyenmanh2
 */
public class CamMove{
    def constraints = []
    
    // Monitoring
    Vector3f pos
    Transform transform
    boolean immediate
    boolean updateNeed = false
}

