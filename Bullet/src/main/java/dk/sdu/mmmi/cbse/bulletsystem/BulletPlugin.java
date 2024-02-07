package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService {

    private Entity bullet;

    @Override
    public void start(GameData gameData, World world) {

    }


    public void createBullet(GameData gameData, World world, Entity shooter) {
        Bullet bullet = new Bullet();
        bullet.setX(shooter.getX() + calculateOffsetX(shooter)); // Calculate based on shooter's facing
        bullet.setY(shooter.getY() + calculateOffsetY(shooter)); // Calculate based on shooter's facing
        bullet.setRadians(Math.toRadians(shooter.getRotation()));
        bullet.setSpeed(200); // Example speed
        // Set other bullet properties here

        world.addEntity(bullet);
    }

    private double calculateOffsetX(Entity shooter) {
        // Implement logic to calculate the X offset for bullet creation
        return 0; // Placeholder
    }

    private double calculateOffsetY(Entity shooter) {
        // Implement logic to calculate the Y offset for bullet creation
        return 0; // Placeholder
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.getEntities(Bullet.class).forEach(world::removeEntity);
    }

}
