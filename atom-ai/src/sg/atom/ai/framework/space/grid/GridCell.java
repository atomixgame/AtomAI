/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.framework.space.grid;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 *
 * @author hungcuong
 */
public class GridCell implements Serializable {

    public int x;
    public int y;
    public int height;
    public int width;
    public int status;
    public Point position;
    public boolean actived;
    // Cost constant
    public static final double NORMAL = 1, EASY = 0.3, TOUGH = 5, VERY_TOUGH = 10, BLOCK = Double.MAX_VALUE;
    public static boolean tidy = false;
    public static boolean showPath = true;
    public static boolean showDist = true;
    public double cost = 1.0;
    public boolean partOfPath = false;
    transient boolean used = false;
    public transient double distFromStart = -1;
    public transient double distFromFinish = -1;
    // private boolean totalBlock = false;
    // Travel and computing Status
    private boolean isStart = false;
    private boolean isFinish = false;

    public GridCell() {
        tidy = true;
    }

    /**
     * Constructer with option for making this cell impasable
     *
     * @param block Boolean, set to true if this cell can not be passed through
     */
    public GridCell(boolean block) {
        //this();
        setTotalBlock(block);
    }

    public void setPosition(Point p) {
        position = p;
    }

    public Point getPosition() {
        return position;
    }

    public void addToPathFromStart(double distSoFar) {
        used = true;

        if (distFromStart == -1) {
            distFromStart = distSoFar + cost;
            return;
        }
        if (distSoFar + cost < distFromStart) {
            distFromStart = distSoFar + cost;
        }
    }

    public void addToPathFromFinish(double distSoFar) {
        used = true;
        if (distFromFinish == -1) {
            distFromFinish = distSoFar + cost;
            return;
        }
        if (distSoFar + cost < distFromFinish) {
            distFromFinish = distSoFar + cost;
        }
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double c) {
        cost = c;
    }

    public boolean isTotalBlock() {
        return cost == BLOCK;
    }

    public void setTotalBlock(boolean flag) {
        if (flag) {
            cost = BLOCK;
        } else {
            cost = NORMAL;
        }
    }

    public boolean isUsed() {
        return used;
    }

    public void resetCell() {
        used = false;
        setPartOfPath(false);
        distFromStart = distFromFinish = -1;
    }

    public void clearCell() {
        setCost(NORMAL);
    }

    public static void setShowPath(boolean flag) {
        showPath = flag;
    }

    public static boolean isShowPath() {
        return showPath;
    }

    public boolean isPartOfPath() {
        return partOfPath;
    }

    public void setPartOfPath(boolean flag) {
        partOfPath = flag;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        if (!tidy) {

            //cells = new Vector();
            tidy = true;
        }

        ois.defaultReadObject();
        //cells.addElement(this);
        if (isStart) {
            //setStart(true);
        }
        if (isFinish) {
            //setFinish(true);
        }


    }
}
