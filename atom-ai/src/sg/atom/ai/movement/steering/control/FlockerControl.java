package sg.atom.ai.movement.steering.control;

import sg.atom.ai.movement.steering.behaviour.Alignment;
import sg.atom.ai.movement.steering.behaviour.Cohesion;
import sg.atom.ai.movement.steering.behaviour.Separation;
import sg.atom.ai.movement.core.AbstractVehicle;
import sg.atom.ai.movement.core.ISteerManager;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class FlockerControl extends SimpleVehicleControl {

    protected Alignment alignment = new Alignment();
    protected Cohesion cohesion = new Cohesion();
    protected Separation separation = new Separation();
    protected float radius = 10.0F;

    public FlockerControl(ISteerManager steerManager) {
        super(steerManager);
    }

    protected void controlUpdate(float tpf) {
        AbstractVehicle self = (AbstractVehicle) getVehicle();

        List neighbors = this.steerManager.neighboursNearby(self, this.radius);
        List obstacles = new ArrayList(neighbors);
        obstacles.addAll(this.steerManager.obstacalsNearby(self, this.radius));

        Vector3f seperationForce = this.separation.calculateForce(self.getWorldTranslation(), obstacles);
        Vector3f cohesionForce = this.cohesion.calculateForce(self.getWorldTranslation(), neighbors);
        Vector3f alignmentForce = this.alignment.calculateForce(self.velocity, neighbors);
        Vector3f momentumForce = self.velocity;

        cohesionForce.multLocal(1.0F);
        alignmentForce.multLocal(1.0F);
        seperationForce.multLocal(400.0F);
        momentumForce.multLocal(1.0F);

        Vector3f last = seperationForce.add(cohesionForce).add(alignmentForce).add(momentumForce);

        self.updateVelocity(last, tpf);
        super.controlUpdate(tpf);
    }
}
