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

    // Timer variables for rotation and shooting
    private double timeUntilNextRotation = 0;
    private double timeUntilNextShot = 0;

    @Override
    public void process(GameData gameData, World world) {
        double delta = gameData.getDelta();

        for (Entity enemy : world.getEntities(Enemy.class)) {
            // Rotation timer
            if (timeUntilNextRotation <= 0) {
                timeUntilNextRotation = 1 + random.nextDouble() * 2; // Reset timer between 1 to 3 seconds
                int rotationChange = random.nextInt(360) - 180; // Change rotation between -180 to 180 degrees
                enemy.setRotation(enemy.getRotation() + rotationChange);
            } else {
                timeUntilNextRotation -= delta; // Decrement timer by elapsed time
            }

            // Continuous forward movement
            double changeX = Math.cos(Math.toRadians(enemy.getRotation())) * enemy.getSpeed();
            double changeY = Math.sin(Math.toRadians(enemy.getRotation())) * enemy.getSpeed();
            enemy.setX(enemy.getX() + changeX * delta);
            enemy.setY(enemy.getY() + changeY * delta);

            // Shooting timer
            if (timeUntilNextShot <= 0) {
                timeUntilNextShot = 1 + random.nextDouble() * 2; // Reset timer between 1 to 3 seconds
                for (BulletSPI bullet : getBulletSPIs()) {
                    world.addEntity(bullet.createBullet(enemy, gameData));
                }
            } else {
                timeUntilNextShot -= delta; // Decrement timer by elapsed time
            }

            // Keep enemy within screen bounds
            enforceScreenBounds(enemy, gameData);
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

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
