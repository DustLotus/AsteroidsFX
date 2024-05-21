package dk.sdu.mmmi.cbse.bulletsystem;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.UUID;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            // Calculate change in X and Y based on bullet's rotation
            double changeX = Math.cos(Math.toRadians(bullet.getRotation())) * bullet.getSpeed();
            double changeY = Math.sin(Math.toRadians(bullet.getRotation())) * bullet.getSpeed();

            // Update bullet's position
            bullet.setX(bullet.getX() + changeX);
            bullet.setY(bullet.getY() + changeY);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();
        bullet.setOwnerId(UUID.fromString(shooter.getID()));
        bullet.setType("bullet");
        bullet.setSpeed(10); // Set bullet speed
        bullet.setX(shooter.getX()); // Initial X position from shooter
        bullet.setY(shooter.getY()); // Initial Y position from shooter

        bullet.setRotation(shooter.getRotation()); // Set bullet's rotation to match the shooter's rotation exactly

        bullet.setPolygonCoordinates(
                // Top left corner
                -2, 1,
                // Top right corner
                2, 1,
                // Bottom right corner
                2, -1,
                // Bottom left corner
                -2, -1
        );

        return bullet;
    }

    private void setShape(Entity entity) {
    }
}


