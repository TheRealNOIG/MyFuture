package com.tylersjunk.engine.Level;

import com.tylersjunk.engine.entities.Entity;
import com.tylersjunk.engine.models.TexturedModel;
import com.tylersjunk.engine.models.TexturedModelLoader;
import com.tylersjunk.engine.renderEngine.Loader;
import com.tylersjunk.engine.renderEngine.MasterRenderer;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    Loader loader;
    MasterRenderer renderer;
    TexturedModelLoader models;

    public List<Entity> entities = new ArrayList<Entity>();

    public LevelManager(Loader loader, MasterRenderer renderer)
    {
        this.loader = loader;
        this.renderer = renderer;
        this.models = new TexturedModelLoader(loader);
    }

    public void LoadLevel(String levelFileName)
    {
        models.texturedModelsList.clear();

        FileReader isr = null;
        File levelFile = new File("res/" + levelFileName + ".lvl");

        try {
            isr = new FileReader(levelFile);
        } catch (FileNotFoundException e) {
            System.err.println("File not found in res; don't use any extention");
        }

        BufferedReader reader = new BufferedReader(isr);
        String line;

        try
        {
            while (true) {
                line = reader.readLine();
                if (line.startsWith("l ")) {
                    String[] currentLine = line.split(" ");
                    models.loadTexturedModel(String.valueOf(currentLine[1]),
                            String.valueOf(currentLine[2]),
                            String.valueOf(currentLine[3]));
                    try
                    {
                        TexturedModel model = models.findModelByName(currentLine[3]);
                        model.getTexture().setReflectivity(Float.valueOf(currentLine[4]));
                        model.getTexture().setShineDamper(Float.valueOf(currentLine[5]));
                    } catch(Exception e) {}
                } else if (line.startsWith("e ")) {
                    String[] currentLine = line.split(" ");
                    entities.add(new Entity(models.findModelByName(currentLine[1]),
                                                new Vector3f(Float.valueOf(currentLine[2]), Float.valueOf(currentLine[3]), Float.valueOf(currentLine[4])),
                                                    Float.valueOf(currentLine[5]), Float.valueOf(currentLine[6]), Float.valueOf(currentLine[7]), Float.valueOf(currentLine[8])));
                } else if(line.startsWith("end"))
                {
                    break;
                }
            }
            reader.close();
        }catch (IOException e) {
            System.err.println("Error reading the file");
        }
    }

    public void RenderCurrentLevel()
    {
        for(Entity entity: entities)
        {
            renderer.processEntity(entity);
        }
    }
}