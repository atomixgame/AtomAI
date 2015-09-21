/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.movement.steering.common;

import sg.atom.ai.movement.core.ISteerManager;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import sg.atom.ai.searching.pathfinding.core.Path;
import sg.atom.ai.movement.steering.control.AvoidControl;
import sg.atom.ai.movement.steering.control.FleeControl;
import sg.atom.ai.movement.steering.control.PathFollowerControl;
import sg.atom.ai.movement.steering.control.SeekControl;
import sg.atom.ai.movement.steering.control.SimpleVehicleControl;
import sg.atom.ai.movement.core.AbstractVehicle;
import sg.atom.ai.movement.steering.group.Flock;
import sg.atom.ai.movement.core.Obstacle;

/**
 *
 * @author hungcuong
 */
public class SteerManager implements ISteerManager {
    
    public Node vehicleNode;
    public Node obstacleNode;
    public Node friendNode;
    SimpleApplication app;
    private Node rootNode;
    public Flock flock;
    
    private ArrayList<DefaultVehicle> vehicles;
    private ArrayList<DefaultVehicle> obstacles;
    private ArrayList<DefaultVehicle> friends;
    
    /**
     * Special vehicle represent the mouse
     */ 
    public DefaultVehicle mouseVehicle;
    
    /** 
     * Due to Spatial based vehicle, this Map exist
     */
    private HashMap<DefaultVehicle, Spatial> steerVehicleToSpatialMap = new HashMap<DefaultVehicle, Spatial>();
    
    public SteerManager(SimpleApplication app) {
        this.app = app;
        this.rootNode = app.getRootNode();
        vehicles = new ArrayList<DefaultVehicle>();
        obstacles = new ArrayList<DefaultVehicle>();
        friends = new ArrayList<DefaultVehicle>();
    }
    
    /**
     * Create the simplest view for debug 
     * @param v
     * @param isStaticObstacle
     * @param color
     * @param controls
     * @return 
     */
    public SimpleVehicleSpatial createViewFor(DefaultVehicle v, boolean isStaticObstacle, ColorRGBA color, SimpleVehicleControl... controls) {
        SimpleVehicleSpatial vs = new SimpleVehicleSpatial(v, this, color);
        if (!isStaticObstacle) {
            if (controls != null) {
                for (SimpleVehicleControl control : controls) {
                    vs.addSteerControl(control);
                }
            }
        }
        setViewFor(v, vs);
        return vs;
    }
    
    public Spatial getViewFor(DefaultVehicle aVehicle) {
        return steerVehicleToSpatialMap.get(aVehicle);
    }
    
    public void setViewFor(DefaultVehicle aVehicle, Spatial sp) {
        steerVehicleToSpatialMap.put(aVehicle, sp);
        
    }
    
    public void createRandomObstacles(int amount) {
        this.obstacleNode = new Node("Obstacles");
        for (int i = 0; i < amount; i++) {
            DefaultVehicle obstacle = new DefaultVehicle(this);
            obstacle.init();
            Vector3f loc = new Vector3f((float) Math.random() * 100.0F - 50.0F, 0.0F, (float) Math.random() * 100.0F - 50.0F);
            SimpleVehicleSpatial ovs = createViewFor(obstacle, true, ColorRGBA.Yellow, new SimpleVehicleControl[]{new SimpleVehicleControl(this)});

            //System.out.println(" " + loc.toString());
            obstacle.setLocation(loc);
            obstacle.setStop(true);
            ovs.updateView();
            this.obstacleNode.attachChild(ovs);
            this.obstacles.add(obstacle);
        }
    }
    
    public void createNodes() {
        this.vehicleNode = new Node("Vehicles");
        this.friendNode = new Node("Friends");
    }
    
    public void createAvoidTest() {
        DefaultVehicle aVehicle = new DefaultVehicle(this);
        SimpleVehicleSpatial ovs = createViewFor(aVehicle, false, ColorRGBA.LightGray, new SimpleVehicleControl[]{new AvoidControl(this)});
        //ovs.getShape().setLocalScale(5);
        this.vehicleNode.attachChild(ovs);
        this.obstacles.add(aVehicle);
    }
   public void createSeparationTest() {
        DefaultVehicle aVehicle = new DefaultVehicle(this);
        SimpleVehicleSpatial ovs = createViewFor(aVehicle, false, ColorRGBA.Cyan, new SimpleVehicleControl[]{new SeekControl(this, mouseVehicle)});
        //ovs.getShape().setLocalScale(5);
        this.vehicleNode.attachChild(ovs);
        this.obstacles.add(aVehicle);
    }
      public void createFleeTest() {
        DefaultVehicle aVehicle = new DefaultVehicle(this);
        SimpleVehicleSpatial ovs = createViewFor(aVehicle, false, ColorRGBA.Cyan, new SimpleVehicleControl[]{new FleeControl(this, mouseVehicle)});
        //ovs.getShape().setLocalScale(5);
        this.vehicleNode.attachChild(ovs);
        this.obstacles.add(aVehicle);
    }
    public void createSeekTest() {
        DefaultVehicle aVehicle = new DefaultVehicle(this);
        SimpleVehicleSpatial ovs = createViewFor(aVehicle, false, ColorRGBA.Cyan, new SimpleVehicleControl[]{new SeekControl(this, mouseVehicle)});
        //ovs.getShape().setLocalScale(5);
        this.vehicleNode.attachChild(ovs);
        this.obstacles.add(aVehicle);
    }
    
