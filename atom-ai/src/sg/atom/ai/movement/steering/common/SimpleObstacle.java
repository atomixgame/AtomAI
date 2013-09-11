package sg.atom.ai.movement.steering.common;

import com.jme3.math.Vector3f;
import sg.atom.ai.movement.core.Obstacle;

public class SimpleObstacle
        implements Obstacle {

    public Vector3f location;
    public float radius;
    public Vector3f velocity;

    public SimpleObstacle() {
        this.location = new Vector3f();
        this.velocity = new Vector3f();
    }

    public SimpleObstacle(Vector3f location, float radius, Vector3f velocity) {
        this.location = location;
        this.radius = radius;
        this.velocity = velocity;
    }

    public Vector3f getVelocity() {
        return this.velocity;
    }

    public Vector3f getLocation() {
        return this.location;
    }

    public float getRadius() {
        return this.radius;
    }

    void update(Vector3f location, float radius, Vector3f velocity) {
        this.location.set(location);
        this.radius = radius;
        this.velocity.set(velocity);
    }
}
