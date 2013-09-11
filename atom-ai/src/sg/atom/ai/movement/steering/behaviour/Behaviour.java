package sg.atom.ai.movement.steering.behaviour;

import com.jme3.math.Vector3f;

public abstract interface Behaviour {

    public Vector3f calculateForce();
}
