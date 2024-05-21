package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;

public class Asteroid extends Entity {
    private int sizeLevel;

    public Asteroid(int sizeLevel) {
        this.sizeLevel = sizeLevel;
        setSizeBasedOnLevel();
    }

    public int getSizeLevel() {
        return sizeLevel;
    }

    public void setSizeLevel(int sizeLevel) {
        this.sizeLevel = sizeLevel;
        setSizeBasedOnLevel();
    }

    private void setSizeBasedOnLevel() {
        // Sizes 30, 20, 10 for levels 3, 2, 1 respectively
        switch (sizeLevel) {
            case 3:
                setSize(30);
                break;
            case 2:
                setSize(20);
                break;
            case 1:
                setSize(10);
                break;
        }
    }
}
