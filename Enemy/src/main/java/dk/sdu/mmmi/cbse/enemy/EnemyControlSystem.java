package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class EnemyControlSystem implements IEntityProcessingService {

    private Random random = new Random();
    private Collection<BulletSPI> bulletServices;

    public EnemyControlSystem() {
        bulletServices = ServiceLoader.load(BulletSPI.class).stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            handleRotation(enemy);
            handleMovement(enemy);
            handleShooting(enemy, gameData, world);
            enforceScreenBounds(enemy, gameData);
        }
    }

    private void handleRotation(Entity enemy) {
        int rotationChange = random.nextInt(6) - 3; // Randomly rotates between -3 and 2 degrees
        enemy.setRotation(enemy.getRotation() + rotationChange);
    }

    private void handleMovement(Entity enemy) {
        double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
        double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
        enemy.setX(enemy.getX() + changeX);
        enemy.setY(enemy.getY() + changeY);
    }

    private void handleShooting(Entity enemy, GameData gameData, World world) {
        double shootingTimer = enemy.getTimer("shooting");
        if (shootingTimer <= 0) {
            double shootingDuration = 0.5 + random.nextDouble(); // shooting every 0.5 to 1.5 seconds
            enemy.setTimer("shooting", shootingDuration);
            bulletServices.forEach(bulletService -> world.addEntity(bulletService.createBullet(enemy, gameData)));
        } else {
            enemy.setTimer("shooting", shootingTimer - gameData.getDelta());
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
