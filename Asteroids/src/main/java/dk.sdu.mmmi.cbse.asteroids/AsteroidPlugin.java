package dk.sdu.mmmi.cbse.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private Random rand = new Random();
    private long lastSpawnTime = 0; // Last spawn time
    private final long spawnInterval = 3000; // Spawn interval in milliseconds

    @Override
    public void start(GameData gameData, World world) {
        // Set initial last spawn time to ensure an asteroid spawns immediately when the game starts
        lastSpawnTime = System.currentTimeMillis() - spawnInterval;

        Entity asteroid = createAsteroid(gameData);

        
        System.out.println("Asteroid Start");
    }

    @Override
    public void process(GameData gameData, World world) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpawnTime >= spawnInterval) {
            // Time to spawn a new asteroid
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
            lastSpawnTime = currentTime; // Update last spawn time
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid(rand.nextInt(3) + 1); // Random size
        asteroid.setX(rand.nextDouble() * gameData.getDisplayWidth()); // Random X position
        asteroid.setY(rand.nextDouble() * gameData.getDisplayHeight()); // Random Y position
        asteroid.setSpeed(1 + rand.nextDouble() * 4); // Speed between 1 and 5
        asteroid.setRotation(rand.nextDouble() * 360); // Random rotation

        asteroid.setPolygonCoordinates(
                // Top left corner
                -3, 3,
                // Top right corner
                3, 3,
                // Bottom right corner
                3, -3,
                // Bottom left corner
                -3, -3
        );


        return asteroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove all asteroids
        for (Entity e : world.getEntities(Asteroid.class)) {
            world.removeEntity(e);
        }
    }
}