package sg.atom.ai.testbed.searching.pathfinding.astar;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import sg.atom.ai.searching.pathfinding.core.world.Mover;

/**
 * An object representing the entity in the game that is going to moving along
 * the path. This allows us to pass around entity/state information to determine
 * whether a particular tile is blocked, or how much cost to apply on a
 * particular tile.
 *
 * For instance, a Mover might represent a tank or plane on a game map. Passing
 * round this entity allows us to determine whether rough ground on a map should
 * effect the unit's cost for moving through the tile.
 */
public class Unit implements Mover {

    /**
     * The unit ID moving
     */
    private int type;
    /**
     * Unit model in the world
     */
    private Spatial model;
    private static final float z = 0.001f;

    /**
     * Create a new mover to be used with path finder
     *
     * @param type The ID of the created unit
     * @param x coordinate of the created unit
     * @param y coordinate of the created unit
     * @param model of the created unit
     */
    public Unit(int type, int x, int y, Spatial model) {
        this.type = type;
        this.model = model;
        this.model.setLocalTranslation(x, y, z);
    }

    /**
     * Create a new mover to be used with path finder
     *
     * @param name
     * @param x
     * @param y
     * @param type
     * @param display
     * @param texture
     */
    public Unit(int x, int y, int type, Texture texture) {
        this.type = type;
        this.model = new Geometry("Unit" + type + "_x" + x + ":" + y, new Quad(1, 1));
        // Texture the object

        this.model.setLocalTranslation(x, y, z);
    }

    /**
     * Get the ID of the unit moving
     *
     * @return The ID of the unit moving
     */
    public int getType() {
        return type;
    }

    /**
     * @return the model
     */
    public Spatial getModel() {
        return model;
    }

    /**
     * @return the model LocalTranslation
     */
    public Vector3f getModelLocation() {
        return model.getLocalTranslation();
    }

    /**
     * Set model LocalTranslation with y at 0.1f
     *
     * @param float x coordinate to move to
     * @param float y coordinate to move to
     */
    public void setModelLocation(float x, float y) {
        model.setLocalTranslation(x, y, z);
    }

    /**
     * Set model LocalTranslation to specified vector
     *
     * @param Vector3f location where to move the model
     */
    public void setModelLocation(Vector3f location) {
        model.setLocalTranslation(location);
    }
}
