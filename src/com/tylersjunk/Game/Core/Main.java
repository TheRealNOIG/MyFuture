package com.tylersjunk.Game.Core;

import com.tylersjunk.engine.entities.Entity;
import com.tylersjunk.engine.entities.Light;
import com.tylersjunk.engine.models.RawModel;
import com.tylersjunk.engine.models.TexturedModel;
import com.tylersjunk.engine.renderEngine.*;
import com.tylersjunk.engine.textures.ModelTexture;
import com.tylersjunk.engine.toolbox.KeyboardHelper;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by Tyler on 10/23/2016.
 */
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

        ModelData data = OBJLoader.loadOBJ("dragon");
        RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
        TexturedModel dragonModle = new TexturedModel(model, new ModelTexture(loader.loadTexture("dragonTexture")));
        dragonModle.getTexture().setReflectivity(1);
        dragonModle.getTexture().setShineDamper(10);

        Entity dragon1 = new Entity(dragonModle, new Vector3f(0, 10, -14), 0, 0, 0, 1);
        Entity dragon2 = new Entity(dragonModle, new Vector3f(0, 15, -14), 0, 0, 0, 1);

        while(!Display.isCloseRequested())
        {
            //Game logic here
            KeyboardHelper.getKeyboard().update();
            camera.update();

            dragon1.setRotY(dragon1.getRotY() + 1);

            //Render here
            renderer.processEntity(dragon1);
            renderer.processEntity(dragon2);

            renderer.render(camera, light);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
