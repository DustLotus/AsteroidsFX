import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Asteroids {
    requires Common;

    provides IGamePluginService with dk.sdu.mmmi.cbse.asteroids.AsteroidPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.asteroids.AsteroidControlSystem;
}