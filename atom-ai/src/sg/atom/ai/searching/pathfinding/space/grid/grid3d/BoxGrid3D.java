/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.searching.pathfinding.space.grid.grid3d;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import sg.atom.ai.searching.pathfinding.space.grid.GridCell;
import sg.atom.ai.searching.pathfinding.space.grid.control.GridCellManager;
import sg.atom.ai.searching.pathfinding.space.grid.control.GridCellPresentor;
import sg.atom.ai.searching.pathfinding.space.grid.control.GridColorControl;
import sg.atom.ai.searching.pathfinding.space.grid.GridMapListener;
import sg.atom.ai.searching.pathfinding.space.grid.GridMap;

/**
 *
 * @author hungcuong
 */
public class BoxGrid3D implements GridCellPresentor, GridMapListener {

    private Material defaultMat;
    private Material selectedMat;
    GridCell gridCell;
    Geometry currentGeo;
    float size = 0.8f;
    private final BitmapText labelText;
    GridCellManager gridCellManager;
    Node gridNode;
    GridColorControl gridColorControl;

    public BoxGrid3D(GridCell gridCell, GridCellManager gridCellManager, BitmapFont font, Material defaultMat) {
        this.gridCell = gridCell;
        this.gridCellManager = gridCellManager;
        // Init materials
        this.defaultMat = defaultMat;
        //this.selectedMat = selectedMat;

        Box aBox = new Box(Vector3f.ZERO, size, size / 20, size);

        int r = gridCell.position.x;
        int c = gridCell.position.y;
        gridNode = new Node("GridNode " + r + "_" + c);
        currentGeo = new Geometry("gridBox" + r + "_" + c, aBox);

        currentGeo.setMaterial(defaultMat.clone());

        float xPos = r * size * 2.2f;
        float zPos = c * size * 2.2f;

        labelText = new BitmapText(font, false);
        labelText.setBox(new Rectangle(0, 0, 6, 3));
        labelText.setQueueBucket(Bucket.Transparent);
        labelText.setLocalTranslation(0, 0.4f, 0);
        labelText.setText(" ");
        //labelText.setText(" " + r + "_" + c + " : " + gridCell.cost);
        labelText.setSize(1f);
        labelText.scale(0.4f);
        labelText.setColor(ColorRGBA.Black);
        //labelText.setLocalTranslation(0, 0, 1f);
        labelText.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        gridColorControl = new GridColorControl(gridCell, gridCellManager, labelText);
        currentGeo.addControl(gridColorControl);
        gridNode.setLocalTranslation(xPos + 0.2f, 0, zPos + 0.2f);
        gridNode.attachChild(currentGeo);
        gridNode.attachChild(labelText);
    }

    Material getDefaultMat() {
        return defaultMat;
    }

    Material getSelectedMat() {
        return selectedMat;
    }

    public GridCell getGridCell() {
        return gridCell;
    }

    public void setGridCell(GridCell gridCell) {
        //throw new UnsupportedOperationException("Not supported yet.");
        this.gridCell = gridCell;
    }

    public void onGridCellChanged() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onGridChanged(GridCell gridCell) {
        if (gridCell == this.gridCell) {
            gridColorControl.setNeedRepaint(true);
        }
    }

    public void onMapChanged(GridMap map) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public Node getGridNode() {
        return gridNode;
    }

    public void setGridNode(Node gridNode) {
        this.gridNode = gridNode;
    }

    public Geometry getGeo() {
        return currentGeo;
    }

    void notifyChanged() {
        gridColorControl.setNeedRepaint(true);
    }
}
