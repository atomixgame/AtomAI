/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.framework.space.grid.control;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import sg.atom.ai.framework.space.grid.GridCell;

/**
 *
 * @author hungcuong
 */
public class GridColorControl implements Control {

    private boolean enable;
    Geometry currentGeo;
    GridCell gridCell;
    GridCellManager gridCellManager;
    boolean needRepaint = false;
    BitmapText text;

    public GridColorControl(GridCell gridCell, GridCellManager gridCellManager, BitmapText text) {
        this.gridCell = gridCell;
        this.gridCellManager = gridCellManager;
        this.text = text;
    }

    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    public void setSpatial(Spatial spatial) {
        this.currentGeo = (Geometry) spatial;
        needRepaint = true;

    }

    public void setEnabled(boolean enabled) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isEnabled() {
        return true;
    }

    public void update(float tpf) {
        if (needRepaint) {
            repaint();
            needRepaint = false;
        }
    }

    void repaint() {
        Material mat = currentGeo.getMaterial();
        if (gridCell.cost != gridCell.NORMAL) {
            if (gridCell.cost == gridCell.EASY) {
                mat.setColor("Color", ColorRGBA.Orange);
            }
            if (gridCell.cost == gridCell.BLOCK) {
                mat.setColor("Color", ColorRGBA.Black);
            }
            if (gridCell.cost == gridCell.TOUGH) {
                mat.setColor("Color", ColorRGBA.LightGray);
            }
            if (gridCell.cost == gridCell.VERY_TOUGH) {
                mat.setColor("Color", ColorRGBA.Gray);
            }
        } else {
            mat.setColor("Color", ColorRGBA.White);
        }
        if (gridCell.showPath && gridCell.partOfPath) {
            mat.setColor("Color", ColorRGBA.Yellow);
        }
        if (gridCellManager.isStart(gridCell)) {
            mat.setColor("Color", ColorRGBA.Green);
        }
        if (gridCellManager.isFinish(gridCell)) {
            mat.setColor("Color", ColorRGBA.Red);
        }
        //g.fillRect(0, 0, size.width, size.height);


        if (gridCell.showDist && gridCellManager.getDistFromStart(gridCell) > 0) {
            text.setText("" + gridCellManager.getDistFromStart(gridCell));
        }
    }

    public void render(RenderManager rm, ViewPort vp) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void write(JmeExporter ex) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void read(JmeImporter im) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isNeedRepaint() {
        return needRepaint;
    }

    public void setNeedRepaint(boolean needRepaint) {
        this.needRepaint = needRepaint;
    }
}
