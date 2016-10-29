package com.tylersjunk.engine.models;

import com.tylersjunk.engine.textures.ModelTexture;

public class TexturedModel {

	public String name;

	private RawModel rawModel;
	private ModelTexture texture;
	
	public TexturedModel(RawModel model, ModelTexture texture)
	{
		this.rawModel = model;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}
}
