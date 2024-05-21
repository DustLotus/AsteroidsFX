package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID(); // Unique identifier for each entity

    private double[] polygonCoordinates; // Stores the shape of the entity in a coordinate system
    private double x; // X-coordinate of the entity's position
    private double y; // Y-coordinate of the entity's position
    private double rotation; // Rotation angle of the entity
    private double speed; // Movement speed of the entity
    private float size; // Size of the entity, can be used for rendering and collision detection

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
}
