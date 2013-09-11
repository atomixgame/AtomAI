package sg.atom.ai.movement.steering.control;

import sg.atom.ai.movement.steering.behaviour.ObstacleAvoid;
import sg.atom.ai.movement.core.AbstractVehicle;
import sg.atom.ai.movement.core.Obstacle;
import sg.atom.ai.movement.core.ISteerManager;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class AvoidControl extends SimpleVehicleControl {

    private ObstacleAvoid avoid = new ObstacleAvoid();
    private List<Obstacle> obstacles = new ArrayList();

    public AvoidControl(ISteerManager steerManager) {
        super(steerManager);
    }

    protected void controlUpdate(float tpf) {
        AbstractVehicle vehicle = (AbstractVehicle) getVehicle();
        if (vehicle == null) {
            return;
        }

        if (this.obstacles.isEmpty()) {
            this.obstacles.addAll(this.steerManager.getObstacals());
        }

        Vector3f avoidance = this.avoid.calculateForce2(vehicle.getWorldTranslation(), vehicle.velocity, vehicle.collisionRadius, vehicle.speed, vehicle.maxTurnForce, tpf, this.obstacles);

        vehicle.updateVelocity(avoidance, tpf);
        super.controlUpdate(tpf);
    }
}
