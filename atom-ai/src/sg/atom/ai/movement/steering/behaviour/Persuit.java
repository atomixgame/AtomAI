package sg.atom.ai.movement.steering.behaviour;

import com.jme3.math.Vector3f;

public class Persuit
        implements Behaviour {

    Vector3f location;
    Vector3f velocity;
    float speed;
    float targetSpeed;
    float tpf;
    Vector3f targetVelocity;
    Vector3f targetLocation;

    public Persuit() {
    }

    public Persuit(Vector3f location, Vector3f velocity, float speed, float targetSpeed, float tpf, Vector3f targetVelocity, Vector3f targetLocation) {
        this.location = location;
        this.velocity = velocity;
        this.speed = speed;
        this.targetSpeed = targetSpeed;
        this.tpf = tpf;
        this.targetVelocity = targetVelocity;
        this.targetLocation = targetLocation;
    }

    public Vector3f calculateForce(Vector3f location, Vector3f velocity, float speed, float targetSpeed, float tpf, Vector3f targetVelocity, Vector3f targetLocation) {
        float speedDiff = targetSpeed - speed;
        float desiredSpeed = (targetSpeed + speedDiff) * tpf;
        Vector3f projectedLocation = targetLocation.add(targetVelocity.mult(desiredSpeed));
        Vector3f desierdVel = projectedLocation.subtract(location).normalize().mult(speed);
        Vector3f steering = desierdVel.subtract(velocity);

        return steering;
    }

    public Vector3f calculateForce() {
        return calculateForce(location, velocity, speed, targetSpeed, tpf, targetVelocity, targetLocation);
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

    public float getTargetSpeed() {
        return targetSpeed;
    }

    public void setTargetSpeed(float targetSpeed) {
        this.targetSpeed = targetSpeed;
    }

    public float getTpf() {
        return tpf;
    }

    public void setTpf(float tpf) {
        this.tpf = tpf;
    }

    public Vector3f getTargetVelocity() {
        return targetVelocity;
    }

    public void setTargetVelocity(Vector3f targetVelocity) {
        this.targetVelocity = targetVelocity;
    }

    public Vector3f getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Vector3f targetLocation) {
        this.targetLocation = targetLocation;
    }
}
