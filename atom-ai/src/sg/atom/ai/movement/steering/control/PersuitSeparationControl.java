package sg.atom.ai.movement.steering.control;

import sg.atom.ai.movement.steering.behaviour.Persuit;
import sg.atom.ai.movement.steering.behaviour.Separation;
import sg.atom.ai.movement.core.AbstractVehicle;
import sg.atom.ai.movement.core.Obstacle;
import sg.atom.ai.movement.core.ISteerManager;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class PersuitSeparationControl extends SimpleVehicleControl {

    private Persuit persuit = new Persuit();
    private Separation separation = new Separation();
    private List<Obstacle> neighbours = new ArrayList();
    private AbstractVehicle target;
    private float radius = 5.0F;

    public PersuitSeparationControl(ISteerManager steerManager, AbstractVehicle target) {
        super(steerManager);
        this.target = target;
    }

    protected void controlUpdate(float tpf) {
        AbstractVehicle vehicle = (AbstractVehicle) getVehicle();
        if (vehicle == null) {
            return;
        }
        this.neighbours.clear();
        this.neighbours.addAll(this.steerManager.neighboursNearby(vehicle, this.radius));

        Vector3f steering = this.persuit.calculateForce(vehicle.getWorldTranslation(), vehicle.velocity, vehicle.speed, this.target.speed, tpf, this.target.velocity, this.target.getFuturePosition(tpf));
        Vector3f separate = this.separation.calculateForce(vehicle.getWorldTranslation(), this.neighbours);
        //System.out.println(" Num of nei :" +neighbours.size());
        Vector3f combine = separate;//steering.add(separate);
        vehicle.updateVelocity(combine, tpf);
        super.controlUpdate(tpf);
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
