/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.movement.steering.behaviour;

import sg.atom.ai.movement.steering.group.VehicleGroup;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface GroupBehaviour extends Behaviour{

    //public abstract ArrayList<AbstractVehicle> getGroupMembers();
    public abstract VehicleGroup getGroup();
}
