package sg.atom.ai.testbed.ailang.abl.sensors;

import abl.runtime.BehavingEntity;
import sg.atom.ai.testbed.ailang.abl.game.Chaser;
import sg.atom.ai.testbed.ailang.abl.wmes.PlayerWME;
/**
 * Adds a PlayerWME object to working memory when sense in invoked.
 * 
 * @author Ben Weber 3-7-11
 */
public class PlayerSensor extends SerialSensor {

	/**
	 * Adds a Player WME to working memory of the agent and deletes previous player WMEs in memory.
	 */
	protected void sense() {

		BehavingEntity.getBehavingEntity().deleteAllWMEClass("PlayerWME");
		BehavingEntity.getBehavingEntity().addWME(
				new PlayerWME(Chaser.getInstance().getPlayerLocation(), Chaser.getInstance().getPlayerTrajectory()));
	}
}
