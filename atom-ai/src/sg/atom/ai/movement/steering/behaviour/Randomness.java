package sg.atom.ai.movement.steering.behaviour;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

public class Randomness implements Behaviour {

    protected float scale = 0.03F;
    protected Vector3f direction = new Vector3f();
    protected Vector3f steering = new Vector3f();

    public Randomness() {
    }

    public Randomness(float scale) {
        this.scale = scale;
    }

    public Vector3f calculateForce() {
        Vector3f jiggle = new Vector3f();
        do {
            jiggle.x = (FastMath.nextRandomFloat() * 2.0F - 1.0F);
            jiggle.z = (FastMath.nextRandomFloat() * 2.0F - 1.0F);
        } while (jiggle.lengthSquared() > 1.0F);

        jiggle.multLocal(this.scale);
        this.direction.addLocal(jiggle);
        this.direction.normalizeLocal();

        this.steering.addLocal(this.direction).normalizeLocal();

        return this.steering;
    }
}
