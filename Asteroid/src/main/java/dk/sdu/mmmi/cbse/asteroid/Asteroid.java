package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {
    private int sizeLevel;

    public Asteroid(int sizeLevel) {
        this.sizeLevel = sizeLevel;
    }

    public int getSizeLevel() {
        return sizeLevel;
    }

    public void setSizeLevel(int sizeLevel) {
        this.sizeLevel = sizeLevel;
    }
}
