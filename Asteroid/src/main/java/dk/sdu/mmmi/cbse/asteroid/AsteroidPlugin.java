package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private Random random = new Random();
    private static final int MAX_ASTEROIDS = 15; // Maximum number of asteroids
    private static final int SPAWN_INTERVAL = 3000; // Interval in milliseconds (3 seconds)
    private long lastSpawnTime = 0;

    @Override
    public void start(GameData gameData, World world) {
        // Spawn initial asteroids
        for (int i = 0; i < MAX_ASTEROIDS; i++) {
            Entity asteroid = createAsteroid(gameData, 3); // Initial asteroids with max size level
            world.addEntity(asteroid);
            System.out.println("Asteroid added at start: X=" + asteroid.getX() + ", Y=" + asteroid.getY());
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        // Check the time elapsed since the last spawn
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpawnTime >= SPAWN_INTERVAL && world.getEntitiesByType("asteroid").size() < MAX_ASTEROIDS) {
            Entity asteroid = createAsteroid(gameData, 3); // Spawn new asteroids with max size level
            world.addEntity(asteroid);
            System.out.println("Asteroid dynamically spawned: X=" + asteroid.getX() + ", Y=" + asteroid.getY());
            lastSpawnTime = currentTime; // Reset the spawn timer
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        // No need to remove asteroids on stop
    }

    private Entity createAsteroid(GameData gameData, int sizeLevel) {
        Asteroid asteroid = new Asteroid(sizeLevel);
        asteroid.setType("asteroid");
        asteroid.setSize(10 + random.nextInt(20)); // Size varies between 10 and 30
        int edge = random.nextInt(4); // Random edge: 0=top, 1=bottom, 2=left, 3=right
        float size = asteroid.getSize(); // Use asteroid's size for edge calculations

        switch (edge) {
            case 0: // Top edge
                asteroid.setX(random.nextFloat() * (gameData.getDisplayWidth() - size) + size / 2);
                asteroid.setY(gameData.getDisplayHeight() - size / 2);
                break;
            case 1: // Bottom edge
                asteroid.setX(random.nextFloat() * (gameData.getDisplayWidth() - size) + size / 2);
                asteroid.setY(size / 2);
                break;
            case 2: // Left edge
                asteroid.setX(size / 2);
                asteroid.setY(random.nextFloat() * (gameData.getDisplayHeight() - size) + size / 2);
                break;
            case 3: // Right edge
                asteroid.setX(gameData.getDisplayWidth() - size / 2);
                asteroid.setY(random.nextFloat() * (gameData.getDisplayHeight() - size) + size / 2);
                break;
        }

        asteroid.setRotation(random.nextDouble() * 360); // Random rotation
        asteroid.setSpeed(1 + random.nextDouble() * 2); // Speed between 1 and 3 for variability

        // Set polygonal shape resembling an asteroid
        asteroid.setPolygonCoordinates(
                -size * 0.6, size * 0.8, // Vertex 1
                size * 0.2, size * 1.0,  // Vertex 2
                size * 0.8, size * 0.4,  // Vertex 3
                size * 1.0, -size * 0.2, // Vertex 4
                size * 0.6, -size * 1.0, // Vertex 5
                0, -size * 1.2,          // Vertex 6
                -size * 0.8, -size * 0.8,// Vertex 7
                -size * 1.2, -size * 0.2,// Vertex 8
                -size * 1.0, size * 0.2  // Vertex 9
        );

        return asteroid;
    }
}
