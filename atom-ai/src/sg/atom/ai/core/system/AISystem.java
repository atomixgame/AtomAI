package sg.atom.ai.core.system;

import java.util.ArrayList;

/**
 *
 * @author hungcuong
 */
/**
 * This class is the core of all the activies in AI for the game It's abstracted
 * but functional and usable-ready.<br/> <p> <strong>Architecture:</strong><br/>
 * It has Modules that init, load, start, stop as Plugin<br/> It has a List of
 * Agents. Plugable : can be hard reference or soft reference though Entity
 * adapter<br/> It has a World as Data to share between Agent. Plugable : hard
 * or soft via WorldAdapter<br/>
 */
public class AISystem {

    ArrayList<AIModule> modules;
    ArrayList<AILayer> layers;
    
    public AISystem() {
    }

    void init() {
    }

    void start() {
    }

    void update() {
    }

    void eventHandle() {
    }

    void stop() {
    }
}
