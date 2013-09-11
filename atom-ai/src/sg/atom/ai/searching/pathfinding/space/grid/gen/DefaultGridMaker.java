/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.searching.pathfinding.space.grid.gen;

import sg.atom.ai.searching.pathfinding.space.grid.GridMap;
import sg.atom.ai.searching.pathfinding.space.grid.GridMap;

/**
 *
 * @author hungcuong
 */
public class DefaultGridMaker implements GridMaker {
    /*
    void makeGridFromBoxes() {
    gridArray = new Geometry[row][col];
    
    for (int r = 0; r < row; r++) {
    for (int c = 0; c < col; c++) {
    float size = 0.4f;
    Box aBox = new Box(Vector3f.ZERO, size, size / 10, size);
    Geometry aGeo = new Geometry("gridBox" + r + "_" + c, aBox);
    aGeo.setMaterial(defaultMat);
    gridArray[r][c] = aGeo;
    
    currentNode.attachChild(aGeo);
    aGeo.setLocalTranslation(r * size * 2.2f, 0, c * size * 2.2f);
    }
    }
    }
     */

    public void fill(GridMap grid) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
