/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.searching.pathfinding.space.grid;

/**
 *
 * @author hungcuong
 */
public interface GridMapListener {

    void onGridChanged(GridCell gridCell);

    void onMapChanged(GridMap map);
}
