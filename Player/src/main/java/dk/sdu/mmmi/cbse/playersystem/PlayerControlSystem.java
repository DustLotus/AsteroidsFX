package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            handleMovement(gameData, player);
            handleShooting(gameData, world, player);
            enforceScreenBounds(player, gameData);
        }
    }

    private void handleMovement(GameData gameData, Entity player) {
        if (gameData.getKeys().isDown(GameKeys.A)) {
            player.setRotation(player.getRotation() - 3);
        }
        if (gameData.getKeys().isDown(GameKeys.D)) {
            player.setRotation(player.getRotation() + 3);
        }
        if (gameData.getKeys().isDown(GameKeys.W)) {
            double changeX = Math.cos(Math.toRadians(player.getRotation()));
            double changeY = Math.sin(Math.toRadians(player.getRotation()));
            player.setX(player.getX() + changeX * 2);
            player.setY(player.getY() + changeY * 2);
        }
    }

    private void handleShooting(GameData gameData, World world, Entity player) {
        if (gameData.getKeys().isDown(GameKeys.SPACE)) {
            for (BulletSPI bullet : getBulletSPIs()) {
                world.addEntity(bullet.createBullet(player, gameData));
            }
        }
    }

    private void enforceScreenBounds(Entity entity, GameData gameData) {
        if (entity.getX() < 0) {
            entity.setX(gameData.getDisplayWidth());
        } else if (entity.getX() > gameData.getDisplayWidth()) {
            entity.setX(0);
        }
        if (entity.getY() < 0) {
            entity.setY(gameData.getDisplayHeight());
        } else if (entity.getY() > gameData.getDisplayHeight()) {
            entity.setY(0);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
