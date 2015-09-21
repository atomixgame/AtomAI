package sg.atom.ai.movement.core;

import com.jme3.math.Vector3f;

public interface Obstacle
{
  public Vector3f getVelocity();

  public Vector3f getLocation();

  public float getRadius();
}

