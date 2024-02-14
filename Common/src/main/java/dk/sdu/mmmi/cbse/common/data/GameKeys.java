package dk.sdu.mmmi.cbse.common.data;

public class GameKeys {

    private static boolean[] keys;
    private static boolean[] pkeys;

    // Increase the number of keys to accommodate A, D, and W
    private static final int NUM_KEYS = 4; // Adjusted for UP, LEFT, RIGHT, SPACE, A, D, W
    public static final int W = 0;
    public static final int A = 1;
    public static final int D = 2;
    public static final int SPACE = 3;

    public GameKeys() {
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }

    public void update() {
        System.arraycopy(keys, 0, pkeys, 0, NUM_KEYS);
    }

    public void setKey(int k, boolean b) {
        if(k >= 0 && k < NUM_KEYS) {
            keys[k] = b;
        }
    }

    public boolean isDown(int k) {
        if(k >= 0 && k < NUM_KEYS) {
            return keys[k];
        }
        return false;
    }

    public boolean isPressed(int k) {
        if(k >= 0 && k < NUM_KEYS) {
            return keys[k] && !pkeys[k];
        }
        return false;
    }
}
