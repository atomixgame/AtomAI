package sg.atom.ai.movement.steering.control;

import com.jme3.math.Vector3f;
import sg.atom.ai.movement.core.ISteerManager;
import sg.atom.ai.movement.steering.behaviour.Randomness;
import sg.atom.ai.movement.core.AbstractVehicle;

public class MoveStraightControl extends SimpleVehicleControl {

    protected Randomness randomness = new Randomness();

    public MoveStraightControl(ISteerManager steerManager) {
        super(steerManager);
    }

    protected void controlUpdate(float tpf) {
        AbstractVehicle vehicle = (AbstractVehicle) getVehicle();
        if (vehicle == null) {
            return;
        }

        vehicle.updateVelocity(Vector3f.ZERO, tpf);
        super.controlUpdate(tpf);
    }
}
