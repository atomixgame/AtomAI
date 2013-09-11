package sg.atom.ai.movement.steering.behaviour;

import com.jme3.math.Vector3f;
import jme3tools.navmesh.Cell;
import jme3tools.navmesh.NavMesh;

public class StayInsideMesh implements Behaviour {

    Vector3f location;
    Vector3f velocity;
    float speed;
    NavMesh navmesh;

    public StayInsideMesh() {
    }

    public StayInsideMesh(Vector3f location, Vector3f velocity, float speed, NavMesh navmesh) {
        this.location = location;
        this.velocity = velocity;
        this.speed = speed;
        this.navmesh = navmesh;
    }

    public Vector3f calculateForce(Vector3f location, Vector3f velocity, float speed, NavMesh navmesh) {
        Vector3f futurePosition = location.add(velocity.mult(speed));
        Cell cell = navmesh.findClosestCell(futurePosition);
        Vector3f snap = navmesh.snapPointToCell(cell, futurePosition.clone());

        if ((snap.x == futurePosition.x) && (snap.z == futurePosition.z)) {
            return Vector3f.ZERO;
        }

        Vector3f sub = snap.subtract(futurePosition);
        snap.addLocal(sub);

        Vector3f desierdVel = snap.subtract(location).normalize().mult(speed);
        Vector3f steering = desierdVel.subtract(velocity);

        return steering;
    }

    public Vector3f calculateForce() {
        return calculateForce(location, velocity, speed, navmesh);
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

    public NavMesh getNavmesh() {
        return navmesh;
    }

    public void setNavmesh(NavMesh navmesh) {
        this.navmesh = navmesh;
    }
}
