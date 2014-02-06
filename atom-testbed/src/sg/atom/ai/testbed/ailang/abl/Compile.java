package sg.atom.ai.testbed.ailang.abl;

import abl.compiler.Abl;
/**
 * Utility class for running the ABL compiler.
 * 
 * Note: if the debug mode is enabled, the generated source code will include 
 * additional code for displaying a debug GUI.
 * 
 * Note: ANT can also be used for performing this task.
 * 
 * @author Ben Weber 3-7-11
 */
public class Compile {
	
	/** enable debug mode? */
	private static boolean debugMode = false;

	/**
	 * Runs the ABL compiler. 
	 */
	public static void main(String[] args) {

		if (debugMode) {
			Abl.main(new String[] { "-g2", "-d", "src", "src/sg/atom/ai/testbed/ailang/abl/agents/ChaserAgent.abl" });
		}
		else {
			Abl.main(new String[] { "-d", "src", "src/sg/atom/ai/testbed/ailang/abl/agents/ChaserAgent.abl" });
		}
	}
}
