package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID(); // Unique identifier for each entity

    private double[] polygonCoordinates; // Stores the shape of the entity in a coordinate system
    private double x; // X-coordinate of the entity's position
    private double y; // Y-coordinate of the entity's position
    private double rotation; // Rotation angle of the entity
    private double speed; // Movement speed of the entity
    private float size; // Size of the entity, can be used for rendering and collision detection
    private Map<String, Double> timers = new HashMap<>(); // Stores various timers for the entity

    // Returns the string representation of the entity's UUID
    public String getID() {
        return ID.toString();
    }

    // Sets the shape of the entity defined by an array of coordinates
    public void setPolygonCoordinates(double... coordinates) {
        this.polygonCoordinates = coordinates;
    }

    // Returns the shape coordinates of the entity
    public double[] getPolygonCoordinates() {
        return polygonCoordinates;
    }

    // Sets the X-coordinate of the entity's position
    public void setX(double x) {
        this.x = x;
    }

    // Returns the X-coordinate of the entity's position
    public double getX() {
        return x;
    }

    // Sets the Y-coordinate of the entity's position
    public void setY(double y) {
        this.y = y;
    }

    // Returns the Y-coordinate of the entity's position
    public double getY() {
        return y;
    }

    // Sets the rotation angle of the entity
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    // Returns the rotation angle of the entity
    public double getRotation() {
        return rotation;
    }

    // Sets the speed of the entity
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // Returns the speed of the entity
    public double getSpeed() {
        return speed;
    }

    // Sets the size of the entity
    public void setSize(float size) {
        this.size = size;
    }

    // Returns the size of the entity
    public float getSize() {
        return size;
    }

    // Sets a timer with a specific key
    public void setTimer(String key, double value) {
        timers.put(key, value);
    }

    // Gets a timer by key, returns 0 if timer is not set
    public double getTimer(String key) {
        return timers.getOrDefault(key, 0.0);
    }

    // Updates a timer by reducing it with the given delta, handles automatic timer expiration
    public void updateTimer(String key, double delta) {
        double timeLeft = getTimer(key);
        timeLeft = Math.max(0, timeLeft - delta);
        timers.put(key, timeLeft);
    }
}
