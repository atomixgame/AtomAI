package sg.atom.ai.testbed.ailang.abl.actions;


import java.awt.Point;
import sg.atom.ai.testbed.ailang.abl.game.Chaser;
/**
 * Stops the chaser. 
 * 
 * @author Ben Weber 3-7-11
 */
public class Stop extends BaseAction {

	/**
	 * Stops the chaser.
	 */
	public void execute(Object[] args) {
		Chaser.getInstance().setChaserTrajectory(new Point(0,0));
	}
}