    public void createTestFlocks() {
        /*
         amount = 0;
        
         Flock f = new Flock();
         for (int i = 0; i < amount; i++) {
         Vehicle neighbour = new Vehicle(ColorRGBA.Blue, this);
         neighbour.flock = f;
         f.add(neighbour);
         Vector3f loc = new Vector3f((float) Math.random() * 5.0F, 0.0F, (float) Math.random() * 5.0F);
         neighbour.setLocation(loc);
         neighbour.addControl(new FlockerControl(this));
         this.friends.add(neighbour);
         }
        
         this.friendNode.attachChild(f);
         */
    }
    
    public void attachNodes() {
        // Now display all the vehicle
        this.vehicleNode.attachChild(this.obstacleNode);
        this.vehicleNode.attachChild(this.friendNode);
        this.rootNode.attachChild(this.vehicleNode);
    }
    
    public void createHero() {
        // Create one vehicle run toward (1,1,1) with Avoid behavior
        DefaultVehicle v = new DefaultVehicle(this);
        v.init();
        v.velocity = Vector3f.UNIT_XYZ;
        
        SimpleVehicleSpatial vs = new SimpleVehicleSpatial(v, this, ColorRGBA.LightGray);
        vs.addSteerControl(new AvoidControl(this));
        this.vehicleNode.attachChild(vs);
        vehicles.add(v);
    }
    
    public void createMouse() {
        // Display the mouse Vehicle
        mouseVehicle = new DefaultVehicle(this);
        mouseVehicle.init();
        SimpleVehicleSpatial mvs = createViewFor(mouseVehicle, false, ColorRGBA.Blue, new SimpleVehicleControl[]{new AvoidControl(this)});
        this.rootNode.attachChild(mvs);
    }
    
    public List<Obstacle> getObstacals() {
        List obList = new ArrayList();
        for (DefaultVehicle v : this.obstacles) {
            obList.add(v.toObstacle());
        }
        return obList;
    }
    
    public List<Obstacle> obstacalsNearby(AbstractVehicle source, float radius) {
        List obList = new ArrayList();
        float r2 = radius * radius;
        for (DefaultVehicle v : this.obstacles) {
            float d = source.getWorldTranslation().subtract(v.getWorldTranslation()).lengthSquared();
            if (d < r2) {
                obList.add(v.toObstacle());
            }
        }
        if (source.flock != null) {
            for (DefaultVehicle v : this.friends) {
                float d = source.getWorldTranslation().subtract(v.getWorldTranslation()).lengthSquared();
                if ((d < r2) && (!source.flock.has(v))) {
                    obList.add(v.toObstacle());
                }
            }
        }
        return obList;
    }
    
    public List<Obstacle> neighboursNearby(AbstractVehicle source, float radius) {
        List neighbours = new ArrayList();
        float r2 = radius * radius;
        //Node n = source.flock != null ? source.flock.getFlockNode() : this.friendNode;
        for (DefaultVehicle v : this.friends) {
            if (!v.equals(source)) {
                
                float d = source.getWorldTranslation().subtract(v.getWorldTranslation()).lengthSquared();
                if (d < r2) {
                    if ((source.flock != null) && (source.flock.has(v))) {
                        neighbours.add(v.toObstacle());
                    } else if (source.flock == null) {
                        neighbours.add(v.toObstacle());
                    }
                }
            }
        }
        return neighbours;
    }

    
    public void resetVehicles() {
        vehicles.clear();
        if (vehicleNode != null) {
            this.rootNode.detachChild(vehicleNode);
            createNodes();
        }
    }
    
    public void addVehicle(DefaultVehicle aVehicle) {
        vehicles.add(aVehicle);
        friends.add(aVehicle);
    }
    
    public void createFlock(Path navmeshPath1, Vector3f initialPos) {
        if (this.flock != null) {
            this.flock.getFlockNode().removeFromParent();
        }
        int amount = 30;
        float random = 10.0F;
        this.flock = new Flock(this, "Flock 1");
        this.flock.setPath(navmeshPath1);
        for (int i = 0; i < amount; i++) {
            DefaultVehicle vehicle = new DefaultVehicle(this);
            vehicle.init();
            
            SimpleVehicleSpatial vehicleSpatial = createViewFor(vehicle, false, ColorRGBA.Blue, new SimpleVehicleControl[]{new PathFollowerControl(this)});
            
            Vector3f pos = initialPos.add(FastMath.nextRandomFloat() * random - random / 2.0F, 0.0F, FastMath.nextRandomFloat() * random - random / 2.0F);
            vehicle.setLocation(pos);
            vehicle.flock = this.flock;
            this.flock.add(vehicle);
        }
        this.friendNode.attachChild(this.flock.getFlockNode());
    }
    
    public Vector3f getMaxSteeringForce(){
        return null;
    }
    
    /* GETTER SETTER SHORTCUT*/
    public AssetManager getAssetManager() {
        return app.getAssetManager();
    }
    
    public PhysicsSpace getPhysicsSpace() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
