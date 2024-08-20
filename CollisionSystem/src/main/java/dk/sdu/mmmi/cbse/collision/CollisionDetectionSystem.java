package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.ICollisionDetectionService;
import dk.sdu.mmmi.cbse.main.Main;

import java.util.Collection;

public class CollisionDetectionSystem implements ICollisionDetectionService {

    @Override
    public void process(World world) {
        Collection<Entity> entities = world.getEntities();
        Entity[] entityArray = entities.toArray(new Entity[0]);

        for (int i = 0; i < entityArray.length; i++) {
            Entity entity1 = entityArray[i];
            for (int j = i + 1; j < entityArray.length; j++) {
                Entity entity2 = entityArray[j];
                if (checkCollision(entity1, entity2)) {
                    handleCollision(entity1, entity2, world);
                }
            }
        }
    }

    private boolean checkCollision(Entity entity1, Entity entity2) {
        double dx = entity1.getX() - entity2.getX();
        double dy = entity1.getY() - entity2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double totalRadius = entity1.getSize() / 2 + entity2.getSize() / 2;
        return distance < totalRadius;
    }

    private void handleCollision(Entity entity1, Entity entity2, World world) {
        if (entity1.getType().equals("bullet") || entity2.getType().equals("bullet")) {
            Entity bullet = entity1.getType().equals("bullet") ? entity1 : entity2;
            Entity other = bullet == entity1 ? entity2 : entity1;

            if (bullet.getOwnerId() != null && bullet.getOwnerId().toString().equals(other.getID())) {
                return; // Ignore collision if bullet hit its owner
            }

            if (other.getType().equals("enemyShip") || other.getType().equals("asteroid")) {
                world.removeEntity(bullet);
                world.removeEntity(other);

                if (other.getType().equals("asteroid")) {
                    Main.incrementDestroyedAsteroids(); // Increase the counter when an asteroid is destroyed
                }

                System.out.println("Bullet hit " + other.getType() + ", both destroyed.");
            }
        }
    }
}
