package com.tylersjunk.Game.Enemy;

import com.tylersjunk.engine.entities.Entity;
import com.tylersjunk.engine.models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Tyler on 4/30/2017.
 */
public class TestEnemy extends Entity {

    public TestEnemy(Entity entity) {
        super(entity);
    }

    public void update()
    {
        this.increasePosition(1, 0, 0);
    }
}
