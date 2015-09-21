/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.searching.pathfinding.core;

import com.jme3.math.Vector3f;
import sg.atom.ai.framework.space.Cell;

/**
 *
 * @author cuongnguyen
 */
public class Waypoint {
    Vector3f position;
    Cell cell;

    /**
     * The cell which owns the waypoint
     */
    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    /**
     * 3D position of waypoint
     */
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Waypoint[position=" + position.x + ", " + position.z + " cell:" + cell + "]";
    }
    
}
