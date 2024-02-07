package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author corfixen
 */
public class Bullet extends Entity {
    private float lifespan = 3.0f;
    private float age = 0;

    public float getLifespan() {
        return lifespan;
    }
    public void setLifespan(float lifespan) {
        this.lifespan = lifespan;
    }
    public float getAge() {
        return age;
    }
    public void setAge(float age) {
        this.age = age;
    }
    public void update(float delta) {
        this.age += delta;
    }
    public boolean isExpired() {
        return age >= lifespan;
    }
}
