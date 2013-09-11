package sg.atom.ai.movement.steering.control;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import sg.atom.ai.movement.steering.common.DefaultVehicle;
import sg.atom.ai.movement.core.AbstractVehicle;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.util.ArrayList;
import java.util.HashMap;
import sg.atom.ai.movement.core.ISteerManager;
import sg.atom.ai.movement.steering.behaviour.Behaviour;

public class SimpleVehicleControl extends AbstractControl {

    DefaultVehicle vehicle;
    protected ISteerManager steerManager;
    private boolean spatialToVehicle = false;
    private boolean physicEnable = false;

    public SimpleVehicleControl(ISteerManager steerManager) {
        this.steerManager = steerManager;
    }

    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        return this;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (vehicle.isEnable()) {
            if (!vehicle.isStop()) {
                if (spatialToVehicle) {
                    relax(tpf);
                } else {
                    vehicle.updateLocation();
                    //System.out.println(" Update vehicle");
                    updateLocation(tpf);

                }
            }
        }
        /*
         if (spatial instanceof SimpleVehicleSpatial) {

         ((SimpleVehicleSpatial) spatial).updateView();
            

         }
         */
    }

    protected void updateLocation(float tpf) {
        Quaternion rotTo = spatial.getLocalRotation().clone();
        rotTo.lookAt(this.vehicle.velocity.normalize(), Vector3f.UNIT_Y);
        if (!physicEnable) {
            spatial.setLocalRotation(rotTo);
            spatial.setLocalTranslation(vehicle.getLocation());
        } else {
            // let drive the physic 
        }
    }

    protected void relax(float tpf) {
        vehicle.setLocation(spatial.getLocalTranslation());
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        if (physicEnable) {
            setupPhysicDriver();
        }
    }

    public void setVehicle(DefaultVehicle vehicle) {
        this.vehicle = vehicle;
        //updateLocation(0);
    }

    AbstractVehicle getVehicle() {
        return this.vehicle;
    }

    public void setSpatialToVehicle(boolean b) {
        this.spatialToVehicle = b;
    }

    private void setupPhysicDriver() {
        //physicControl = new ();
    }
}
