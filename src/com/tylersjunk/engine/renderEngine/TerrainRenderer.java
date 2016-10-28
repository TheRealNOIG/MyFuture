package com.tylersjunk.engine.renderEngine;

import com.tylersjunk.engine.entities.Terrain;
import com.tylersjunk.engine.models.RawModel;
import com.tylersjunk.engine.shaders.TerrainShader;
import com.tylersjunk.engine.textures.ModelTexture;
import com.tylersjunk.engine.toolbox.Maths;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import java.util.List;

public class TerrainRenderer {

	private TerrainShader shader;
	
	public TerrainRenderer(TerrainShader shader, Matrix4f projectionMatrix)
	{
		this.shader = shader;
		this.shader.start();
		this.shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	public void render(List<Terrain> terrains)
	{
		for(Terrain terrain:terrains)
		{
			this.prepareTerrain(terrain);
			this.loadModelMatrix(terrain);
			GL11.glDrawElements(GL11.GL_TRIANGLES, terrain.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			this.unbindTerrainModel();
		}
	}
	
	private void prepareTerrain(Terrain terrain)
	{
		RawModel rawModel = terrain.getModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = terrain.getTexture();
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}
	
	private void unbindTerrainModel()
	{
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void loadModelMatrix(Terrain terrain)
	{
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(terrain.getLocation(), 0, 0, 0, 1);
		shader.loadTransformationMatrix(transformationMatrix);
	}
}
