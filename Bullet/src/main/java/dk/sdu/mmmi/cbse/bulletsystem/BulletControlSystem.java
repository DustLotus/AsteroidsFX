package dk.sdu.mmmi.cbse.bulletsystem;
import java.util.Random;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    private Random rand = new Random();

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
        bullet.setSpeed(10);
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());

        // Define the spread range (e.g., +/- 5 degrees)
        double spreadAngle = 5.0; // Maximum deviation in degrees
        double offset = (rand.nextDouble() * 2 * spreadAngle) - spreadAngle; // Generate random offset within the spread range
        bullet.setRotation(shooter.getRotation() + offset); // Apply the offset to the shooter's rotation

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
