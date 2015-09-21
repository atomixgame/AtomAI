/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.searching.pathfinding.astar.implementation2;

import sg.atom.ai.framework.space.grid.control.GridCellManager;
import sg.atom.ai.framework.space.grid.GridCell;
import sg.atom.ai.framework.space.grid.GridMap;
import java.awt.Point;
import java.util.ArrayList;
import sg.atom.ai.searching.pathfinding.PathFinder;

/**
 *
 * @author hungcuong
 */
public class HuristicAStar extends OneTailAStar implements PathFinder {

    double minCost;
    GridCellManager gridCellManager;

    public HuristicAStar() {
        super();
        stepSpeed = 20;

    }

    /**
     * calculates the waighted manhattan distance from a to b
     */
    public double cbDist(Point a, Point b, double low) {
        return low * (Math.abs(a.x - b.x) + Math.abs(a.y - b.y) - 1);
    }

    @Override
    public GridCell[] findPath(GridMap map) {
        gridCellManager = map.getGridCellManager();
        minCost = Double.MAX_VALUE;
        for (int i = 0; i < map.w; i++) {
            for (int j = 0; j < map.h; j++) {
                minCost = Math.min(map.gridCell[j][i].getCost(), minCost);
            }
        }
        //minCost=0.9;
        System.out.println("Cheepest Tile = " + minCost);
        return super.findPath(map);
    }

    public int step() {
        int tests = 0;
        boolean found = false;
        boolean growth = true;
        GridCell finish = gridCellManager.getFinishCell();
        Point end = finish.getPosition();
        ArrayList temp = (ArrayList) edge.clone();
        //find the most promesing edge cell
        double min = Double.MAX_VALUE;
        double score;
        //int best = -1;
        GridCell best = (GridCell) temp.get(temp.size() - 1);;
        GridCell now;
        for (int i = 0; i < temp.size(); i++) {
            now = (GridCell) temp.get(i);
            if (!done.contains(now)) {
                //score =now.getDistFromStart();

                score = gridCellManager.getDistFromStart(now);
                score += cbDist(now.getPosition(), end, minCost);
                if (score < min) {
                    min = score;
                    best = now;
                }
            }
        }
        now = best;
        //System.out.println(now.getPosition()+" Selected for expansion");
        edge.remove(now);
        done.add(now);
        GridCell next[] = map.getAdjacent(now);
        for (int i = 0; i < 4; i++) {
            if (next[i] != null) {
                if (next[i] == finish) {
                    found = true;
                }
                if (!next[i].isTotalBlock()) {
                    next[i].addToPathFromStart(gridCellManager.getDistFromStart(now));
                    tests++;
                    if (!edge.contains(next[i]) && !done.contains(next[i])) {
                        edge.add(next[i]);
                        growth = true;
                    }
                }
            }
            if (found) {
                return FOUND;
            }
        }
        map.notifyChanged();
        if (edge.size() == 0) {
            return NO_PATH;
        }

        //now process best.
        return NOT_FOUND;
    }
}
