package com.tylersjunk.engine.renderEngine;

import com.tylersjunk.engine.entities.Camera;
import com.tylersjunk.engine.entities.Entity;
import com.tylersjunk.engine.entities.Light;
import com.tylersjunk.engine.entities.Terrain;
import com.tylersjunk.engine.models.TexturedModel;
import com.tylersjunk.engine.shaders.StaticShader;
import com.tylersjunk.engine.shaders.TerrainShader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

	private float FOV = 75;
	private float NEAR_PLANE = 0.1f;
	private float FAR_PLANE = 100;
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	public MasterRenderer(float FOV, float nearPlane, float farPlane)
	{
		this.setFOV(FOV);
		this.setNEAR_PLANE(nearPlane);
		this.setFAR_PLANE(farPlane);
		this.createProjectionMatrix();
		setupRenderers();
	}
	
	public MasterRenderer(float FOV, float farPlane)
	{
		this.setFOV(FOV);
		this.setFAR_PLANE(farPlane);
		this.createProjectionMatrix();
		setupRenderers();
	}
	
	public MasterRenderer(float FOV)
	{
		this.setFOV(FOV);
		this.createProjectionMatrix();
		setupRenderers();
	}
	
	private void setupRenderers()
	{
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
	}
	
	public void render(Camera camera, Light light)
	{
		prepare();
        shader.start();
        shader.loadLight(light);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        terrainShader.start();
        terrainShader.loadLight(light);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();
        entities.clear();
	}
	
	public void processTerrain(Terrain terrain)
	{
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity)
	{
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null)
		{
			batch.add(entity);
		}
		else
		{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public void prepare()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT| GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.5f, 0.8f, 1, 1);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public void cleanUp()
	{
		shader.cleanUp();
		terrainShader.cleanUp();
	}
	
	private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
        System.err.println(projectionMatrix);
    }

	public void setFOV(float fOV) {
		FOV = fOV;
	}

	public void setNEAR_PLANE(float nEAR_PLANE) {
		NEAR_PLANE = nEAR_PLANE;
	}

	public void setFAR_PLANE(float fAR_PLANE) {
		FAR_PLANE = fAR_PLANE;
	}
}
