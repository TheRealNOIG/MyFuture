package com.tylersjunk.Game.Core;

import com.tylersjunk.engine.toolbox.KeyboardHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Tyler on 10/23/2016.
 */
public class Camera extends com.tylersjunk.engine.entities.Camera {

    public Camera(float boundingBoxWidth, float boundingBoxHeight, Vector3f position) {
        super(boundingBoxWidth, boundingBoxHeight, position);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void update()
    {
        handleInput();
    }

    private void handleInput()
    {
        KeyboardHelper.getKeyboard().update();
        if(KeyboardHelper.getKeyboard().isKeyDown(Keyboard.KEY_W))
            System.err.println("Down");
        if(KeyboardHelper.getKeyboard().isKeyUp(Keyboard.KEY_S))
            System.err.println("Up");
    }
}
