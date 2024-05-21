package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionDetectionSystemTest {

    private CollisionDetectionSystem collisionDetectionSystem;
    private World world;
    private Entity bullet;
    private Entity asteroid;
    private Entity enemyShip;

    @BeforeEach
    void setUp() {
        collisionDetectionSystem = new CollisionDetectionSystem();
        world = new World();

        bullet = new Entity();
        bullet.setType("bullet");
        bullet.setX(100);
        bullet.setY(100);
        bullet.setSize(5);

        asteroid = new Entity();
        asteroid.setType("asteroid");
        asteroid.setX(105);
        asteroid.setY(105);
        asteroid.setSize(20);

        enemyShip = new Entity();
        enemyShip.setType("enemyShip");
        enemyShip.setX(200);
        enemyShip.setY(200);
        enemyShip.setSize(20);

        world.addEntity(bullet);
        world.addEntity(asteroid);
        world.addEntity(enemyShip);
    }

    @Test
    void testBulletAsteroidCollision() {
        collisionDetectionSystem.process(world);
        assertNull(world.getEntity(bullet.getID()), "Bullet should be removed after collision with asteroid.");
        assertNull(world.getEntity(asteroid.getID()), "Asteroid should be removed after collision with bullet.");
    }

    @Test
    void testBulletEnemyShipCollision() {
        bullet.setX(200);
        bullet.setY(200);
        collisionDetectionSystem.process(world);
        assertNull(world.getEntity(bullet.getID()), "Bullet should be removed after collision with enemy ship.");
        assertNull(world.getEntity(enemyShip.getID()), "Enemy ship should be removed after collision with bullet.");
    }

    @Test
    void testBulletMiss() {
        bullet.setX(400);
        bullet.setY(400);
        collisionDetectionSystem.process(world);
        assertNotNull(world.getEntity(bullet.getID()), "Bullet should not be removed if it doesn't collide.");
        assertNotNull(world.getEntity(asteroid.getID()), "Asteroid should not be removed if it doesn't collide.");
        assertNotNull(world.getEntity(enemyShip.getID()), "Enemy ship should not be removed if it doesn't collide.");
    }

    @Test
    void testNoCollisionBetweenNonBullets() {
        asteroid.setX(250);
        asteroid.setY(250);
        collisionDetectionSystem.process(world);
        assertNotNull(world.getEntity(asteroid.getID()), "Asteroid should not be removed if it doesn't collide with a bullet.");
        assertNotNull(world.getEntity(enemyShip.getID()), "Enemy ship should not be removed if it doesn't collide with a bullet.");
    }
}
