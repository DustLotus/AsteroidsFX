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
            double dx = Math.cos(bullet.getRadians()) * bullet.getSpeed() * gameData.getDelta();
            double dy = Math.sin(bullet.getRadians()) * bullet.getSpeed() * gameData.getDelta();
            bullet.setX(bullet.getX() + dx);
            bullet.setY(bullet.getY() + dy);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Bullet bullet = new Bullet();
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        bullet.setRadians(Math.toRadians(shooter.getRotation()));
        bullet.setSpeed(1);
        setShape(bullet);
        System.out.println("Bullet created: Position (" + bullet.getX() + ", " + bullet.getY() +
                ") Direction (Radians): " + bullet.getRadians() +
                " Speed: " + bullet.getSpeed());
        return bullet;
    }

    private void setShape(Entity entity) {
        // Example shape setting, you can adjust as per your bullet's visual representation
        entity.setPolygonCoordinates(1, -2, 4, 1, 1, 2, -4, 1);

    }
}