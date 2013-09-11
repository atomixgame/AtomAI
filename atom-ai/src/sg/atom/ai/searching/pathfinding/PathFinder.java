/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.searching.pathfinding;

import sg.atom.ai.searching.pathfinding.space.grid.GridCell;
import sg.atom.ai.searching.pathfinding.space.grid.GridMap;

/**
 *
 * @author hungcuong
 */
public interface PathFinder {

    /**
     * Finds the shortest route through the map and returns an array
     * of all of the cells.
     */
    public GridCell[] findPath(GridMap map);
}
