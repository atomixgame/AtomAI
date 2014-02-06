package sg.atom.ai.testbed.searching.pathfinding.astar;

import sg.atom.ai.searching.pathfinding.space.grid.control.GridCellManager;
import sg.atom.ai.searching.pathfinding.space.grid.grid3d.Map3D;
import sg.atom.ai.searching.pathfinding.space.grid.GridCell;
import sg.atom.ai.searching.pathfinding.space.grid.grid3d.BoxGrid3D;
import sg.atom.ai.searching.pathfinding.space.grid.GridMap;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.LoopMode;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.debug.Arrow;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import sg.atom.ai.searching.pathfinding.astar.implementation2.HuristicAStar;
import sg.atom.ai.searching.pathfinding.PathFinder;

/**
 * Sample 7 - how to load an OgreXML model and play an animation, using
 * channels, a controller, and an AnimEventListener.
 */
public class TestAStar3D extends SimpleApplication
        implements AnimEventListener, ScreenController {

    Node player;
    private AnimChannel channel;
    private AnimControl control;

    public static void main(String[] args) {
        TestAStar3D app = new TestAStar3D();
        app.start();
    }
    private Nifty nifty;
    private Geometry mark;
    PathFinder finder;//new OneTailAStar();

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(30f);
        viewPort.setBackgroundColor(ColorRGBA.LightGray);
        initKeys();

        /**
         * Add a light source so we can see the model
         */
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
        rootNode.addLight(dl);

        /**
         * Load a model that contains animation
         */
        player = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        player.setLocalScale(0.5f);
        rootNode.attachChild(player);

        /**
         * Create a controller and channels.
         */
        control = player.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("stand");

        initMark();
        initNifty();
        setupCam();
        setupPathFindingMap();
        finder = new HuristicAStar();
    }

    public void setupPathFindingMap() {

        gridNode = new Map3D(new GridMap(), assetManager);
        gridNode.setLocalTranslation(0, -2, 0);
        //gridNode.addControl(new GridBoxControl(8, 8, defaultMat, selectedMat));
        rootNode.attachChild(gridNode);
    }

    public void setupCam() {
        cam.setLocation(new Vector3f(-20, 30f, 10));
        cam.lookAt(new Vector3f(10, 0, 10), Vector3f.UNIT_Y);
        // disable the fly cam
        flyCam.setEnabled(false);
        inputManager.setCursorVisible(true);
    }
    Map3D gridNode;

    /**
     * Use this listener to trigger something after an animation is done.
     */
    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (animName.equals("Walk")) {
            /**
             * After "walk", reset to "stand".
             */
            channel.setAnim("stand", 0.50f);
            channel.setLoopMode(LoopMode.DontLoop);
            channel.setSpeed(1f);
        }
    }

    /**
     * Use this listener to trigger something between two animations.
     */
    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        // unused
    }

    /**
     * Custom Keybindings: Mapping a named action to a key input.
     */
    private void initKeys() {
        inputManager.addMapping("Walk", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "Walk");
        inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(actionListener, "Shoot");
        inputManager.addMapping("ToogleCam", new KeyTrigger(KeyInput.KEY_T));
        inputManager.addListener(actionListener, "ToogleCam");
    }
    /**
     * Definining the named action that can be triggered by key inputs.
     */
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Walk") && !keyPressed) {
                if (!channel.getAnimationName().equals("Walk")) {
                    /**
                     * Play the "walk" animation!
                     */
                    channel.setAnim("Walk", 0.50f);
                    channel.setLoopMode(LoopMode.Loop);
                }
            } else if (name.equals("Shoot")) {
                if (keyPressed) {
                    checkShoot(cam, inputManager);
                }
            } else if (name.equals("ToogleCam")) {
                if (keyPressed) {
                    flyCam.setEnabled(!flyCam.isEnabled());
                }
            }
        }
    };

    void initNifty() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();

        nifty.fromXml("Interface/PathFindingUI.xml", "start", this);

        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);


    }

    GridCell checkShoot(Camera cam, InputManager inputManager) {

        Vector3f origin = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();

        Ray ray = new Ray(origin, direction);
        CollisionResults results = new CollisionResults();
        gridNode.collideWith(ray, results);


        if (results.size() > 0) {
            CollisionResult closest = results.getClosestCollision();
            mark.setLocalTranslation(closest.getContactPoint());

            Quaternion q = new Quaternion();
            q.lookAt(closest.getContactNormal(), Vector3f.UNIT_Y);
            mark.setLocalRotation(q);

            rootNode.attachChild(mark);

            // More
            Geometry aGeo = closest.getGeometry();

            for (BoxGrid3D aBox : gridNode.getBoxList()) {
                if (aGeo == aBox.getGeo()) {
                    gridNode.getMap().getGridCellManager().activeBlock(aBox.getGridCell());
                    System.out.println(aBox.getGridNode().getName());
                    return aBox.getGridCell();
                }
            }

        } else {
            gridNode.detachChild(mark);
        }
        return null;
    }

    protected void initMark() {
        Arrow arrow = new Arrow(Vector3f.UNIT_Z.mult(2f));
        arrow.setLineWidth(3);

        //Sphere sphere = new Sphere(30, 30, 0.2f);
        mark = new Geometry("BOOM!", arrow);
        //mark = new Geometry("BOOM!", sphere);
        Material mark_mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mark_mat.setColor("Color", ColorRGBA.Red);
        mark.setMaterial(mark_mat);
    }

    public void clickStartPoint() {
        System.out.print("GridCellManager.SET_START !");
        GridCellManager.setEditMode(GridCellManager.SET_START);
    }

    public void clickFinishPoint() {
        System.out.print("GridCellManager.SET_FINISH !");
        GridCellManager.setEditMode(GridCellManager.SET_FINISH);
    }

    public void clickNormalPoint() {
        System.out.print("GridCellManager.SET_BLOCKS!");
        GridCellManager.setEditMode(GridCellManager.SET_BLOCKS);
    }

    public void clickGo() {
        System.out.print("Find the path !");
        finder.findPath(gridNode.getMap());
    }

    public void clickClear() {
        System.out.print("Clear !");
        gridNode.getMap().getGridCellManager().clearAll();
    }

    public void bind(Nifty nifty, Screen screen) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
