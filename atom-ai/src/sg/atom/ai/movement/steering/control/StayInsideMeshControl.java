package sg.atom.ai.movement.steering.control;

import sg.atom.ai.movement.steering.behaviour.StayInsideMesh;
import sg.atom.ai.movement.core.AbstractVehicle;
import com.jme3.math.Vector3f;
import sg.atom.ai.framework.space.navmesh.NavMesh;
import sg.atom.ai.movement.core.ISteerManager;

public class StayInsideMeshControl extends SimpleVehicleControl {

    private NavMesh navmesh;
    private StayInsideMesh stayInsideMesh = new StayInsideMesh();

    public StayInsideMeshControl(ISteerManager steerManager, NavMesh navmesh) {
        super(steerManager);
        this.navmesh = navmesh;
    }

    protected void controlUpdate(float tpf) {
        AbstractVehicle vehicle = (AbstractVehicle) getVehicle();

        Vector3f force = this.stayInsideMesh.calculateForce(vehicle.getWorldTranslation(), vehicle.velocity, vehicle.speed, this.navmesh);

        vehicle.updateVelocity(force, tpf);
        super.controlUpdate(tpf);
    }
}
