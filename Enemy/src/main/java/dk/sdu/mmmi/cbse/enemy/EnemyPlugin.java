package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {

    private Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        spawnEnemies(gameData, world);
    }

    @Override
    public void process(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Enemy.class).forEach(world::removeEntity);
    }

    private void spawnEnemies(GameData gameData, World world) {
        int currentEnemyCount = world.getEntities(Enemy.class).size();
        int maxEnemies = 5;

        while (currentEnemyCount < maxEnemies) {
            Entity enemy = createEnemyShip(gameData);
            world.addEntity(enemy);
            currentEnemyCount++;
        }
    }

    private Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Enemy();
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
                7, 0,    // Rightmost point (head of the star, made longer)
                -6, -5,   // Left upper arm
                -4, 0,    // Inner left point
                -6, 5   // Left lower arm
        );

        // Debug printout for spawning information
        System.out.printf("Spawned Enemy at X: %.2f, Y: %.2f, Edge: %d, Rotation: %.2f\n",
                enemyShip.getX(), enemyShip.getY(), edge, enemyShip.getRotation());

        return enemyShip;
    }

}
