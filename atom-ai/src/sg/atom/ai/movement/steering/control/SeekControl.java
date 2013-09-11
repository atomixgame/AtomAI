package sg.atom.ai.movement.steering.control;

import sg.atom.ai.movement.steering.behaviour.Seek;
import sg.atom.ai.movement.core.AbstractVehicle;
import com.jme3.math.Vector3f;
import sg.atom.ai.movement.core.ISteerManager;

public class SeekControl extends SimpleVehicleControl {

    private Seek seek = new Seek();
    private AbstractVehicle target;

    public SeekControl(ISteerManager steerManager, AbstractVehicle target) {
        super(steerManager);
        this.target = target;
    }

    protected void controlUpdate(float tpf) {
        AbstractVehicle vehicle = (AbstractVehicle) getVehicle();
        if (vehicle == null) {
            return;
        }

        Vector3f steering = this.seek.calculateForce(vehicle.getWorldTranslation(), vehicle.velocity, vehicle.speed, this.target.getWorldTranslation());

        vehicle.updateVelocity(steering, tpf);
        super.controlUpdate(tpf);
    }
}
