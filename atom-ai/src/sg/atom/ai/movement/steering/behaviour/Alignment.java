package sg.atom.ai.movement.steering.behaviour;

import com.jme3.math.Vector3f;
import java.util.List;
import sg.atom.ai.movement.core.Obstacle;

public class Alignment
        implements Behaviour {

    Vector3f velocity;
    List<Obstacle> neighbors;

    public Alignment() {
    }

    public Alignment(Vector3f velocity, List<Obstacle> neighbors) {
        this.velocity = velocity;
        this.neighbors = neighbors;
    }

    public Vector3f calculateForce() {
        return calculateForce(velocity, neighbors);
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
    

    public Vector3f calculateForce(Vector3f velocity, List<Obstacle> neighbors) {
        if (neighbors.isEmpty()) {
            return Vector3f.ZERO;
        }

        Vector3f alignment = new Vector3f();

        int count = 0;
        for (Obstacle v : neighbors) {
            if (!v.getVelocity().equals(Vector3f.ZERO)) {
                alignment.addLocal(v.getVelocity());
                count++;
            }
        }

        if (count > 0) {
            alignment.divideLocal(neighbors.size());
        }

        return alignment.subtract(velocity);
    }
}
