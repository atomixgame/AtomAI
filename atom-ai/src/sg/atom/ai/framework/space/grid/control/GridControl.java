/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.framework.space.grid.control;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author hungcuong
 */
public class GridControl implements Control {

    private Node currentNode;
    private boolean enable;

    GridControl() {
    }

    public Control cloneForSpatial(Spatial spatial) {
        //throw new UnsupportedOperationException("Not supported yet.");
        return null;
    }

    public void setSpatial(Spatial spatial) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (!(spatial instanceof Node)) {
            throw new IllegalArgumentException("Only support for a Node");
        }
        currentNode = (Node) spatial;
        //makeGridFromBoxes();
        enable = true;
        

    }

    public void setEnabled(boolean enabled) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isEnabled() {
        return true;
    }
    float lastTime = 0, timePerStep = 0.5f;

    public void update(float tpf) {
        if (!enable) {
            return;
        }
        //System.out.println(tpf);
        if (lastTime < timePerStep) {
            lastTime += tpf;
            return;
        } else {
            lastTime = 0;
        }

        //System.out.println("Updated!");
/*
        for (int r = 0; r < row; r++) {
        for (int c = 0; c < col; c++) {
        int ranInt = FastMath.rand.nextInt(5);
        if (ranInt > 2) {
        gridArray[r][c].setMaterial(defaultMat);
        } else {
        gridArray[r][c].setMaterial(selectedMat);
        }
        }
        }
         */
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
}
