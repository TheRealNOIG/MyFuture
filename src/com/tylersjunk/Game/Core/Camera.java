package com.tylersjunk.Game.Core;

import com.tylersjunk.engine.renderEngine.DisplayManager;
import com.tylersjunk.engine.toolbox.KeyboardHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;


public class Camera extends com.tylersjunk.engine.entities.Camera {

    private boolean isMouseLooked;

    private float movementSpeed = 5f;
    private float sideMovementSpeed = 4f;
    private float verticalMovementSpeed = 0.5f;

    private float currentSpeed;
    private float sideCurrentSpeed;
    private float verticalCurrentSpeed;

    public Camera(float boundingBoxWidth, float boundingBoxHeight, Vector3f position) {
        super(boundingBoxWidth, boundingBoxHeight, position);
        Mouse.setGrabbed(true);
        isMouseLooked = true;
    }

    @Override
    protected void update()
    {
        handleInput();
    }

    private void handleInput()
    {
        if(isMouseLooked)
        {
            float movePitch = this.pitch - Mouse.getDY() / 2;
            this.yaw += Mouse.getDX() / 2;
            if(movePitch >= -90 && movePitch <= 90)
            {
                this.pitch = movePitch;
            }
        }
        if(this.yaw >= 360)
            this.yaw = this.yaw - 360;
        if(this.yaw <= 0)
            this.yaw = this.yaw + 360;

        //region KeyBoard Input
        KeyboardHelper.getKeyboard().update();
        if(KeyboardHelper.getKeyboard().isKeyDown(Keyboard.KEY_ESCAPE))
        {
            isMouseLooked = !isMouseLooked;
            Mouse.setGrabbed(isMouseLooked);
        }

        if(KeyboardHelper.getKeyboard().isKeyDown(Keyboard.KEY_W)) {
            currentSpeed = -movementSpeed;
        } else if(KeyboardHelper.getKeyboard().isKeyDown(Keyboard.KEY_S))
        {
            currentSpeed = movementSpeed;
        }else
        {
            currentSpeed = 0;
        }
        if(KeyboardHelper.getKeyboard().isKeyDown(Keyboard.KEY_D)) {
            sideCurrentSpeed = -sideMovementSpeed;
        } else if(KeyboardHelper.getKeyboard().isKeyDown(Keyboard.KEY_A))
        {
            sideCurrentSpeed = sideMovementSpeed;
        }else
        {
            sideCurrentSpeed = 0;
        }
        if(KeyboardHelper.getKeyboard().isKeyDown(Keyboard.KEY_SPACE))
        {
            verticalCurrentSpeed = verticalMovementSpeed;
        } else if(KeyboardHelper.getKeyboard().isKeyDown(Keyboard.KEY_LSHIFT))
        {
            verticalCurrentSpeed = -verticalMovementSpeed;
        }else
        {
            verticalCurrentSpeed = 0;
        }
        //endregion

        float distance = currentSpeed * DisplayManager.getFramTimeSecounds();
        float dx = (float) (distance * -(Math.sin(Math.toRadians(yaw))));
        float dz = (float) (distance * Math.cos(Math.toRadians(yaw)));
        float sideDistance = sideCurrentSpeed * DisplayManager.getFramTimeSecounds();
        float sx = (float) (sideDistance * -(Math.sin(Math.toRadians(yaw + 90))));
        float sz = (float) (sideDistance * Math.cos(Math.toRadians(yaw + 90)));

        vx = dx + sx;
        vz = dz + sz;

        this.position = new Vector3f(this.position.getX() + vx, this.position.y + verticalCurrentSpeed, this.position.getZ() + vz);
    }
}
