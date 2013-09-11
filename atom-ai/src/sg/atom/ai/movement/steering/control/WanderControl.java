package sg.atom.ai.movement.steering.control;

import sg.atom.ai.movement.core.ISteerManager;
import sg.atom.ai.movement.steering.behaviour.Randomness;
import sg.atom.ai.movement.core.AbstractVehicle;

public class WanderControl extends SimpleVehicleControl {

    protected Randomness randomness = new Randomness();

    public WanderControl(ISteerManager steerManager) {
        super(steerManager);
    }

    @Override
    protected void controlUpdate(float tpf) {
        AbstractVehicle vehicle = (AbstractVehicle) getVehicle();
        if (vehicle == null) {
            return;
        }

        vehicle.updateVelocity(this.randomness.calculateForce(), tpf);
        super.controlUpdate(tpf);
    }
}
