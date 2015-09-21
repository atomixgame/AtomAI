/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.framework.space.grid.grid3d;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.input.controls.ActionListener;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.ArrayList;
import sg.atom.ai.framework.space.grid.GridCell;
import sg.atom.ai.framework.space.grid.GridMapListener;
import sg.atom.ai.framework.space.grid.GridMap;

/**
 *
 * @author hungcuong
 */
public class Map3D extends Node implements GridMapListener {

    GridMap map;
    private final AssetManager assetManager;
    private Geometry mark;
    ArrayList<BoxGrid3D> boxList = new ArrayList<BoxGrid3D>();

    public Map3D(GridMap map, AssetManager assetManager) {
        super("GridNode");
        this.assetManager = assetManager;
        this.map = map;
        int h = map.h;
        int w = map.w;

        // Get the materials
        Material defaultMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        defaultMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);  // activate transparency
        defaultMat.setColor("Color", ColorRGBA.White);
        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");

        map.addGridMapListener(this);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                BoxGrid3D box = new BoxGrid3D(map.getGridCellAt(j, i), map.getGridCellManager(), font, defaultMat);
                boxList.add(box);
                map.addGridMapListener(box);
                attachChild(box.getGridNode());
            }
        }


    }

    public void onGridChanged(GridCell gridCell) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onMapChanged(GridMap map) {
        for (BoxGrid3D box : boxList) {
            box.notifyChanged();
        }
    }

    public ArrayList<BoxGrid3D> getBoxList() {
        return boxList;
    }

    public void setBoxList(ArrayList<BoxGrid3D> boxList) {
        this.boxList = boxList;
    }

    public GridMap getMap() {
        return map;
    }

    public void setMap(GridMap map) {
        this.map = map;
    }
}
