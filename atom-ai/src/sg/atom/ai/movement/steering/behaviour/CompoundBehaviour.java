/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.movement.steering.behaviour;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author hungcuong
 */
public class CompoundBehaviour implements Behaviour {

    ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
    HashMap<Behaviour, Float> strengths = new HashMap<Behaviour, Float>();
    private Alignment alignment;

    public Vector3f calculateForce() {
        return alignment.calculateForce();
    }
}
