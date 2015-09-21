/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.framework.space.grid.control;

import sg.atom.ai.framework.space.grid.GridCell;

/**
 *
 * @author hungcuong
 */
public interface GridCellPresentor {

    public void setGridCell(GridCell gridCell);

    public GridCell getGridCell();

    public void onGridCellChanged();
}
