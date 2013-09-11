package sg.atom.ai.movement.steering.control;

import sg.atom.ai.movement.steering.behaviour.Evade;
import sg.atom.ai.movement.core.AbstractVehicle;
import com.jme3.math.Vector3f;
import sg.atom.ai.movement.core.ISteerManager;

public class EvadeControl extends SimpleVehicleControl {

    private Evade evade = new Evade();
    private AbstractVehicle target;

    public EvadeControl(ISteerManager steerManager, AbstractVehicle target) {
        super(steerManager);
        this.target = target;
    }

    protected void controlUpdate(float tpf) {
        AbstractVehicle vehicle = (AbstractVehicle) getVehicle();
        if (vehicle == null) {
            return;
        }

        Vector3f steering = this.evade.calculateForce(vehicle.getWorldTranslation(), vehicle.velocity, vehicle.speed, this.target.speed, tpf, this.target.velocity, this.target.getFuturePosition(tpf));

        vehicle.updateVelocity(steering, tpf);
        super.controlUpdate(tpf);
    }
}
