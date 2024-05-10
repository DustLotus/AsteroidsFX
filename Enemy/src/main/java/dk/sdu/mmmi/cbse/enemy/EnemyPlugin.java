package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    public void process(GameData gameData, World world) {

    }

    private Entity createEnemyShip(GameData gameData) {

        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(
                11, 0,    // Rightmost point (head of the star, made longer)
                1, -3,    // Inner point between top and right arm
                1, -10,   // Top arm
                -4, -4,    // Inner point between top and left upper arm
                -9, -6,   // Left upper arm
                -6, 0,    // Inner left point
                -9, 6,    // Left lower arm
                -4, 4,     // Inner point between left lower and right lower arm
                1, 10,    // Right lower arm
                1, 3      // Inner point between right lower arm and head
        );

        enemyShip.setX(gameData.getDisplayHeight()/2);
        enemyShip.setY(gameData.getDisplayWidth()/2);
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }

}
