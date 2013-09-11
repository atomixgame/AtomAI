/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.ai.movement.steering.common;

import sg.atom.ai.movement.core.ISteerManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Sphere;
import sg.atom.ai.movement.steering.behaviour.ObstacleAvoid;
import sg.atom.ai.movement.steering.control.SimpleVehicleControl;

/**
 *
 * @author hungcuong
 */
public class SimpleVehicleSpatial extends Node {

    public static enum SteeringStatus {

        Normal, MeetObstacle, Panic
    }

    public static enum ShapeType {

        Box, Sphere, Cylinder
    }
    protected Geometry collisionLine;
    protected Geometry velocityLine;
    SteerManager steerManager;
    DefaultVehicle vehicle;
    private ColorRGBA color;
    private Geometry shape;
    ShapeType shapeType;
    Geometry g;
    Geometry f;
    static int count = 0;

    SimpleVehicleSpatial(DefaultVehicle vehicle, SteerManager steerManager, ColorRGBA color) {
        super("Vehicle");
        this.vehicle = vehicle;
        this.color = color;
        this.steerManager = steerManager;
        shapeType = ShapeType.Box;
        createMark();
    }

    private void createMark() {
        if (shape != null) {
            detachChild(shape);
        }
        if (shapeType == ShapeType.Box) {
            Box b = new Box(Vector3f.ZERO, 1f, 1f, 1f);
            shape = new Geometry("Box", b);

        } else {
            Sphere b = new Sphere(8, 8, 1f);
            shape = new Geometry("Sphere", b);
        }

        Material mat = new Material(steerManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        shape.setMaterial(mat);
        shape.setLocalScale(vehicle.collisionRadius);
        attachChild(shape);
    }

    public void showRadius() {
    }

    public void setColor(ColorRGBA color) {
        this.color = color;
        shape.getMaterial().setColor("Color", color);
    }

    public Geometry getShape() {
        return shape;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public void showVelocity(float length, boolean show) {
        if (this.velocityLine == null) {
            Line line = new Line(Vector3f.ZERO, new Vector3f(0.0F, 0.0F, length));
            Geometry geom = new Geometry("cylinder", line);
            Material mat = new Material(this.steerManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Cyan);
            geom.setMaterial(mat);
            this.velocityLine = geom;
        }

        Line line = (Line) this.velocityLine.getMesh();
        line.updatePoints(Vector3f.ZERO, new Vector3f(0.0F, 0.0F, length));

        if (show) {
            if (this.velocityLine.getParent() == null) {
                attachChild(this.velocityLine);
            }
        } else if (this.velocityLine.getParent() != null) {
            this.velocityLine.removeFromParent();
        }
    }

    public void showObstacleMark() {
        float collisionRadius = vehicle.getBehaviourByClass(ObstacleAvoid.class).getCollisionRadius();
        if (this.g == null) {
            this.g = new Geometry("" + count++, new Sphere(6, 6, collisionRadius));
        }
        /*
         this.g.setLocalTranslation(closest);

         // No g
         if (this.g != null) {
         this.g.move(0.0F, -30.0F, 0.0F);
         }
         // set g
         Geometry g = new Geometry("" + count++, new Sphere(6, 6, 0.1F));
         g.setLocalTranslation(location);
         */
    }

    public void showAttentionMark() {
        /*
         if (true) {
         m = new Material(this.steerManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
         m.setColor("Color", ColorRGBA.Red);
         this.g.setMaterial(m);
         this.steerManager.getRootNode().attachChild(this.g);
         } else if (true) {
         Material m = new Material(this.steerManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
         m.setColor("Color", ColorRGBA.White);
         g.setMaterial(m);
         this.steerManager.getRootNode().attachChild(g);
         } else if (true) {
         if (this.f != null) {
         this.f.removeFromParent();
         }
         this.f = new Geometry("" + count++, new com.jme3.scene.shape.Line(location, location.add(velocity.mult(300.0F))));
         Material m = new Material(this.steerManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
         m.setColor("Color", ColorRGBA.Orange);
         this.f.setMaterial(m);
         this.steerManager.getRootNode().attachChild(this.f);
         }
         */
    }

    public void showCollisionRange(float length, boolean show) {
        if (this.collisionLine == null) {
            Line line = new Line(Vector3f.ZERO, new Vector3f(0.0F, 0.0F, length));
            Geometry geom = new Geometry("cylinder", line);
            Material mat = new Material(this.steerManager.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
            mat.setColor("Color", ColorRGBA.Pink);
            geom.setMaterial(mat);
            this.collisionLine = geom;
        }

        Line line = (Line) this.collisionLine.getMesh();
        line.updatePoints(Vector3f.ZERO, new Vector3f(0.0F, 0.0F, length));

        if (show) {
            if (this.collisionLine.getParent() == null) {
                attachChild(this.collisionLine);
            }
        } else if (this.collisionLine.getParent() != null) {
            this.collisionLine.removeFromParent();
        }
    }

    public void updateView() {
        showVelocity(this.vehicle.velocity.length(), true);
        setLocalTranslation(vehicle.location);
    }

    void addSteerControl(SimpleVehicleControl steerControl) {
        addControl(steerControl);
        steerControl.setVehicle(vehicle);
    }

    public void setCollideWarning(boolean warn) {
        if (this.collisionLine != null) {
            if (warn) {
                this.collisionLine.getMaterial().setColor("Color", ColorRGBA.Red);
            } else {
                this.collisionLine.getMaterial().setColor("Color", ColorRGBA.Cyan);
            }
        }
    }
}
