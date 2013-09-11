package sg.atom.ai.movement.steering.control;

import sg.atom.ai.movement.steering.behaviour.ObstacleAvoid;
import sg.atom.ai.movement.steering.behaviour.Persuit;
import sg.atom.ai.movement.steering.behaviour.Separation;
import sg.atom.ai.movement.core.AbstractVehicle;
import sg.atom.ai.movement.core.Obstacle;
import sg.atom.ai.movement.core.ISteerManager;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;

public class PersuitAvoidSeparationControl extends SimpleVehicleControl {

    private Persuit persuit = new Persuit();
    private ObstacleAvoid avoid = new ObstacleAvoid();
    private Separation separation = new Separation();
    private List<Obstacle> obstacles = new ArrayList();
    private List<Obstacle> neighbours = new ArrayList();
    private AbstractVehicle target;
    private float radius = 5.0F;

    public PersuitAvoidSeparationControl(ISteerManager steerManager, AbstractVehicle target) {
        super(steerManager);
        this.target = target;

    }

    protected void controlUpdate(float tpf) {
        AbstractVehicle vehicle = (AbstractVehicle) getVehicle();
        if (vehicle == null) {
            return;
        }

        if (this.obstacles.isEmpty()) {
            this.obstacles.addAll(this.steerManager.getObstacals());
        }
        this.neighbours.clear();
        this.neighbours.addAll(this.steerManager.neighboursNearby(vehicle, this.radius));

        Vector3f steering = this.persuit.calculateForce(vehicle.getWorldTranslation(), vehicle.velocity, vehicle.speed, this.target.speed, tpf, this.target.velocity, this.target.getFuturePosition(tpf));
        Vector3f avoidance = this.avoid.calculateForce(vehicle.getWorldTranslation(), vehicle.velocity, vehicle.speed, vehicle.collisionRadius, vehicle.maxTurnForce, tpf, this.obstacles);
        Vector3f separate = this.separation.calculateForce(vehicle.getWorldTranslation(), this.neighbours);

        vehicle.updateVelocity(steering.add(avoidance).add(separate), tpf);
        super.controlUpdate(tpf);
    }
}
