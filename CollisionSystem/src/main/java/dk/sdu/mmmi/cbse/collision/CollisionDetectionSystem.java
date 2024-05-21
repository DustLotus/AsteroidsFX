package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.ICollisionDetectionService;

import java.util.List;

public class CollisionDetectionSystem implements ICollisionDetectionService {

    @Override
    public void process(World world) {
        List<Entity> entities = (List<Entity>) world.getEntities(); // Assuming there's a method to get all entities
        for (int i = 0; i < entities.size(); i++) {
            Entity entity1 = entities.get(i);
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity2 = entities.get(j);
                if (checkCollision(entity1, entity2)) {
                    handleCollision(entity1, entity2);
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

    private void handleCollision(Entity entity1, Entity entity2) {

        System.out.println("Collision detected between " + entity1.getID() + " and " + entity2.getID());
    }
}
