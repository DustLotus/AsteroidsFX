package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            world.addEntity(bullet);
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
        bullet.setSpeed(3);
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        bullet.setRotation(shooter.getRotation());

        bullet.setPolygonCoordinates(
            // Top left corner
            -2, 2,
            // Top right corner
            2, 2,
            // Bottom right corner
            2, -2,
            // Bottom left corner
            -2, -2
        );

        return bullet;
    }

    private void setShape(Entity entity) {
    }

}
