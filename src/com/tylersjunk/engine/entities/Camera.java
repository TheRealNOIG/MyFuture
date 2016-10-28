package com.tylersjunk.engine.entities;

import com.tylersjunk.engine.physics.AABB;
import org.lwjgl.util.vector.Vector3f;

public abstract class Camera {

	protected Vector3f position = new Vector3f(0, 0, 10);
	protected float pitch, yaw, roll;
	
	public AABB bb;
	protected float bbWidth;
	protected float bbHeight;
	
	protected float vx, vy, vz;
	
	public Camera(float boundingBoxWidth, float boundingBoxHeight, Vector3f position)
	{
		this.position = position;
		this.bb = new AABB(new Vector3f(position.x - boundingBoxWidth, position.y - boundingBoxHeight, position.z - boundingBoxWidth), new Vector3f(position.x + boundingBoxWidth, position.y, position.z + boundingBoxWidth));
		this.bbWidth = boundingBoxWidth;
		this.bbHeight = boundingBoxHeight;
	}
	
	protected void update()
	{
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
}
