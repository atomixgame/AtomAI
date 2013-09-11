package sg.atom.testbed.pathfinding.astar;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import java.util.HashMap;
import sg.atom.ai.searching.pathfinding.astar.implementation1.AStarHeuristic;
import sg.atom.ai.searching.pathfinding.astar.implementation1.AStarPathFinder;
import sg.atom.ai.searching.pathfinding.core.algorithm.PathFinder;
import sg.atom.ai.searching.pathfinding.core.path.Path;
import sg.atom.ai.searching.pathfinding.core.world.Mover;
import sg.atom.ai.searching.pathfinding.core.world.WorldMap;

/**
 * Example use of A* pathfinding. The A* used in this example is based on advice
 * from website http://www.policyalmanac.org/games/aStarTutorial.htm and related
 * pages. It uses a Binary Heap for maintaining an Open List. Faster and more
 * accurate than Example1.<br>
 *
 * @author Mindgamer
 */
public class PathfindingTest extends SimpleApplication {

    private Camera cam;
    private WorldMap map = new GameMap();
    // The map on which the units will move
    private Texture[] tileTextures = new Texture[6];
    // The list of tile images to render the map
    private HashMap<Spatial, Mover> units = new HashMap<Spatial, Mover>();
    // All the units on the map
    private PathFinder finder;
    // The path finder we'll use to search our map
    private Path path;
    // The last path found for the current unit
    private Spatial pickObject;
    // Picked scene element (Quad - terrain tile or vehicle)
    private Spatial lastPickObject;
    // The scene element to which we got our last path (for avoiding multiple searches)
    private Mover pickedUnit;
    // Picked vehicle Java object (Unit)
    private boolean pickIsVehicle;
    // Identifies if we clicked on a unit this update
    private boolean mouseIsPressed;
    // So that we sould not have to call "MouseInput.get().isButtonDown(0)" multiple times
    private boolean mousePressHandled;
    // Used for avoiding handling one mousepress multiple times
    private boolean monitorPathfinder;
    private Geometry pathTile;
    // Tile used for drawing the path 
    private Geometry markTile;

    /**
     * Entry point to our simple test game
     *
     * @param argv The arguments passed into the game
     */
    public static void main(String[] args) {
        PathfindingTest app = new PathfindingTest();

        /* Create the game */

        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        app.start();
    }
    private CollisionResults pickResults;

