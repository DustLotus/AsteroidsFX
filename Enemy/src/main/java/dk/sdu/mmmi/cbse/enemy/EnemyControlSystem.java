package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {

    private Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            // Random rotation change
            int rotationChange = random.nextInt(6) - 3; // Randomly rotates between -3 and 2 degrees
            enemy.setRotation(enemy.getRotation() + rotationChange);

            // Continuous forward movement
            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

            // Random shooting logic
            if (random.nextBoolean()) { // Randomly decides whether to shoot
                for (BulletSPI bullet : getBulletSPIs()) {
                    world.addEntity(bullet.createBullet(enemy, gameData));
                }
            }

            // Keep enemy within screen bounds
            enforceScreenBounds(enemy, gameData);
        }
    }

    private void enforceScreenBounds(Entity enemy, GameData gameData) {
        if (enemy.getX() < 0) enemy.setX(1);
        if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(gameData.getDisplayWidth() - 1);
        if (enemy.getY() < 0) enemy.setY(1);
        if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(gameData.getDisplayHeight() - 1);
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
