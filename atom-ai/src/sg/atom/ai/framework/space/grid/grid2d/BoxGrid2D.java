/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.framework.space.grid.grid2d;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import sg.atom.ai.framework.space.grid.GridCell;
import sg.atom.ai.framework.space.grid.control.GridCellManager;
import sg.atom.ai.framework.space.grid.control.GridCellPresentor;
import sg.atom.ai.framework.space.grid.GridMapListener;
import sg.atom.ai.framework.space.grid.GridMap;

/**
 *
 * @author hungcuong
 */
public class BoxGrid2D extends java.awt.Component implements GridCellPresentor, GridMapListener {

    GridCell gridCell;
    GridCellManager gridCellManager;

    public BoxGrid2D(GridCell gridCell, GridCellManager gridCellManager) {
        this.gridCell = gridCell;
        this.gridCellManager = gridCellManager;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
    }

    public void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == e.MOUSE_CLICKED) {
            gridCellManager.activeBlock(gridCell);
        }

    }

    public Dimension getPreferredSize() {
        return new Dimension(10, 10);
    }

    public void paint(Graphics g) {
        Dimension size = getSize();
        g.setColor(Color.white);
        if (gridCell.cost != gridCell.NORMAL) {
            if (gridCell.cost == gridCell.EASY) {
                g.setColor(Color.orange);
            }
            if (gridCell.cost == gridCell.BLOCK) {
                g.setColor(Color.black);
            }
            if (gridCell.cost == gridCell.TOUGH) {
                g.setColor(Color.lightGray);
            }
            if (gridCell.cost == gridCell.VERY_TOUGH) {
                g.setColor(Color.gray);
            }
        }
        if (gridCell.showPath && gridCell.partOfPath) {
            g.setColor(Color.yellow);
        }
        if (gridCellManager.isStart(gridCell)) {
            g.setColor(Color.green);
        }
        if (gridCellManager.isFinish(gridCell)) {
            g.setColor(Color.red);
        }
        g.fillRect(0, 0, size.width, size.height);


        g.setColor(Color.black);
        if (gridCell.showDist && gridCellManager.getDistFromStart(gridCell) > 0) {
            g.drawString("" + gridCellManager.getDistFromStart(gridCell), 1, (int) (size.height * 0.75));
        }
        g.drawRect(0, 0, size.width - 1, size.height - 1);

    }

    public void setGridCell(GridCell gridCell) {
        //throw new UnsupportedOperationException("Not supported yet.");
        this.gridCell = gridCell;
    }

    public void onGridCellChanged() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public GridCell getGridCell() {
        return gridCell;
    }

    public void onGridChanged(GridCell gridCell) {
        //throw new UnsupportedOperationException("Not supported yet.");
        if (gridCell == this.gridCell) {
            repaint();
        }
    }

    public void onMapChanged(GridMap map) {
        repaint();
    }
}
