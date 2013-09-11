package sg.atom.ai.movement.steering.behaviour;

import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import java.util.List;
import sg.atom.ai.movement.core.Obstacle;

public class ObstacleAvoid implements Behaviour {

    Vector3f location;
    Vector3f velocity;
    float collisionRadius;
    float speed;
    float turnSpeed;
    float tpf;
    List<Obstacle> obstacles;
    private Obstacle closestObs;

    public ObstacleAvoid() {
    }

    public ObstacleAvoid(Geometry g, Geometry f, Vector3f location, Vector3f velocity, float collisionRadius, float speed, float turnSpeed, float tpf, List<Obstacle> obstacles) {

        this.location = location;
        this.velocity = velocity;
        this.collisionRadius = collisionRadius;
        this.speed = speed;
        this.turnSpeed = turnSpeed;
        this.tpf = tpf;
        this.obstacles = obstacles;
    }

    public Vector3f calculateForce2(Vector3f location, Vector3f velocity, float collisionRadius, float speed, float turnSpeed, float tpf, List<Obstacle> obstacles) {
        float cautionRange = 10000.0F;
        com.jme3.math.Line line = new com.jme3.math.Line(location, velocity);
        Plane plane = new Plane(velocity, collisionRadius / 4.0F);
        Vector3f closest = Vector3f.POSITIVE_INFINITY;

        for (Obstacle obs : obstacles) {
            Vector3f loc = obs.getLocation().subtract(location);

            if (plane.whichSide(loc) != Plane.Side.Positive) {
                continue;
            }

            if (location.distance(obs.getLocation()) <= cautionRange) {
                if (line.distanceSquared(obs.getLocation()) - collisionRadius / 2.0F <= 0.0F) {
                    if (obs.getLocation().distanceSquared(location) < closest.distance(location)) {
                        closest = obs.getLocation();
                        this.closestObs = obs;
                    }
                }
            }
        }

        if (!closest.equals(Vector3f.POSITIVE_INFINITY)) {


            float dot = closest.subtract(location).dot(line.getDirection().cross(Vector3f.UNIT_Y));

            if (dot <= 0.0F) {
                return velocity.cross(Vector3f.UNIT_Y);
            }
            return velocity.cross(Vector3f.UNIT_Y).negate();
        }

        return Vector3f.ZERO;
    }

    public Vector3f calculateForce(Vector3f location, Vector3f velocity, float collisionRadius, float speed, float turnSpeed, float tpf, List<Obstacle> obstacles) {
        float cautionRange = speed / turnSpeed * tpf;

        Plane plane = new Plane(velocity, 1.0F);

        float r1 = cautionRange + collisionRadius;

        for (Obstacle obstacle : obstacles) {
            Vector3f loc = obstacle.getLocation().subtract(location);

            if (plane.whichSide(loc) != Plane.Side.Positive) {
                continue;
            }
            if (loc.lengthSquared() < (r1 + obstacle.getRadius()) * (r1 + obstacle.getRadius())) {
                Vector3f projPoint = plane.getClosestPoint(loc);
                if (projPoint.lengthSquared() < (collisionRadius + obstacle.getRadius()) * (collisionRadius + obstacle.getRadius())) {

                    return loc.negate();
                }
            }
        }

        return Vector3f.ZERO;
    }

    public Vector3f calculateForce() {
        return calculateForce(location, velocity, collisionRadius, speed, turnSpeed, tpf, obstacles);
    }

    public Obstacle getClosestObs() {
        return closestObs;
    }

    public float getCollisionRadius() {
        return collisionRadius;
    }
   
}
