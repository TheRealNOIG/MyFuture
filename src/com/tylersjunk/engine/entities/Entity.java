package com.tylersjunk.engine.entities;

import com.tylersjunk.engine.models.TexturedModel;
import com.tylersjunk.engine.physics.AABB;
import org.lwjgl.util.vector.Vector3f;

public class Entity {

	private TexturedModel model;
	private Vector3f position;
	private float rotX,rotY,rotZ,scale;
	public AABB bb;

	public String uniqueName;
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super();
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}

	public Entity(String uniqueName, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super();
		this.uniqueName = uniqueName;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}

	public Entity(Entity entity)
	{
		if(entity.uniqueName != null)
			this.uniqueName = entity.uniqueName;
		this.model = entity.model;
		this.position = entity.position;
		this.rotX = entity.rotX;
		this.rotY = entity.rotY;
		this.rotZ = entity.rotZ;
		this.scale = entity.scale;
	}
	
	public void increasePosition(float dx, float dy, float dz)
	{
		this.position.x += dx;
		this.position.y += dy;
		this.position.z += dz;
	}
	public void setPosition(float dx, float dy, float dz)
	{
		this.position.x = dx;
		this.position.y = dy;
		this.position.z = dz;
	}
	
	public void increaseRotation(float dx, float dy, float dz)
	{
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}
	public void setRotation(float dx, float dy, float dz)
	{
		this.rotX = dx;
		this.rotY = dy;
		this.rotZ = dz;
	}


	public TexturedModel getModel() {
		return model;
	}


	public void setModel(TexturedModel model) {
		this.model = model;
	}


	public Vector3f getPosition() {
		return position;
	}


	public void setPosition(Vector3f position) {
		this.position = position;
	}


	public float getRotX() {
		return rotX;
	}


	public void setRotX(float rotX) {
		this.rotX = rotX;
	}


	public float getRotY() {
		return rotY;
	}


	public void setRotY(float rotY) {
		this.rotY = rotY;
	}


	public float getRotZ() {
		return rotZ;
	}


	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}


	public float getScale() {
		return scale;
	}


	public void setScale(float scale) {
		this.scale = scale;
	}
}
