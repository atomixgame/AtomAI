package sg.atom.ai.movement.steering.group;

import sg.atom.ai.movement.steering.common.DefaultVehicle;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
import jme3tools.navmesh.Path;
import sg.atom.ai.movement.core.AbstractVehicle;
import sg.atom.ai.movement.steering.common.SteerManager;

public class Flock<T extends AbstractVehicle> {

    private Node flockNode;
    public int lastPath;
    public T pivot;
    private Path path;
    public ArrayList<T> members;
    private HashMap<T, Vector3f> positions;
    private final SteerManager steerManager;

    public Flock(SteerManager steerManager, String name) {
        this.steerManager = steerManager;
        this.lastPath = 0;
        this.members = new ArrayList();
        this.positions = new HashMap();
        this.flockNode = new Node("Flock Node_" + name);
    }

    public boolean add(T e) {
        this.flockNode.attachChild(steerManager.getViewFor((DefaultVehicle) e));
        return this.members.add(e);
    }

    public boolean has(T e) {
        return this.members.contains(e);
    }

    public boolean remove(T e) {
        this.flockNode.attachChild(steerManager.getViewFor((DefaultVehicle) e));
        return this.members.remove(e);
    }

    public void setPath(Path p) {
        this.path = p;
    }

    public Path getPath() {
        return this.path;
    }

    public void registerFormation() {
        this.positions.clear();
        Vector3f center = new Vector3f();
        for (AbstractVehicle e : this.members) {
            Vector3f pos = e.getLocation();
            center.addLocal(pos);
        }
        center.divideLocal(this.members.size());
        this.pivot = ((T) this.members.get(0));
        for (T e : this.members) {
            if (e.getLocation().distance(center) < this.pivot.getLocation().distance(center)) {
                this.pivot = e;
            }
        }
        for (T e : this.members) {
            this.positions.put(e, e.getLocation().subtract(this.pivot.getLocation()));
        }
    }

    public Vector3f getRelativePosition(T e) {
        return (Vector3f) this.positions.get(e);
    }

    public Node getFlockNode() {
        return this.flockNode;
    }
}
