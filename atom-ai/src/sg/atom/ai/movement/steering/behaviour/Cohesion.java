package sg.atom.ai.movement.steering.behaviour;

import com.jme3.math.Vector3f;
import java.util.List;
import sg.atom.ai.movement.core.Obstacle;

public class Cohesion
        implements Behaviour {

    Vector3f velocity;
    List<Obstacle> neighbors;
    public Cohesion() {
    }
    
    public Cohesion(Vector3f velocity, List<Obstacle> neighbors) {
        this.velocity = velocity;
        this.neighbors = neighbors;
    }





    public Vector3f calculateForce() {
        return calculateForce(velocity, neighbors);
    }

    public Vector3f calculateForce(Vector3f location, List<Obstacle> neighbors) {
        if (neighbors.isEmpty()) {
            return Vector3f.ZERO;
        }

        Vector3f cohesion = new Vector3f();

        int count = 0;
        for (Obstacle v : neighbors) {
            if (!v.getVelocity().equals(Vector3f.ZERO)) {
                count++;
                cohesion.addLocal(location.subtract(v.getLocation()));
            }
        }

        if (count > 0) {
            cohesion.divideLocal(count);
        }

        return cohesion.negate();
    }
    public Vector3f getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3f velocity) {
        this.velocity = velocity;
    }

    public List<Obstacle> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Obstacle> neighbors) {
        this.neighbors = neighbors;
    }
    

}
