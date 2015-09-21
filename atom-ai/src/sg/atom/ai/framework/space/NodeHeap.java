package sg.atom.ai.framework.space;

import com.jme3.math.Vector3f;
import sg.atom.structure.collections.MinHeap;

/**
 * A NavigationHeap is a priority-ordered list facilitated by the STL heap
 * functions. This class is also used to hold the current path finding session
 * ID and the desired goal point for NavigationCells to query. Thanks to Amit J.
 * Patel for detailing the use of STL heaps in this way. It's much faster than a
 * linked list or multimap approach.
 *
 * Portions Copyright (C) Greg Snook, 2000
 *
 * @author TR
 *
 */
public class NodeHeap {

    private MinHeap nodes = new MinHeap();
    private int sessionID;
    private Vector3f goal;

    int getSessionID() {
        return sessionID;
    }

    Vector3f getGoal() {
        return goal;
    }

    public void initialize(int sessionID, Vector3f goal) {
        this.goal = goal;
        this.sessionID = sessionID;
        nodes.clear();
    }

    public void addCell(Cell pCell) {
        Node newNode = new Node(pCell, pCell.getTotalCost());
        nodes.add(newNode);
    }

    /**
     * Adjust a cell in the heap to reflect it's updated cost value. NOTE: Cells
     * may only sort up in the heap.
     */
    public void adjustCell(Cell pCell) {
        Node n = findNodeIterator(pCell);

        if (n != nodes.lastElement()) {
            // update the node data
            n.cell = pCell;
            n.cost = pCell.getTotalCost();

            nodes.sort();
        }
    }

    /**
     * @return true if the heap is not empty
     */
    public boolean isNotEmpty() {
        return !nodes.isEmpty();
    }

    /**
     * Pop the top off the heap and remove the best value for processing.
     */
    public Node getTop() {
        return (Node) nodes.deleteMin();
    }

    /**
     * Search the container for a given cell. May be slow, so don't do this
     * unless nessesary.
     */
    public Node findNodeIterator(Cell pCell) {
        for (Object n : nodes) {

            if (((Node) n).cell.equals(pCell)) {
                return ((Node) n);
            }
        }
        return (Node) nodes.lastElement();
    }
}
