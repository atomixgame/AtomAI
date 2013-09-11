package sg.atom.ai.movement.steering.behaviour;

import com.jme3.math.Vector3f;

public class Seek implements Behaviour {

    Vector3f location;
    Vector3f velocity;
    float speed;
    Vector3f target;

    public Seek() {
    }

    public Seek(Vector3f location, Vector3f velocity, float speed, Vector3f target) {
        this.location = location;
        this.velocity = velocity;
        this.speed = speed;
        this.target = target;
    }

    public Vector3f calculateForce(Vector3f location, Vector3f velocity, float speed, Vector3f target) {
        Vector3f desierdVel = target.subtract(location).normalize().mult(speed);
        Vector3f steering = desierdVel.subtract(velocity);

        return steering;
    }

    public Vector3f calculateForce() {
        return calculateForce(location, velocity, speed, target);
    }

    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }

    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Vector3f getTarget() {
        return target;
    }

    public void setTarget(Vector3f target) {
        this.target = target;
    }
}
