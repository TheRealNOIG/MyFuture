package com.tylersjunk.Game.Core;

import com.tylersjunk.engine.Level.LevelManager;
import com.tylersjunk.engine.entities.Entity;
import com.tylersjunk.engine.entities.Light;
import com.tylersjunk.engine.models.RawModel;
import com.tylersjunk.engine.models.TexturedModel;
import com.tylersjunk.engine.renderEngine.*;
import com.tylersjunk.engine.textures.ModelTexture;
import com.tylersjunk.engine.toolbox.KeyboardHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;


public class Main {

    public static final int WIDTH = 1080;
    public static final int HEIGHT = WIDTH / 9 * 6;

    public static void main(String[] args)
    {
        DisplayManager.createDisplay(WIDTH, HEIGHT);

        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer(75, 2000);

        Camera camera = new Camera(0, 0, new Vector3f(0, 15, 10));
        Light light = new Light(new Vector3f(10, 5, 0), new Vector3f(1, 1, 1));

        LevelManager levelManager = new LevelManager(loader, renderer);
        levelManager.LoadLevel("leveltest");

        while(!Display.isCloseRequested())
        {
            //Game logic here
            KeyboardHelper.getKeyboard().update();
            camera.update();

            levelManager.FindDynamicEntityByName("moveDragon").increaseRotation(0, 1, 0);

            //Render here
            levelManager.RenderCurrentLevel();

            renderer.render(camera, light);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