    @Override
    public void simpleInitApp() {
        inputManager.setCursorVisible(true);
        /* Setup our camera in good position */
        cam.setLocation(new Vector3f(map.getWidth() / 2, (map.getHeight() / 2) - 0.5f, 31.0f));
        cam.update();

        /* Get textures */
        tileTextures[WorldMap.TERRAIN_TREES] = assetManager.loadTexture("com/jmedemos/astar_pathfinder/img/trees.png");
        tileTextures[WorldMap.TERRAIN_GRASS] = assetManager.loadTexture("com/jmedemos/astar_pathfinder/img/grass.png");
        tileTextures[WorldMap.TERRAIN_WATER] = assetManager.loadTexture("com/jmedemos/astar_pathfinder/img/water.png");
        tileTextures[WorldMap.TANK] = assetManager.loadTexture("com/jmedemos/astar_pathfinder/img/tank.png");
        tileTextures[WorldMap.PLANE] = assetManager.loadTexture("com/jmedemos/astar_pathfinder/img/plane.png");
        tileTextures[WorldMap.BOAT] = assetManager.loadTexture("com/jmedemos/astar_pathfinder/img/boat.png");


        /* Create our map */
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                // Add tiles

                Quad quad = new Quad(1, 1);
                Geometry unit = new Geometry("Tile " + x + ":" + y, quad);
                //Texture ts = assetManager.loadTexture("")
                Material mat = new Material(assetManager, "Common/MatDefs/Misc/ColoredTextured.j3md");
                mat.setTexture("ColorMap", tileTextures[map.getTerrain(x, y)]);
                unit.setMaterial(mat);
                unit.setLocalTranslation(x, y, 0f);
                rootNode.attachChild(unit);
            }
        }


        /* Pathfinding */
        finder = new AStarPathFinder(
                map,
                new AStarHeuristic());
        pathTile = new Geometry("Tile", new Quad(0.5f, 0.5f));

        markTile = new Geometry("Tile", new Quad(0.5f, 0.5f));
        //markTile.setSolidColor(ColorRGBA.white);
        monitorPathfinder = true;


        /* 
         * Add our units. Units are added to the HashMap with the Spatial as a key to
         * allow identifying the Unit by the spatial in picking operation later.
         */
        Mover temp = new Unit(15, 15, WorldMap.TANK, tileTextures[WorldMap.TANK]);
        units.put(temp.getModel(), temp);
        rootNode.attachChild(temp.getModel());
        temp = new Unit(2, 7, WorldMap.BOAT, tileTextures[WorldMap.BOAT]);
        units.put(temp.getModel(), temp);
        rootNode.attachChild(temp.getModel());
        temp = new Unit(20, 25, WorldMap.PLANE, tileTextures[WorldMap.PLANE]);
        units.put(temp.getModel(), temp);
        rootNode.attachChild(temp.getModel());
        pickedUnit = null;		// Currently nothing is selected
    }

    @Override
    public void simpleUpdate(float tpf) {

        /* Find where mouse is now */
        Vector2f screenPos = new Vector2f(inputManager.getCursorPosition());
        Vector3f worldCoords = cam.getWorldCoordinates(screenPos, 0);			// Get the world location of that X,Y value
        Vector3f worldCoords2 = cam.getWorldCoordinates(screenPos, 1);
        Ray mouseRay = new Ray(worldCoords, // Create a ray starting from the camera & going 
                worldCoords2.subtractLocal(worldCoords).normalizeLocal());			//		in the direction of the mouse's location
        mouseRay.collideWith(rootNode, pickResults);

        // Check if there is any object under the mouse
        for (int i = 0; i < pickResults.size(); i++) {
            pickObject = (Spatial) pickResults.getClosestCollision().getGeometry();
            if (units.containsKey(pickObject)) {
                pickIsVehicle = true;
                break;
            } else {
                pickIsVehicle = false;
            }
        }

        /* Determine what we must do based on what the mouse is doing */
        mouseIsPressed = true;
        if (mouseIsPressed && !mousePressHandled) {						// Mouse is pressed down and we have not handled this yet
            mousePressHandled = true;									// Note that we have handled this mouse press
            if (pickIsVehicle) {											// If we are clicking on a unit, pick that unit
                pickedUnit = units.get(pickObject);
            } else if (pickedUnit != null && path != null) { 				// Otherwise, if some unit is already selected and there was a path found, move to new location
                pickedUnit.setModelLocation(pickObject.getLocalTranslation().getX(), pickObject.getLocalTranslation().getY());
            }
            path = null;
        } else if (!mouseIsPressed) { 									// Mouse is not pressed
            mousePressHandled = false;									// Note that mouse is no longer pressed
            if (pickedUnit != null) {									// If some object is selected, trace its path
                if (pickObject != lastPickObject && // If we are at a new location and there is
                        !units.containsKey(pickObject)) {				// 		no other vehicle here, search for a new path
                    lastPickObject = pickObject;
                    if (monitorPathfinder) {
                        map.clearVisited();			// Clear which tiles have been visited by pathfinder previously
                    }
                    path = finder.findPath(
                            pickedUnit,
                            (int) pickedUnit.getModelLocation().getX(),
                            (int) pickedUnit.getModelLocation().getY(),
                            (int) pickObject.getLocalTranslation().getX(),
                            (int) pickObject.getLocalTranslation().getY());
                }
            }
        }
        /*
         if (MouseInput.get().isButtonDown(1)) {
         pickedUnit = null;		// If right mouse button pressed, deselect unit
         path = null;			// Clear path
         }
         */

    }
    /*
     public void render(float tpf) {

     // Draw unit selection
     if (pickedUnit != null) {
     }

     // Mark tiles visited by path-finder
     if (!units.containsKey(pickObject) && path != null && monitorPathfinder) {
     for (int y = 0; y < map.getHeight(); y++) {
     for (int x = 0; x < map.getWidth(); x++) {
     if (map.isVisited(x, y)) {
     markTile.setLocalTranslation(x, y, 0.00105f);
     }
     }
     }
     }

     // Draw path
     if (!units.containsKey(pickObject) && path != null) {
     Step tempStep;
     for (int i = 1; i < path.getLength(); i++) {
     tempStep = path.getStep(i);
     pathTile.setLocalTranslation(tempStep.getX(), tempStep.getY(), 0.0011f);
     }
     tempStep = null;
     }

     }
     */
}
