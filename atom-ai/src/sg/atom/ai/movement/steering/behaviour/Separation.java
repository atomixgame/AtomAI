package sg.atom.ai.movement.steering.behaviour;

import com.jme3.math.Vector3f;
import java.util.List;
import sg.atom.ai.movement.core.Obstacle;

public class Separation implements Behaviour {

    Vector3f location;
    List<Obstacle> neighbours;

    public Separation() {
    }

    public Separation(Vector3f location, List<Obstacle> neighbours) {
        this.location = location;
        this.neighbours = neighbours;
    }

    public Vector3f calculateForce(Vector3f location, List<Obstacle> neighbours) {
        Vector3f steering = new Vector3f();

        int count = 0;
        //Vector3f avgPoint = new Vector3f();
        for (Obstacle o : neighbours) {

            Vector3f loc = o.getLocation().subtract(location).negate();
            float len2 = loc.lengthSquared();

            steering.x += loc.x / (len2 * len2 + 1.0F);
            steering.y += loc.y / (len2 * len2 + 1.0F);
            steering.z += loc.z / (len2 * len2 + 1.0F);

            //steering.subtractLocal(loc.divide(len2 * len2 + 1.0F)).negateLocal();
            count++;
            //avgPoint.addLocal(o.getLocation());
        }

        if (count == 0) {
            return Vector3f.ZERO;
        } else {
            //avgPoint.divideLocal(count);
            //steering = location.subtract(avgPoint).negate().mult(1.5f);
            //return steering;
        }
        return steering.divide(count);
    }

    public Vector3f calculateForce() {
        return calculateForce(location, neighbours);
    }

    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }

    public List<Obstacle> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Obstacle> neighbours) {
        this.neighbours = neighbours;
    }
}
