package sg.atom.ai.movement.core;

import com.jme3.math.Vector3f;

public abstract interface Obstacle
{
  public abstract Vector3f getVelocity();

  public abstract Vector3f getLocation();

  public abstract float getRadius();
}

