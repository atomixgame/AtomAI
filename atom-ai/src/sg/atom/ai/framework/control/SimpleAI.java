/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.framework.control;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import sg.atom.ai.movement.steering.common.SteerManager;
import sg.atom.ai.movement.steering.control.SimpleVehicleControl;
import sg.atom.world.SceneGraphHelper;

public class SimpleAI extends AbstractControl implements AnimEventListener {
    // simple state machine
    // decision tree

    String defaultState = "Standing";
    String currentState;
    String inEvent = "Stop";
    boolean isActive;
    // 
    // Position
    //Vector3f currentPos;
    float speed = 50;
    Vector3f targetPos;
    // Helper Control
    AnimControl animControl;
    AnimChannel baseAnimChannel, topAnimChannel;

    /*
     public UnitAI(SpatialEntity thisEntity, String currentState, boolean isActive) {
     this.currentState = currentState;
     this.isActive = isActive;
     this.thisEntity = thisEntity;
     }
     */
    private SimpleVehicleControl movementControl;
    private SteerManager steerManager;

    public SimpleAI(String currentState, SteerManager steerManager) {
        this.currentState = currentState;
        this.steerManager = steerManager;

    }

    public void setInEvent(String eventName) {
        inEvent = eventName;
    }

    public void changeState(String otherState) {
        currentState = otherState;

        if (currentState.equals("Moving")) {
            baseAnimChannel.setAnim("RunBase", 0.5f);
            baseAnimChannel.setAnim("RunTop", 0.5f);

        } else if (currentState.equals("Standing")) {
            baseAnimChannel.setAnim("IdleBase", 0.5f);
            baseAnimChannel.setAnim("IdleTop", 0.5f);
        }
    }

    public void updateAI(float tpf) {
        if (currentState.equals("Standing")) {
            if (inEvent != null) {
                if (inEvent.equals("Move")) {
                    changeState("Moving");
                    goToPos(targetPos, tpf);
                    //changeState("Moving");

                } else {
                    // Continue Stop
                    // Do nothing
                }
            }
        } else if (currentState.equals("Moving")) {
            if (inEvent != null) {
                if (inEvent.equals("Stop")) {
                    changeState("Standing");
                    stop();

                } else {
                    // Continue moving
                    goToPos(targetPos, tpf);
                }
            }
        }
    }

    // Helper entity Funtions
    private void goToPos(Vector3f targetPos, float tpf) {
        //playAniIfNot("Run");
        boolean reach = false;
        Vector3f currentPos = spatial.getLocalTranslation();

        float distance = currentPos.distance(targetPos);
        //System.out.println("(*) MOVE         Current:" + currentPos + " Target : " + targetPos + " Dis : " + distance);
        Vector3f newPos = new Vector3f();
        if (speed * tpf <= distance) {
            newPos = currentPos.clone().interpolate(targetPos, speed * tpf / distance);
        } else {
            newPos.set(targetPos);
            reach = true;
        }


        if (reach) {
            inEvent = "Stop";
        }
        //changeState("Standing");
    }

    private void stop() {
        //playAni("Stand");
    }
    /*
     private void playAni(String animName) {
     animChannel.setAnim(animName, 0.4f);
     }

     private void playAniIfNot(String animName) {
     if (!animChannel.getAnimationName().equals(animName)) {
     animChannel.setAnim(animName, 0.4f);
     }
     }
     */

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        try {
            // Animation Control

            animControl = SceneGraphHelper.travelDownFindFirstControl(spatial, AnimControl.class);

            //animChannel.setAnim("Standingaround");
            for (String animName : animControl.getAnimationNames()) {
                System.out.println(" Animations: " + animName);
            }

            topAnimChannel = animControl.createChannel();
            topAnimChannel.setLoopMode(LoopMode.Loop);

            baseAnimChannel = animControl.createChannel();
            baseAnimChannel.setLoopMode(LoopMode.Loop);

            baseAnimChannel.setAnim("RunBase", 0.5f);
            baseAnimChannel.setAnim("RunTop", 0.5f);

        } catch (NullPointerException ex) {
            throw new IllegalStateException("You have to add this Control after Animcontrol");
        }

        try {
            movementControl = spatial.getControl(SimpleVehicleControl.class);
            initAIVar();
        } catch (NullPointerException ex) {
            throw new IllegalStateException("You have to add this Control after SimpleVehicleControl");
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        updateAI(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        return this;
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        // finish Anim
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
    }

    public void setTargetPos(Vector3f targetPos) {
        this.targetPos.set(targetPos);
    }

    private void initAIVar() {
        changeState("Standing");
        targetPos = new Vector3f();
    }
}
