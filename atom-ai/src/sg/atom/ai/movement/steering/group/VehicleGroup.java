/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.movement.steering.group;

import java.util.ArrayList;
import sg.atom.ai.movement.core.AbstractVehicle;

/**
 *
 * @author cuong.nguyenmanh2
 */
public interface VehicleGroup {
    public abstract ArrayList<AbstractVehicle> getGroupMembers();
}
