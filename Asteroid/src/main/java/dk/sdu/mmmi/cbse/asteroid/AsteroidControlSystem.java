package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.ArrayList;
import java.util.List;

public class AsteroidControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> asteroidsToRemove = new ArrayList<>();
        List<Entity> entitiesToRemove = new ArrayList<>();
        List<Entity> asteroidsToAdd = new ArrayList<>();

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            // Check if the asteroid should be destroyed (e.g., hit by another entity, such as a bullet)
            for (Entity entity : world.getEntities()) {
                if (!entity.getType().equals("asteroid") && checkCollision(asteroid, entity)) {
                    asteroidsToRemove.add(asteroid);
                    entitiesToRemove.add(entity);
                    if (((Asteroid) asteroid).getSizeLevel() > 1) {
                        asteroidsToAdd.addAll(splitAsteroid((Asteroid) asteroid, gameData));
                    }
                    break; // Exit the loop as the asteroid is already destroyed
                }
            }

            // Move the asteroid
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation())) * asteroid.getSpeed();
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation())) * asteroid.getSpeed();

            asteroid.setX(asteroid.getX() + changeX);
            asteroid.setY(asteroid.getY() + changeY);

            enforceScreenBounds(asteroid, gameData);
        }

        // Remove destroyed asteroids and other entities
        for (Entity asteroid : asteroidsToRemove) {
            world.removeEntity(asteroid);
        }
        for (Entity entity : entitiesToRemove) {
            world.removeEntity(entity);
        }

        // Add new smaller asteroids
        for (Entity newAsteroid : asteroidsToAdd) {
            world.addEntity(newAsteroid);
        }
    }

    private boolean checkCollision(Entity entity1, Entity entity2) {
        double dx = entity1.getX() - entity2.getX();
        double dy = entity1.getY() - entity2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double totalRadius = entity1.getSize() / 2 + entity2.getSize() / 2;
        return distance < totalRadius;
    }

    private List<Entity> splitAsteroid(Asteroid asteroid, GameData gameData) {
        List<Entity> newAsteroids = new ArrayList<>();
        int newSizeLevel = asteroid.getSizeLevel() - 1;

        for (int i = 0; i < 2; i++) {
            Asteroid newAsteroid = new Asteroid(newSizeLevel);
            newAsteroid.setType("asteroid");
            newAsteroid.setSize(asteroid.getSize() / 2); // New size is half of the original

            // Randomize the direction slightly
            double randomAngle = Math.random() * 360;
            newAsteroid.setRotation(randomAngle);

            // Position the new asteroid near the original one
            newAsteroid.setX(asteroid.getX() + (Math.random() - 0.5) * asteroid.getSize());
            newAsteroid.setY(asteroid.getY() + (Math.random() - 0.5) * asteroid.getSize());
            newAsteroid.setSpeed(asteroid.getSpeed() * 1.5); // New asteroids move faster

            newAsteroids.add(newAsteroid);
        }

        return newAsteroids;
    }

    private void enforceScreenBounds(Entity entity, GameData gameData) {
        if (entity.getX() < 0) {
            entity.setX(gameData.getDisplayWidth());
        }
        if (entity.getX() > gameData.getDisplayWidth()) {
            entity.setX(0);
        }
        if (entity.getY() > gameData.getDisplayHeight()) {
            entity.setY(0);
        }
        if (entity.getY() < 0) {
            entity.setY(gameData.getDisplayHeight());
        }
    }
}
