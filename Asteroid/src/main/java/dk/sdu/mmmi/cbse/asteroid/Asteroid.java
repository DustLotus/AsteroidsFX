package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {
    private int size; // Large = 3, Medium = 2, Small = 1

    public Asteroid(int size) {
        this.size = size;
    }
}
