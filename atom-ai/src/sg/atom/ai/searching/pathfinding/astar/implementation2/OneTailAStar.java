/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.searching.pathfinding.astar.implementation2;

import sg.atom.ai.searching.pathfinding.space.grid.control.GridCellManager;
import sg.atom.ai.searching.pathfinding.space.grid.GridCell;
import sg.atom.ai.searching.pathfinding.space.grid.GridMap;
import java.util.ArrayList;
import sg.atom.ai.searching.pathfinding.PathFinder;

/**
 *
 * @author hungcuong
 */
public class OneTailAStar extends java.lang.Object implements PathFinder, Runnable {

    public final int NO_PATH = -1, NOT_FOUND = 0, FOUND = 1;
    protected ArrayList edge;
    protected ArrayList done;
    protected GridMap map;
    int stepSpeed = 100;//higher is slower
    private int maxSteps = 1000;
    Thread loop;
    double distFromStart = 0;
    private boolean findFirst = false;
    GridCellManager gridCellManager;

    public GridCell[] findPath(GridMap map) {
        this.map = map;
        gridCellManager = map.getGridCellManager();
        gridCellManager.reset();

        edge = new ArrayList();
        done = new ArrayList();
        System.out.println("calculating route");
        if (GridCellManager.getStartCell() == null) {
            System.out.println("No start point set");
            return null;
        }
        if (GridCellManager.getFinishCell() == null) {
            System.out.println("No finish point set");
            return null;
        }
        System.out.println("Starting from " + map.getStartPosition());
        loop = new Thread(this);
        loop.start();
        return null;
    }

    public void run() {
        edge.add(GridCellManager.getStartCell());
        int pass = 0;
        boolean found = false;
        double start, diff;
        int state = NOT_FOUND;
        while (state == NOT_FOUND && pass < maxSteps) {
            pass++;
            start = System.currentTimeMillis();
            state = step();
            diff = System.currentTimeMillis() - start;
            try {
                loop.sleep(Math.max((long) (stepSpeed - diff), 0));
            } catch (InterruptedException e) {
            }
            // System.out.println(diff);
        }
        if (state == FOUND) {
            setPath(map);
        } else {
            System.out.println("No Path Found");
        }
    }

    public int step() {
        int tests = 0;
        boolean found = false;
        boolean growth = false;
        GridCell finish = GridCellManager.getFinishCell();
        ArrayList temp = (ArrayList) edge.clone();
        for (int i = 0; i < temp.size(); i++) {
            GridCell now = (GridCell) temp.get(i);
            GridCell next[] = map.getAdjacent(now);
            for (int j = 0; j < 4; j++) {
                if (next[j] != null) {
                    if (next[j] == finish) {
                        found = true;
                    }
                    next[j].addToPathFromStart(gridCellManager.getDistFromStart(now));
                    tests++;
                    if (!next[j].isTotalBlock() && !edge.contains(next[j])) {
                        edge.add(next[j]);
                        growth = true;
                    }
                }
            }
            if (found) {
                return FOUND;
            }
            done.add(now);

            //edge.removeElement(now);
        }
        map.notifyChanged();
        if (!growth) {
            return NO_PATH;
        }
        // System.out.println("Tests:"+tests+" Edge:"+edge.size()+" Done:"+done.size());
        return NOT_FOUND;
    }

    public void setPath(GridMap map) {
        System.out.println("Path Found");
        GridCell.setShowPath(true);
        boolean finished = false;
        GridCell next;
        GridCell now = GridCellManager.getFinishCell();
        GridCell stop = GridCellManager.getStartCell();
        while (!finished) {
            next = map.getLowestAdjacent(now);
            now = next;
            now.setPartOfPath(true);
            gridCellManager.notifyChanged(now);
            if (now == stop) {
                finished = true;
            }
            try {
                loop.sleep(stepSpeed);
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Done");


    }
}
