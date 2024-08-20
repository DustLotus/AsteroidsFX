package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.collision.CollisionDetectionSystem;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.ICollisionDetectionService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

public class Main extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();

    private final Pane gameWindow = new Pane();

    private ICollisionDetectionService collisionService;

    private final Map<String, IGamePluginService> activePlugins = new HashMap<>();

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage window) throws Exception {
        Text text = new Text(10, 20, "Destroyed asteroids: 0");
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(text);

        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.A)) {
                gameData.getKeys().setKey(GameKeys.A, true);
            }
            if (event.getCode().equals(KeyCode.D)) {
                gameData.getKeys().setKey(GameKeys.D, true);
            }
            if (event.getCode().equals(KeyCode.W)) {
                gameData.getKeys().setKey(GameKeys.W, true);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
            if (event.getCode().equals(KeyCode.O)) {
                toggleModule("EnemyPlugin");
            }
            if (event.getCode().equals(KeyCode.P)) {
                toggleModule("AsteroidPlugin");
            }
        });
        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.A)) {
                gameData.getKeys().setKey(GameKeys.A, false);
            }
            if (event.getCode().equals(KeyCode.D)) {
                gameData.getKeys().setKey(GameKeys.D, false);
            }
            if (event.getCode().equals(KeyCode.W)) {
                gameData.getKeys().setKey(GameKeys.W, false);
            }
            if (event.getCode().equals(KeyCode.SPACE)) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
        });

        collisionService = new CollisionDetectionSystem();  // Initialize the collision service once here

        // Load and start all game plugins (modules)
        loadAndStartModules();

        // Create polygons for all entities and add them to the game window
        for (Entity entity : world.getEntities()) {
            Polygon polygon = new Polygon(entity.getPolygonCoordinates());
            polygons.put(entity, polygon);
            gameWindow.getChildren().add(polygon);
        }

        // Start game loop
        render();

        // Show the game window
        window.setScene(scene);
        window.setTitle("ASTEROIDS");
        window.show();
    }

    private void render() {
        new AnimationTimer() {
            private long then = System.nanoTime();

            @Override
            public void handle(long now) {
                float delta = (now - then) / 1_000_000_000.0f; // Calculate delta in seconds
                then = now; // Update then to the current time for the next frame
                gameData.setDelta(delta); // Set the calculated delta in GameData

                update(); // Process game logic updates
                draw(); // Render the updated game state
                gameData.getKeys().update(); // Optionally update the state of keys or other input if necessary
            }
        }.start();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        // Perform collision detection and handling
        collisionService.process(world);

        // Ensure plugins are processing their logic
        for (IGamePluginService gamePlugin : activePlugins.values()) {
            gamePlugin.process(gameData, world);
        }

        // Ensure polygons are in sync with entities
        world.getEntities().forEach(entity -> {
            if (!polygons.containsKey(entity)) {
                Polygon polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
        });

        // Remove polygons of removed entities
        polygons.keySet().removeIf(entity -> {
            if (!world.getEntities().contains(entity)) {
                gameWindow.getChildren().remove(polygons.get(entity));
                return true;
            }
            return false;
        });
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    private void loadAndStartModules() {
        ServiceLoader<IGamePluginService> plugins = ServiceLoader.load(IGamePluginService.class);
        for (IGamePluginService plugin : plugins) {
            plugin.start(gameData, world);
            activePlugins.put(plugin.getClass().getSimpleName(), plugin);
        }
    }

    private void toggleModule(String pluginName) {
        IGamePluginService plugin = activePlugins.get(pluginName);
        if (plugin != null) {
            plugin.stop(gameData, world);
            activePlugins.remove(pluginName);
            System.out.println(pluginName + " module stopped.");
        } else {
            ServiceLoader<IGamePluginService> plugins = ServiceLoader.load(IGamePluginService.class);
            for (IGamePluginService loadedPlugin : plugins) {
                if (loadedPlugin.getClass().getSimpleName().equals(pluginName)) {
                    loadedPlugin.start(gameData, world);
                    activePlugins.put(pluginName, loadedPlugin);
                    System.out.println(pluginName + " module started.");
                    break;
                }
            }
        }
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
