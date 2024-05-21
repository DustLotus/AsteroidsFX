package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation())) * asteroid.getSpeed();
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation())) * asteroid.getSpeed();

            asteroid.setX(asteroid.getX() + changeX);
            asteroid.setY(asteroid.getY() + changeY);

            enforceScreenBounds(asteroid, gameData);
            System.out.println("Asteroid moved to X: " + asteroid.getX() + " Y: " + asteroid.getY());
        }
    }

    private void enforceScreenBounds(Entity entity, GameData gameData) {
        if (entity.getX() < 0) {
            entity.setX(gameData.getDisplayWidth());
        }
        if (entity.getX() > gameData.getDisplayWidth()) {
            entity.setX(0);
        }
        if (entity.getY() > gameData.getDisplayHeight()) {
            entity.setY(0);
        }
        if (entity.getY() < 0) {
            entity.setY(gameData.getDisplayHeight());
        }
    }
}
