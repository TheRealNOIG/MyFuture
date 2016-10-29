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

    public TexturedModel loadTexturedModel(String modelFileName, String modelTexture, String modelName)
    {
        ModelData data = OBJLoader.loadOBJ(modelFileName);
        RawModel model = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getIndices());
        TexturedModel texturedModel = new TexturedModel(model, new ModelTexture(loader.loadTexture(modelTexture)));
        texturedModel.name = modelName;

        texturedModelsList.add(texturedModel);
        return texturedModel;
    }

    public TexturedModel findModelByName(String modelName)
    {
        for (TexturedModel model: texturedModelsList) {
            if(model.name == modelName)
                return model;
        }
        return null;
    }
}
