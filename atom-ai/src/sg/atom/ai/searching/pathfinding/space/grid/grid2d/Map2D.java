/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.searching.pathfinding.space.grid.grid2d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JPanel;
import sg.atom.ai.searching.pathfinding.space.grid.GridCell;
import sg.atom.ai.searching.pathfinding.space.grid.GridMapListener;
import sg.atom.ai.searching.pathfinding.space.grid.GridMap;

/**
 *
 * @author hungcuong
 */
public class Map2D extends JPanel implements GridMapListener {

    transient Image buffer;
    GridMap map;

    public Map2D(GridMap map) {
        super();
        this.map = map;
        int h = map.h;
        int w = map.w;

        //{{INIT_CONTROLS
        setLayout(new GridLayout(w, h));
        setSize(insets().left + insets().right + 430, insets().top + insets().bottom + 270);
        //}}
        map.addGridMapListener(this);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                BoxGrid2D box = new BoxGrid2D(map.getGridCellAt(j, i), map.getGridCellManager());
                map.addGridMapListener(box);
                add(box);
            }
        }
    }

    public void paint(Graphics g) {
        if (buffer == null) {
            buffer = createImage(getBounds().width, getBounds().height);
        }
        Graphics bg = buffer.getGraphics();
        super.paint(bg);
        bg.setColor(Color.black);
        g.drawImage(buffer, 0, 0, null);
        g.drawRect(0, 0, getBounds().width - 1, getBounds().height - 1);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    public GridMap getMap() {
        return map;
    }

    public void onGridChanged(GridCell aCell) {
    }

    public void onMapChanged(GridMap aMap) {
        repaint();
    }
}
