package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Emil
 */
public class Player extends Entity {
    private float fireCooldown = 0; // Time remaining until next shot is allowed
    private float fireRate = 0.5f; // Player can fire once every 0.5 seconds

    public float getFireCooldown() {
        return fireCooldown;
    }
    public void setFireCooldown(float fireCooldown) {
        this.fireCooldown = fireCooldown;
    }
    public void update(float delta) {
        if (fireCooldown > 0) {
            fireCooldown -= delta;
        }
    }
    public boolean canFire() {
        return fireCooldown <= 0;
    }
    public void shoot() {
        if (canFire()) {
            fireCooldown = fireRate;
        }
    }
}
