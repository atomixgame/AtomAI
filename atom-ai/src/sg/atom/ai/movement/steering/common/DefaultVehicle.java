package sg.atom.ai.movement.steering.common;

import sg.atom.ai.movement.core.ISteerManager;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.HashMap;
import sg.atom.ai.movement.steering.behaviour.Behaviour;
import sg.atom.ai.movement.core.AbstractVehicle;
import sg.atom.ai.movement.core.Obstacle;

public class DefaultVehicle extends AbstractVehicle {

    private ISteerManager steerManager;
    protected SimpleObstacle me;
    boolean collideWarning;
    private boolean enable;
    private boolean stop;
    ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
    HashMap<Behaviour, Float> strengths = new HashMap<Behaviour, Float>();

    public DefaultVehicle(ISteerManager steerManager) {
        this.steerManager = steerManager;
        this.enable = true;
        this.stop = false;

        location = Vector3f.ZERO;
        speed = 1.5F;
        maxSpeed = 1.5F;
        maxTurnForce = 1.0F;
        mass = 1.0F;
        collisionRadius = 0.2F;
        velocity = Vector3f.ZERO;
        scale = 1;
    }

    @Override
    public void updateVelocity(Vector3f steeringInfluence, float scale) {
        super.updateVelocity(steeringInfluence, scale);
    }

    public void setCollideWarning(boolean warn) {
        this.collideWarning = warn;
    }

    public Obstacle toObstacle() {
        if (this.me == null) {
            this.me = new SimpleObstacle();
        }
        this.me.update(getWorldTranslation(), this.mass, this.velocity);
        return this.me;
    }

    @Override
    public void setLocation(Vector3f location) {
        super.setLocation(location);

    }

    public void updateLocation() {
        setLocation(getLocation().add(velocity.mult(scale)));
    }

    public void init() {
    }

    public boolean isEnable() {
        return enable;
    }

    public boolean isStop() {
        return stop;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public ArrayList<Behaviour> getBehaviours() {
        return behaviours;
    }

    public <T> T getBehaviourByClass(Class<T> clazz) {
        for (Behaviour b : behaviours) {
            if (clazz.isInstance(b)) {
                return (T) b;
            }
        }
        return null;
    }
}
