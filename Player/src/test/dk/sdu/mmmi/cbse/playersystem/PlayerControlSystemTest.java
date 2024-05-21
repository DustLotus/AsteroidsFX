package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerControlSystemTest {

    private PlayerControlSystem playerControlSystem;
    private GameData gameData;
    private World world;
    private Entity player;

    @BeforeEach
    void setUp() {
        playerControlSystem = new PlayerControlSystem();
        gameData = mock(GameData.class);

        // Correctly mock the gameData to provide display dimensions
        when(gameData.getDisplayWidth()).thenReturn(800);
        when(gameData.getDisplayHeight()).thenReturn(600);

        // Mocking GameKeys
        GameKeys keys = mock(GameKeys.class);
        when(keys.isDown(anyInt())).thenReturn(false);  // Default to no key pressed
        when(gameData.getKeys()).thenReturn(keys);

        world = new World();
        player = new Entity();
        player.setRotation(0); // Initial rotation
        player.setX(100); // Initial X position
        player.setY(100); // Initial Y position

        world.addEntity(player);
    }

    @Test
    void testHandleMovementWithLeftKey() {
        when(gameData.getKeys().isDown(GameKeys.A)).thenReturn(true);
        playerControlSystem.process(gameData, world);
        assertTrue(player.getRotation() < 0, "Rotation should decrease when moving left");
    }

    @Test
    void testHandleMovementWithRightKey() {
        when(gameData.getKeys().isDown(GameKeys.D)).thenReturn(true);
        playerControlSystem.process(gameData, world);
        assertTrue(player.getRotation() > 0, "Rotation should increase when moving right");
    }

    @Test
    void testHandleMovementWithForwardKey() {
        when(gameData.getKeys().isDown(GameKeys.W)).thenReturn(true);
        double initialX = player.getX();
        double initialY = player.getY();
        playerControlSystem.process(gameData, world);
        assertNotEquals(initialX, player.getX(), "X position should change when moving forward");
        assertNotEquals(initialY, player.getY(), "Y position should change when moving forward");
    }

    @Test
    void testEnforceScreenBounds() {
        // Test wrapping from left to right
        player.setX(-1);
        playerControlSystem.process(gameData, world);
        assertEquals(gameData.getDisplayWidth(), player.getX(), "Should wrap to right when moving beyond left edge");

        // Test wrapping from right to left
        player.setX(gameData.getDisplayWidth() + 1);
        playerControlSystem.process(gameData, world);
        assertEquals(0, player.getX(), "Should wrap to left when moving beyond right edge");

        // Test wrapping from top to bottom
        player.setY(gameData.getDisplayHeight() + 1);
        playerControlSystem.process(gameData, world);
        assertEquals(0, player.getY(), "Should wrap to bottom when moving beyond top edge");

        // Test wrapping from bottom to top
        player.setY(-1);
        playerControlSystem.process(gameData, world);
        assertEquals(gameData.getDisplayHeight(), player.getY(), "Should wrap to top when moving beyond bottom edge");
    }
}
