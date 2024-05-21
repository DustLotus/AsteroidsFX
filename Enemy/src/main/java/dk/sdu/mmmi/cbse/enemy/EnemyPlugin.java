package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {

    private Random random = new Random();
    private static final int MAX_ENEMIES = 5; // Maximum number of enemies
    private static final int SPAWN_INTERVAL = 6000; // Interval in milliseconds (6 seconds)
    private long lastSpawnTime = 0;

    @Override
    public void start(GameData gameData, World world) {
        // Spawn initial enemies
        for (int i = 0; i < MAX_ENEMIES; i++) {
            Entity enemy = createEnemyShip(gameData);
            world.addEntity(enemy);
            System.out.println("Enemy added at start: X=" + enemy.getX() + ", Y=" + enemy.getY());
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        // Check the time elapsed since the last spawn
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpawnTime >= SPAWN_INTERVAL && world.getEntitiesByType("enemyShip").size() < MAX_ENEMIES) {
            Entity enemy = createEnemyShip(gameData); // Spawn new enemies
            world.addEntity(enemy);
            System.out.println("Enemy dynamically spawned: X=" + enemy.getX() + ", Y=" + enemy.getY());
            lastSpawnTime = currentTime; // Reset the spawn timer
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        // No need to remove enemies on stop
    }

    private Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Enemy();
        enemyShip.setType("enemyShip");
        float size = 30; // Assuming a size for visibility calculations
        int edge = random.nextInt(4); // Random edge: 0=top, 1=bottom, 2=left, 3=right

        switch (edge) {
            case 0: // Top edge
                enemyShip.setX(random.nextFloat() * (gameData.getDisplayWidth() - size) + size / 2);
                enemyShip.setY(gameData.getDisplayHeight() - size / 2);
                break;
            case 1: // Bottom edge
                enemyShip.setX(random.nextFloat() * (gameData.getDisplayWidth() - size) + size / 2);
                enemyShip.setY(size / 2);
                break;
            case 2: // Left edge
                enemyShip.setX(size / 2);
                enemyShip.setY(random.nextFloat() * (gameData.getDisplayHeight() - size) + size / 2);
                break;
            case 3: // Right edge
                enemyShip.setX(gameData.getDisplayWidth() - size / 2);
                enemyShip.setY(random.nextFloat() * (gameData.getDisplayHeight() - size) + size / 2);
                break;
        }

        enemyShip.setRotation(random.nextInt(360)); // Initial random rotation
        enemyShip.setSize((int) size); // Set size

        // Set the polygonal shape for the enemy ship
        enemyShip.setPolygonCoordinates(
                10, 0,   // Rightmost point (head of the star, made longer)
                -16, -10, // Left upper arm
                -12, 0,  // Inner left point
                -16, 10  // Left lower arm
        );

        // Debug printout for spawning information
        System.out.printf("Spawned Enemy at X: %.2f, Y: %.2f, Edge: %d, Rotation: %.2f\n",
                enemyShip.getX(), enemyShip.getY(), edge, enemyShip.getRotation());

        return enemyShip;
    }
}
