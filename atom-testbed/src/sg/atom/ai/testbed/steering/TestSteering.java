package sg.atom.testbed.steering;



import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.RawInputListener;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.input.event.JoyAxisEvent;
import com.jme3.input.event.JoyButtonEvent;
import com.jme3.input.event.KeyInputEvent;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.input.event.MouseMotionEvent;
import com.jme3.input.event.TouchEvent;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Grid;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.terrain.geomipmap.TerrainQuad;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.EffectBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.util.ArrayList;
import jme3tools.navmesh.NavMesh;
import jme3tools.navmesh.NavMeshPathfinder;
import jme3tools.navmesh.Path;
import sg.atom.ai.movement.steering.common.SteerManager;
import sg.atom.ai.framework.control.SimpleAI;

public class TestSteering extends SimpleApplication
        implements ActionListener, ScreenController {

    private BulletAppState bulletAppState;
    private TerrainQuad terrain;
    private NavMesh navmesh;
    public static TestSteering instance;
    private NavMeshPathfinder nmp;
    ArrayList<Spatial> array = new ArrayList();
    CharacterControl character;
    Spatial model;
    private Vector3f pos1;
    private Vector3f pos2;
    private Spatial g1;
    private Spatial g2;
    private Vector3f goal;
    private Spatial goal1;
    SteerManager steerManager;
    private Geometry groundGeo;
    private Nifty nifty;

    public static void main(String[] args) {
        TestSteering app = new TestSteering();
        instance = app;
        app.setSettings(new AppSettings(true));
        app.settings.setHeight(600);
        app.settings.setWidth(800);

        app.setShowSettings(false);
        app.start();
        app.setPauseOnLostFocus(false);
    }

    public void simpleInitApp() {
        this.bulletAppState = new BulletAppState();
        this.bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        this.stateManager.attach(this.bulletAppState);

        setupBasicKeys();
        setupCamera();
        createTerrain();
        //createGrid(40);
        steerManager = new SteerManager(this);
        steerManager.createNodes();
        steerManager.createObstacles(3000);
        steerManager.attachNodes();
        steerManager.createMouse();
        steerManager.createSeekTest();
        steerManager.createFleeTest();
        initCrossHairs();
        initMouseTarget();
        //initNiftyGui();
        //createCharacter();
        createLight();
    }

    public void initNiftyGui() {
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                inputManager,
                audioRenderer,
                guiViewPort);
        nifty = niftyDisplay.getNifty();
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        nifty.addScreen("start", makeScreen(nifty));
        nifty.gotoScreen("start");

        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);
    }

    public Screen makeScreen(final Nifty nifty) {

        // create a screen
        Screen screen = new ScreenBuilder("start") {
            {
                controller(TestSteering.this);

                layer(new LayerBuilder("layer") {
                    {
                        childLayoutHorizontal();

                        panel(new PanelBuilder() {
                            {
                                id("panel");
                                childLayoutCenter();
                                height("400px");
                                width("20%");
                                alignCenter();
                                valignCenter();
                                padding("10px");
                                backgroundColor("#460f");
                                visibleToMouse();
                                onStartScreenEffect(new EffectBuilder("move") {
                                    {
                                        effectParameter("mode", "in");
                                        effectParameter("direction", "top");
                                        length(300);
                                        startDelay(0);
                                        inherit(true);
                                    }
                                });

                                onEndScreenEffect(new EffectBuilder("move") {
                                    {
                                        effectParameter("mode", "out");
                                        effectParameter("direction", "bottom");
                                        length(300);
                                        startDelay(0);
                                        inherit(true);
                                    }
                                });



                                panel(new PanelBuilder() {
                                    {
                                        padding("4px,4px,4px,4px");
                                        childLayoutVertical();
                                        alignCenter();
                                        valignCenter();
                                        width("100%");
                                        height("380px");
                                        backgroundColor("#111f");

                                        text(new TextBuilder() {
                                            {
                                                text("AI Test bed");
                                                font("aurulent-sans-16.fnt");
                                                color("#ffff");
                                                width("*");
                                                alignCenter();
                                                valignCenter();
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });


            }
        }.build(nifty);

        return (screen);
    }

    @Override
    public void bind(final Nifty nifty, final Screen screen) {
    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }

    private void createTerrain() {
        Node terrainScene = (Node) this.assetManager.loadModel("Scenes/cena.j3o");

        this.terrain = ((TerrainQuad) terrainScene.getChild("terrain-cena"));
        Geometry navm = (Geometry) terrainScene.getChild("NavMesh");

        Material m = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.getAdditionalRenderState().setWireframe(true);
        m.setColor("Color", ColorRGBA.Green);

        Geometry g = new Geometry("lixo", navm.getMesh());
        g.setMaterial(m);
        g.setCullHint(Spatial.CullHint.Never);

        this.navmesh = new NavMesh();
        this.navmesh.loadFromMesh(navm.getMesh());

        this.rootNode.attachChild(terrainScene);
        this.rootNode.attachChild(g);

        RigidBodyControl terrainPhysicsNode = new RigidBodyControl(CollisionShapeFactory.createMeshShape(this.terrain), 0.0F);
        this.terrain.addControl(terrainPhysicsNode);
        getPhysicsSpace().add(terrainPhysicsNode);
    }

    private void createGrid(int num) {
        Grid grid = new Grid(num * 2 + 1, num * 2 + 1, 1);
        Geometry gridGeo = new Geometry("WhitePlane", grid);

        Box boxshape3 = new Box(new Vector3f(0, 0, 0), num, 0.1f, num);
        groundGeo = new Geometry("translucent cube", boxshape3);

        Material whiteMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        groundGeo.setMaterial(whiteMat);
        whiteMat.setColor("Color", new ColorRGBA(1f, 0.9f, 0.7f, 1));
        Material colorMat = whiteMat.clone();
        colorMat.setColor("Color", ColorRGBA.Gray);
        colorMat.getAdditionalRenderState().setWireframe(true);
        gridGeo.setMaterial(colorMat);
        gridGeo.setLocalTranslation(-(float) num, 0.2f, -(float) num);
        rootNode.attachChild(groundGeo);
        rootNode.attachChild(gridGeo);


    }

    private void createFloor() {
    }

    public Spatial createSphereAtPoint(Vector3f point, ColorRGBA color) {
        Geometry g = new Geometry(point.toString(), new Sphere(16, 16, 0.5F));
        g.setCullHint(Spatial.CullHint.Never);
        Material m = new Material(this.assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", color);
        g.setMaterial(m);
        g.setLocalTranslation(point);
        this.rootNode.attachChild(g);
        return g;
    }

    private Path navmesh(Vector3f init, Vector3f end) {
        for (Spatial s : this.array) {
            s.removeFromParent();
        }

        Path path = new Path();
        boolean buildNavigationPath = this.navmesh.buildNavigationPath(path, this.navmesh.findClosestCell(init), init, this.navmesh.findClosestCell(end), end, 0.4F);

        if (buildNavigationPath) {
            for (Path.Waypoint p : path.getWaypoints()) {
                Vector3f v = p.getPosition();
                v.y = 0.0F;
                p.setPosition(v);
                this.array.add(createSphereAtPoint(v, ColorRGBA.Pink));
            }
            this.array.add(createSphereAtPoint(path.getEnd().getPosition(), ColorRGBA.Cyan));

            return path;
        }

        return null;
    }

    private void setupBasicKeys() {
        this.inputManager.addMapping("ResetVehicles", new Trigger[]{new KeyTrigger(KeyInput.KEY_R)});
        this.inputManager.addListener(this, new String[]{"ResetVehicles"});

        this.inputManager.addMapping("1", new Trigger[]{new KeyTrigger(KeyInput.KEY_1)});
        this.inputManager.addListener(this, new String[]{"1"});

        this.inputManager.addMapping("2", new Trigger[]{new KeyTrigger(KeyInput.KEY_2)});
        this.inputManager.addListener(this, new String[]{"2"});

        this.inputManager.addMapping("3", new Trigger[]{new KeyTrigger(KeyInput.KEY_3)});
        this.inputManager.addListener(this, new String[]{"3"});

        this.inputManager.addMapping("4", new Trigger[]{new KeyTrigger(KeyInput.KEY_4)});
        this.inputManager.addListener(this, new String[]{"4"});

        this.inputManager.addMapping("5", new Trigger[]{new KeyTrigger(KeyInput.KEY_5)});
        this.inputManager.addListener(this, new String[]{"5"});

        this.inputManager.addMapping("6", new Trigger[]{new KeyTrigger(KeyInput.KEY_6)});
        this.inputManager.addListener(this, new String[]{"6"});
    }

    private void createCharacter() {
        //CapsuleCollisionShape capsule = new CapsuleCollisionShape(0.3F, 0.4F);
        //this.character = new CharacterControl(capsule, 0.35F);
        this.model = this.assetManager.loadModel("Models/Sinbad/Sinbad.mesh.xml");
        model.setLocalScale(0.2f);
        model.setLocalTranslation(0, 1f, 0);
        this.rootNode.attachChild(this.model);
        model.addControl(new SimpleAI("Standing", steerManager));
        /*
         this.model.addControl(this.character);
         this.character.setPhysicsLocation(new Vector3f(0.0F, 5.0F, 0.0F));
         this.rootNode.attachChild(this.model);
         getPhysicsSpace().add(this.character);

         this.nmp = new NavMeshPathfinder(this.navmesh);
         this.nmp.setPosition(this.character.getPhysicsLocation());
         this.nmp.setEntityRadius(capsule.getRadius());
         */

        /*
         this.nmp.warp(this.character.getPhysicsLocation());
         Ray ray = new Ray(this.cam.getLocation(), this.cam.getDirection());

         CollisionResults collisionResults = new CollisionResults();
         this.terrain.collideWith(ray, collisionResults);

         if (collisionResults.size() != 0) {
         if (this.goal1 != null) {
         this.goal1.removeFromParent();
         }
         this.goal = collisionResults.getClosestCollision().getContactPoint();
         if (this.nmp.computePath(this.goal)) {
         this.goal1 = createSphereAtPoint(this.goal, ColorRGBA.Blue);
         }
         }
         */
    }

    private void createLight() {
        Vector3f direction = new Vector3f(0.1F, -0.7F, 1.0F).normalizeLocal();
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(direction);
        dl.setColor(new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F));
        this.rootNode.addLight(dl);
    }

    private void setupCamera() {
        getCamera().setLocation(new Vector3f(-20.0F, 20.0F, -20.0F));
        getCamera().lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        getFlyByCamera().setMoveSpeed(50.0F);
        getFlyByCamera().setDragToRotate(true);
        inputManager.setCursorVisible(true);

    }

    private void initCrossHairs() {
        this.guiFont = this.assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(this.guiFont, false);
        ch.setSize(this.guiFont.getCharSet().getRenderedSize() * 2);
        ch.setText("+");
        ch.setLocalTranslation(this.settings.getWidth() / 2 - this.guiFont.getCharSet().getRenderedSize() / 3 * 2, this.settings.getHeight() / 2 + ch.getLineHeight() / 2.0F, 0.0F);

        this.guiNode.attachChild(ch);
    }

    public void simpleUpdate(float tpf) {
    }

    public PhysicsSpace getPhysicsSpace() {
        return this.bulletAppState.getPhysicsSpace();
    }

    public void setStartPoint() {
        Ray ray = new Ray(this.cam.getLocation(), this.cam.getDirection());

        CollisionResults collisionResults = new CollisionResults();
        if (terrain != null) {
            this.terrain.collideWith(ray, collisionResults);
        } else {
            this.groundGeo.collideWith(ray, collisionResults);
        }
        if (collisionResults.size() != 0) {
            if (this.g1 != null) {
                this.g1.removeFromParent();
            }
            this.pos1 = collisionResults.getClosestCollision().getContactPoint();
            this.g1 = createSphereAtPoint(this.pos1, ColorRGBA.Orange);
        }
    }

    public void setEndPoint() {
        Ray ray = new Ray(this.cam.getLocation(), this.cam.getDirection());

        CollisionResults collisionResults = new CollisionResults();
        if (terrain != null) {
            this.terrain.collideWith(ray, collisionResults);
        } else {
            this.groundGeo.collideWith(ray, collisionResults);
        }

        if (collisionResults.size() != 0) {
            if (this.g2 != null) {
                this.g2.removeFromParent();
            }
            this.pos2 = collisionResults.getClosestCollision().getContactPoint();
            this.g2 = createSphereAtPoint(this.pos2, ColorRGBA.Green);
        }
    }

    public void createFlock() {
        if ((this.pos1 != null) && (this.pos2 != null)) {
            Path navmeshPath1 = navmesh(this.pos1, this.pos2);
            if (navmeshPath1 != null) {
                steerManager.createFlock(navmeshPath1, this.pos1);
            }
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            if (name.equals("ResetVehicles")) {
                steerManager.resetVehicles();

            } else if (name.equals("1")) {
                setStartPoint();
            } else if (name.equals("2")) {
                setEndPoint();
            } else if (name.equals("3")) {
                createFlock();
            } else if (name.equals("4")) {
                this.steerManager.flock.registerFormation();
            } else if (name.equals("5")) {
                createFlock();
            }
        }
    }

    public void setTargetVehicleToMouse() {
        Vector3f pos = cam.getWorldCoordinates(inputManager.getCursorPosition(), .0f);
        Vector3f dir = cam.getWorldCoordinates(inputManager.getCursorPosition(), .3f);
        dir.subtractLocal(pos).normalizeLocal();

        CollisionResults rs = new CollisionResults();
        Ray camRay = new Ray(pos, dir);
        if (terrain != null) {
            terrain.collideWith(camRay, rs);
        } else {
            groundGeo.collideWith(camRay, rs);
        }
        //System.out.println("press");
        if (rs.size() > 0) {
            CollisionResult result = rs.getClosestCollision();
            steerManager.mouseVehicle.setLocation(result.getContactPoint());
        }
    }

    private void initMouseTarget() {
        inputManager.addRawInputListener(mouseTargetControl);
    }
    RawInputListener mouseTargetControl = new RawInputListener() {
        @Override
        public void onTouchEvent(TouchEvent evt) {
        }

        @Override
        public void onMouseMotionEvent(MouseMotionEvent evt) {
            setTargetVehicleToMouse();
        }

        @Override
        public void onMouseButtonEvent(MouseButtonEvent evt) {
            if (evt.isPressed() && evt.getButtonIndex() == MouseInput.BUTTON_LEFT) {
            } else {
            }
        }

        @Override
        public void onKeyEvent(KeyInputEvent evt) {
        }

        @Override
        public void onJoyButtonEvent(JoyButtonEvent evt) {
        }

        @Override
        public void onJoyAxisEvent(JoyAxisEvent evt) {
        }

        @Override
        public void endInput() {
        }

        @Override
        public void beginInput() {
        }
    };
}
