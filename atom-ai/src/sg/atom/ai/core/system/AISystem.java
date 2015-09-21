package sg.atom.ai.core.system;

import java.util.ArrayList;

/**
 * This class is the core of all the activies in AI for the game It's abstracted
 * but functional and usable-ready.
 *
 * <p> <strong>Architecture:</strong>
 *
 * <p>It has Modules that init, load, start, stop as Plugin, Service. This drive
 * by DI enable different implementation of algorimth adapt to the big picture.
 *
 * <p> It has a List of Agents. Plugable : can be hard reference or soft
 * reference though Entity adapter and slot. Relationship between agents are
 * managed via Fiber as in Actor framework.
 *
 * <p>It has a World as Data to share between Agent. Plugable : hard or soft via
 * WorldAdapter.
 *
 * <p> A Load-balance architecture. It has a sotiphicated scheduler for Agents
 * and other runnable.
 *
 *
 * @author hungcuong
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
