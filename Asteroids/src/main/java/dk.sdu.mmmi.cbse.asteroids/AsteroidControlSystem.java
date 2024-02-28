package dk.sdu.mmmi.cbse.asteroids;


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



            System.out.println("Asteroid moved to X: " + asteroid.getX() + " Y: " + asteroid.getY());
        }
    }
}