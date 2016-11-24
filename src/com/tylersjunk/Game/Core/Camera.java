package com.tylersjunk.Game.Core;

import com.tylersjunk.engine.entities.Terrain;
import com.tylersjunk.engine.renderEngine.DisplayManager;
import com.tylersjunk.engine.toolbox.KeyboardHelper;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;


public class Camera extends com.tylersjunk.engine.entities.Camera {

    private float movementSpeed = 50f;
    private float sideMovementSpeed = 40f;
    private float jumpIntensity = 100;
    private static final float GRAVITY = 7f;

    private float currentSpeed;
    private float sideCurrentSpeed;
    private float verticalCurrentSpeed;

    private float player_height = 13.5f;
    private boolean isGrounded;

    public Camera(float boundingBoxWidth, float boundingBoxHeight, Vector3f position) {
        super(boundingBoxWidth, boundingBoxHeight, position);
        Mouse.setGrabbed(true);
        Mouse.enabled = true;
    }

    @Override
    protected void update(Terrain terrain)
    {
        handleInput(terrain);
    }

    private void handleInput(Terrain terrain)
    {
        if(Mouse.enabled)
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
        if(KeyboardHelper.getKeyboard().isKeyUp(Keyboard.KEY_ESCAPE))
        {
            Mouse.enabled = !Mouse.enabled;
            Mouse.setGrabbed(Mouse.enabled);
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
        if(KeyboardHelper.getKeyboard().isKeyDown(Keyboard.KEY_SPACE) && isGrounded)
        {
            verticalCurrentSpeed = jumpIntensity;
        } else
        {
            verticalCurrentSpeed -= GRAVITY;
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

        Vector3f updatePosition = new Vector3f(this.position.getX() + vx, this.position.y + (verticalCurrentSpeed * DisplayManager.getFramTimeSecounds()), this.position.getZ() + vz);

        float terrainHeight = terrain.getHeightOfTerrain(updatePosition.x, updatePosition.z);
        if(updatePosition.y < terrainHeight + player_height)
        {
            isGrounded = true;
            verticalCurrentSpeed = 0;
            updatePosition.y = terrainHeight + player_height;
        }else
        {
            isGrounded = false;
        }

        this.position = updatePosition;
    }
}
