package sg.atom.ai.movement.steering.control;

import sg.atom.ai.movement.steering.behaviour.Alignment;
import sg.atom.ai.movement.steering.behaviour.Cohesion;
import sg.atom.ai.movement.steering.behaviour.Persuit;
import sg.atom.ai.movement.steering.behaviour.Seek;
import sg.atom.ai.movement.steering.behaviour.Separation;
import sg.atom.ai.movement.core.ISteerManager;
import sg.atom.ai.movement.steering.common.DefaultVehicle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.List;
import jme3tools.navmesh.Path;

public class PathFollowerControl extends SimpleVehicleControl {

    protected Persuit persuit = new Persuit();
    protected Seek seek = new Seek();
    protected Alignment alignment = new Alignment();
    protected Cohesion cohesion = new Cohesion();
    protected Separation separation = new Separation();
    protected float radius = 10.0F;
    private float minDistanceToWaypoint = 5.0F;
    boolean arrived;
    Geometry g = new Geometry("kkk", new Sphere(6, 6, 0.3F));

    public PathFollowerControl(ISteerManager steerManager) {
        super(steerManager);
        this.arrived = false;
        /*
         Material mat = new Material(outer.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
         mat.setColor("Color", ColorRGBA.Red);
         this.g.setMaterial(mat);
         * 
         */
    }

    protected void controlUpdate(float tpf) {
        DefaultVehicle self = (DefaultVehicle) getVehicle();
        /*
         if (!self.getParent().hasChild(this.g)) {
         self.getParent().attachChild(this.g);
         }*/
        List neighbors = this.steerManager.neighboursNearby(self, this.radius);
        List obstacles = new ArrayList(neighbors);
        obstacles.addAll(this.steerManager.obstacalsNearby(self, this.radius));

        Vector3f seperationForce = this.separation.calculateForce(self.getWorldTranslation(), obstacles);
        Vector3f cohesionForce = this.cohesion.calculateForce(self.getWorldTranslation(), neighbors);
        Vector3f alignmentForce = this.alignment.calculateForce(self.velocity, neighbors);
        Vector3f momentumForce = self.velocity;
        Vector3f persuitForce = this.persuit.calculateForce(self.getWorldTranslation(), self.velocity, self.speed, 0.0F, tpf, Vector3f.ZERO, ((Path.Waypoint) self.flock.getPath().getWaypoints().get(self.flock.lastPath)).getPosition());

        cohesionForce.multLocal(0.5F);
        alignmentForce.multLocal(0.5F);
        seperationForce.multLocal(100.0F);
        momentumForce.multLocal(1.0F);
        persuitForce.multLocal(1.0F);

        Vector3f flock = seperationForce.add(cohesionForce).add(alignmentForce).add(momentumForce).add(persuitForce);
        try {
            Vector3f pivotLoc = self.flock.pivot.getWorldTranslation();
            Vector3f flockLoc = self.flock.getRelativePosition(self);
            this.g.setLocalTranslation(flockLoc.add(pivotLoc));
            Vector3f seekForce = this.seek.calculateForce(self.getWorldTranslation(), self.velocity, self.speed, self.flock.getRelativePosition(self).add(self.flock.pivot.getWorldTranslation()));
            flock.subtractLocal(cohesionForce).subtractLocal(seperationForce).addLocal(seekForce.mult(1.0F));
        } catch (Exception e) {
        }
        this.minDistanceToWaypoint = (self.collisionRadius * 5.0F);

        if (this.minDistanceToWaypoint >= ((Path.Waypoint) self.flock.getPath().getWaypoints().get(self.flock.lastPath)).getPosition().distance(self.getWorldTranslation())) {
            self.flock.lastPath += 1;
            if (self.flock.lastPath >= self.flock.getPath().getWaypoints().size()) {
                this.arrived = true;
                self.flock.lastPath -= 1;
                self.velocity = Vector3f.ZERO;
            }
        }

        self.updateVelocity(flock, tpf);
        super.controlUpdate(tpf);
    }
}
