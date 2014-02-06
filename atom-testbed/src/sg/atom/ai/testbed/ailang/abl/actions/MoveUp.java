package sg.atom.ai.testbed.ailang.abl.actions;


import java.awt.Point;
import sg.atom.ai.testbed.ailang.abl.game.Chaser;
/**
 * Sets the trajectory of the chaser. 
 * 
 * @author Ben Weber 3-7-11
 */
public class MoveUp extends BaseAction {

	/**
	 * Sets the trajectory of the player to move up.
	 */
	public void execute(Object[] args) {
		Chaser.getInstance().setChaserTrajectory(new Point(0, -Chaser.ChaserSpeed));
	}
}
