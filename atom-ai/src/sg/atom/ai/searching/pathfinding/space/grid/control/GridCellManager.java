/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.searching.pathfinding.space.grid.control;

import java.util.Vector;
import sg.atom.ai.searching.pathfinding.space.grid.GridCell;
import sg.atom.ai.searching.pathfinding.space.grid.GridMap;

/**
 *
 * @author hungcuong
 */
public class GridCellManager {

    GridMap parentMap;
    private static GridCell startCell;
    private static GridCell finishCell;
    private static Vector<GridCell> cells = new Vector<GridCell>();
    public static boolean tidy = false;
    private static boolean showPath = true;
    private static boolean showDist = true;
    public static final int SET_BLOCKS = 0, SET_START = 1, SET_FINISH = 2;
    static int editMode = SET_BLOCKS;
    static double newBlockStrength = GridCell.BLOCK;

    public GridCellManager(GridMap parentMap) {
        this.parentMap = parentMap;
    }

    public static GridCell getFinishCell() {
        return finishCell;
    }

    public static GridCell getStartCell() {
        return startCell;
    }

    public boolean isStart(GridCell aCell) {
        return startCell == aCell;
    }

    public void setStart(GridCell aCell) {
        GridCell temp = startCell;
        startCell = aCell;
        notifyChanged(temp);
        // Tell display to update
        notifyChanged(aCell);
    }

    public boolean isFinish(GridCell aCell) {
        return finishCell == aCell;
    }

    public void setFinish(GridCell aCell) {
        GridCell temp = finishCell;
        finishCell = aCell;
        notifyChanged(temp);
        // Tell display to update
        notifyChanged(aCell);

    }

    public static void reset() {
        for (int i = 0; i < cells.size(); i++) {
            ((GridCell) cells.elementAt(i)).resetCell();
        }
    }

    public void clearAll() {
        for (int i = 0; i < cells.size(); i++) {
            ((GridCell) cells.elementAt(i)).clearCell();
        }
        notifyChanged(parentMap);
    }

    public double getDistFromStart(GridCell aCell) {
        if (startCell == aCell) {
            return 0;
        }
        if (aCell.isTotalBlock()) {
            return -1;
        }
        return aCell.distFromStart;
    }

    private void notifyChanged(GridMap parentMap) {
        parentMap.notifyChanged();
    }

    public void notifyChanged(GridCell aCell) {
        parentMap.notifyChanged(aCell);
    }

    public static void setEditMode(int mode) {
        editMode = mode;
        System.out.println("Mode set");
    }

    public static void setNewBlockStrength(double s) {
        if (s < 0) {
            newBlockStrength = GridCell.BLOCK;
        } else {
            newBlockStrength = s;
        }
    }

    public void setCost(GridCell gridCell) {
        if (gridCell.cost != GridCellManager.newBlockStrength) {
            gridCell.cost = GridCellManager.newBlockStrength;
        } else {
            gridCell.cost = GridCell.NORMAL;
        }
        System.out.print(" Set Cost :" + gridCell.position.x + " " + gridCell.position.y + " = " + gridCell.cost);
        notifyChanged(gridCell);
    }

    public void activeBlock(GridCell gridCell) {
        gridCell.setShowPath(false);
        switch (GridCellManager.editMode) {
            case (GridCellManager.SET_BLOCKS):

                setCost(gridCell);
                break;
            case (GridCellManager.SET_START):
                System.out.print(" Set Start :" + gridCell.position.x + " " + gridCell.position.y);
                setStart(gridCell);

                break;
            case (GridCellManager.SET_FINISH):
                System.out.print(" Set Finish :" + gridCell.position.x + " " + gridCell.position.y);
                setFinish(gridCell);
                break;
        }
    }

    public void add(GridCell gridCell) {
        cells.add(gridCell);
    }
}
