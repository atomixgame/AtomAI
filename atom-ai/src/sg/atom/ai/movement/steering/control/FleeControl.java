package sg.atom.ai.movement.steering.control;

import sg.atom.ai.movement.steering.behaviour.Flee;
import sg.atom.ai.movement.core.AbstractVehicle;
import com.jme3.math.Vector3f;
import sg.atom.ai.movement.core.ISteerManager;

public class FleeControl extends SimpleVehicleControl {

    private Flee flee = new Flee();
    private AbstractVehicle target;

    public FleeControl(ISteerManager steerManager, AbstractVehicle target) {
        super(steerManager);
        this.target = target;
    }

    protected void controlUpdate(float tpf) {
        AbstractVehicle vehicle = (AbstractVehicle) getVehicle();
        if (vehicle == null) {
            return;
        }

        Vector3f steering = this.flee.calculateForce(vehicle.getWorldTranslation(), vehicle.velocity, vehicle.speed, this.target.getWorldTranslation());

        vehicle.updateVelocity(steering, tpf);
        super.controlUpdate(tpf);
    }
}
