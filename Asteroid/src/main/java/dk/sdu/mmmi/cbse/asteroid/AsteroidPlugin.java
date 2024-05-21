package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        // Determine how many asteroids to spawn at the start
        int numAsteroids = 5; // For example, start with 5 asteroids
        for (int i = 0; i < numAsteroids; i++) {
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
            System.out.println("Asteroid added at start: X=" + asteroid.getX() + ", Y=" + asteroid.getY());
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        // Process method remains empty if no dynamic spawning is needed during the game
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid(random.nextInt(3) + 1);
        asteroid.setSize(20 + random.nextInt(20)); // Size varies between 20 and 40 for visibility
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
                -size * 0.3, size * 0.4, // Vertex 1
                size * 0.1, size * 0.5,  // Vertex 2
                size * 0.4, size * 0.2,  // Vertex 3
                size * 0.5, -size * 0.1, // Vertex 4
                size * 0.3, -size * 0.5, // Vertex 5
                0, -size * 0.6,          // Vertex 6
                -size * 0.4, -size * 0.4,// Vertex 7
                -size * 0.6, -size * 0.1,// Vertex 8
                -size * 0.5, size * 0.1  // Vertex 9
        );

        return asteroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity e : world.getEntities(Asteroid.class)) {
            world.removeEntity(e);
        }
    }
}
