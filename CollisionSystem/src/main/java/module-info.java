import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Collision {
    requires Common;
    provides dk.sdu.mmmi.cbse.common.services.ICollisionDetectionService with dk.sdu.mmmi.cbse.collision.CollisionDetectionSystem;
}
