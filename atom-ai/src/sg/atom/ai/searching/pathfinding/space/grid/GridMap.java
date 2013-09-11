/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.searching.pathfinding.space.grid;

import sg.atom.ai.searching.pathfinding.space.grid.control.GridCellManager;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 *
 * @author hungcuong
 */
public class GridMap {

    public int w = 20;
    public int h = 20;
    public GridCell gridCell[][] = new GridCell[w][h];
    public GridCellManager gridCellManager;

    public GridMap() {
        this.gridCellManager = new GridCellManager(this);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                gridCell[j][i] = new GridCell();
                gridCell[j][i].setPosition(new Point(j, i));
                gridCellManager.add(gridCell[j][i]);
            }
        }
    }

    public Point getStartPosition() {
        GridCell start = GridCellManager.getStartCell();
        return start.getPosition();
    }

    public GridCell[] getAdjacent(GridCell g) {
        GridCell next[] = new GridCell[4];
        Point p = g.getPosition();
        if (p.y != 0) {
            next[0] = gridCell[p.x][p.y - 1];
        }
        if (p.x != w - 1) {
            next[1] = gridCell[p.x + 1][p.y];
        }
        if (p.y != h - 1) {
            next[2] = gridCell[p.x][p.y + 1];
        }
        if (p.x != 0) {
            next[3] = gridCell[p.x - 1][p.y];
        }
        return next;
    }

    public GridCell getLowestAdjacent(GridCell g) {
        GridCell next[] = getAdjacent(g);
        GridCell small = next[0];
        double dist = Double.MAX_VALUE;
        for (int i = 0; i < 4; i++) {
            if (next[i] != null) {
                double nextDist = gridCellManager.getDistFromStart(next[i]);
                if (nextDist < dist && nextDist >= 0) {
                    small = next[i];
                    dist = gridCellManager.getDistFromStart(next[i]);
                }
            }
        }
        return small;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        //GridCell.tidy = false;
        //ois.defaultReadObject();
        //GridCell.setShowPath(false);
    }
    //{{REGISTER_LISTENERS
    //};
    //{{DECLARE_CONTROLS
    //}}

    public GridCell getGridCellAt(int j, int i) {
        return gridCell[j][i];
    }

    public GridCell[][] getGridCell() {
        return gridCell;
    }

    public GridCellManager getGridCellManager() {
        return gridCellManager;
    }

    public void notifyChanged(GridCell aCell) {
        for (GridMapListener listener : getGridMapListener()) {
            listener.onGridChanged(aCell);
            //listener.onMapChanged(this);
        }
    }

    public void notifyChanged() {
        for (GridMapListener listener : getGridMapListener()) {
            listener.onMapChanged(this);
        }
    }
    ArrayList<GridMapListener> listenerList = new ArrayList<GridMapListener>();

    public ArrayList<GridMapListener> getGridMapListener() {
        return this.listenerList;
    }

    public void addGridMapListener(GridMapListener aListener) {
        listenerList.add(aListener);

    }
}
