/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.movement.steering.pipe;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import sg.atom.ai.movement.steering.behaviour.Alignment;
import sg.atom.ai.movement.steering.behaviour.Behaviour;

/**
 *
 * @author hungcuong
 */
public class CompoundBehaviour implements Behaviour {

    ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
    HashMap<Behaviour, Float> strengths = new HashMap<Behaviour, Float>();
    private Alignment alignment;
    Vector3f MAX_STEERING_FORCE;
    
    public Vector3f calculateForce() {
        return alignment.calculateForce();
    }
}
