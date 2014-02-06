package sg.atom.ai.movement.core;

import sg.atom.ai.movement.steering.group.Flock;
import sg.atom.ai.movement.steering.common.DefaultVehicle;
import com.jme3.math.Vector3f;

public abstract class AbstractVehicle {

    public Vector3f location = Vector3f.ZERO;
    public float speed;
    
    public float maxSpeed;
    public float maxTurnForce;
    
    public float mass;
    public float collisionRadius;
    public Vector3f velocity;
    public Flock<DefaultVehicle> flock;
    public float scale;

    public void updateVelocity(Vector3f steeringInfluence, float scale) {
        Vector3f steeringForce = truncate(steeringInfluence, this.maxTurnForce * scale);
        Vector3f acceleration = steeringForce.divide(this.mass);
        Vector3f vel = truncate(this.velocity.add(acceleration), this.maxSpeed);
        
        this.velocity = vel;
        this.velocity.y = 0.0F;
        this.scale = scale;
    }

    private Vector3f truncate(Vector3f source, float limit) {
        if (source.lengthSquared() <= limit * limit) {
            return source;
        }
        return source.normalize().scaleAdd(limit, Vector3f.ZERO);
    }

    public Vector3f getFuturePosition(float tpf) {
        return location.add(this.velocity);
    }

    public Vector3f getWorldTranslation() {
        return location;
    }

    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }
}
