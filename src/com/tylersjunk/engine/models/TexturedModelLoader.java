package com.tylersjunk.engine.models;

import com.tylersjunk.engine.renderEngine.Loader;
import com.tylersjunk.engine.renderEngine.ModelData;
import com.tylersjunk.engine.renderEngine.OBJLoader;
import com.tylersjunk.engine.textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;

public class TexturedModelLoader{

    Loader loader;
    List<TexturedModel> texturedModelsList = new ArrayList<TexturedModel>();

    public TexturedModelLoader(Loader loader)
    {
        this.loader = loader;
    }

    public TexturedModel loadTexturedModel(String modelName, String modelTexture)
    {
        ModelData data = OBJLoader.loadOBJ(modelName);
        RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
        TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture(modelTexture)));

        texturedModelsList.add(texturedModel);
        return texturedModel;
    }
}
