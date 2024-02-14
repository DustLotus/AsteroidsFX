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
            double radians = Math.toRadians(bullet.getRotation());
            double dx = Math.cos(radians) * bullet.getSpeed();
            double dy = Math.sin(radians) * bullet.getSpeed();

            System.out.println("Before: X=" + bullet.getX() + ", Y=" + bullet.getY());
            bullet.setX(bullet.getX() + 5); // Example update
            System.out.println("After: X=" + bullet.getX() + ", Y=" + bullet.getY()); // Move bullet right by 5 units each frame/update
            bullet.setY(bullet.getY());
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Entity();
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
