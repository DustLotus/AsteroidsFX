package dk.sdu.mmmi.cbse.common.data;

public class GameData {

    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    private final GameKeys keys = new GameKeys();
    private long lastUpdateTime; // The last update time in nanoseconds
    private float delta; // Delta time in seconds

    public GameKeys getKeys() {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }


    public void updateDelta(long currentTime) {
        // Assuming currentTime is in nanoseconds, convert to seconds
        delta = (currentTime - lastUpdateTime) / 1_000_000_000.0f;
        lastUpdateTime = currentTime;
    }

    public float getDelta() {
        return delta;
    }
}
