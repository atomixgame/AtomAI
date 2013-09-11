package sg.atom.ai.searching.pathfinding.space.navmesh;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.HashMap;
import java.util.LinkedList;
import jme3tools.navmesh.NavMesh;
import jme3tools.navmesh.util.NavMeshGenerator;
import jme3tools.optimize.GeometryBatchFactory;
import sg.atom.world.SceneGraphHelper;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class NavMeshHelper {

    private NavMesh navMesh = new NavMesh();
    private Node rootNode;
    private Node worldRoot;
    private HashMap<Long, Spatial> entities = new HashMap<Long, Spatial>();
    private int newId = 0;
    private Application app;
    private AssetManager assetManager;
    private NavMeshGenerator generator = new NavMeshGenerator();

    /**
     * creates the nav mesh for the loaded level
     */
    public void createNavMesh() {

        Mesh mesh = new Mesh();

        //version a: from mesh
        GeometryBatchFactory.mergeGeometries(SceneGraphHelper.findGeometries(worldRoot, new LinkedList<Geometry>()), mesh);
        Mesh optiMesh = generator.optimize(mesh);

        navMesh.loadFromMesh(optiMesh);

        //TODO: navmesh only for debug
        Geometry navGeom = new Geometry("NavMesh");
        navGeom.setMesh(optiMesh);
        Material green = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        green.setColor("Color", ColorRGBA.Green);
        green.getAdditionalRenderState().setWireframe(true);
        navGeom.setMaterial(green);

        worldRoot.attachChild(navGeom);
    }
}
