package sg.atom.ai.movement.core;

import java.util.List;

public abstract interface ISteerManager
{
  public abstract List<Obstacle> getObstacals();

  public abstract List<Obstacle> obstacalsNearby(AbstractVehicle paramAbstractVehicle, float paramFloat);

  public abstract List<Obstacle> neighboursNearby(AbstractVehicle paramAbstractVehicle, float paramFloat);

}
