package com.tylersjunk.engine.physics;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class AABB {

	public Vector3f min, max;
	

	public AABB(Vector3f min, Vector3f max) {
		this.min = min;
		this.max = max;
	}
	
	public void setAABB(Vector3f min, Vector3f max) {
		this.min = min;
		this.max = max;
	}
	
	public void moveAABB(float xa, float ya, float za) {
		this.min = new Vector3f(this.min.x + xa, this.min.y + ya, this.min.z + za);
		this.max = new Vector3f(this.max.x + xa, this.max.y + ya, this.max.z + za);
	}
	
	public boolean intersect(AABB b) 
    {
    	if(this.min.x <= b.max.x && this.max.x >= b.min.x && this.min.y <= b.max.y && this.max.y >= b.min.y &&
    			this.min.z <= b.max.z && this.max.z >= b.min.z)
    	{
    		return true;
    	}
    	return false;
    }
	
	public Vector2f test(AABB other)
	{
		Vector2f reply = new Vector2f(this.min.x - other.max.x, this.max.x - other.min.x);
		return reply;
	}
	
	public Vector3f max(Vector3f a, Vector3f b)
	{
		  float x;
		  float y;
		  float z;
		  if(b.getX() > a.getX())x = b.getX(); else x = a.getX();
		  if(b.getY() > a.getY())y = b.getY(); else y = a.getY();
		  if(b.getZ() > a.getZ())z = b.getZ(); else z = a.getZ();
		  
		  return new Vector3f(x, y, z);
	}
	
	public float clipXCollide(AABB c, float xa) {
        float max;
        if (c.max.y <= this.min.y || c.min.y >= this.max.y) {
            return xa;
        }
        if (c.max.z <= this.min.z || c.min.z >= this.max.z) {
            return xa;
        }
        if (xa > 0.0f && c.max.x <= this.min.x && (max = this.min.x - c.max.x) < xa) {
            xa = max;
        }
        if (xa < 0.0f && c.min.x >= this.max.x && (max = this.max.x - c.min.x) > xa) {
            xa = max;
        }
        return xa;
    }

    public float clipYCollide(AABB c, float ya) {
        float max;
        if (c.max.x <= this.min.x || c.min.x >= this.max.x) {
            return ya;
        }
        if (c.max.z <= this.min.z || c.min.z >= this.max.z) {
            return ya;
        }
        if (ya > 0.0f && c.max.y <= this.min.y && (max = this.min.y - c.max.y) < ya) {
            ya = max;
        }
        if (ya < 0.0f && c.min.y >= this.max.y && (max = this.max.y - c.min.y) > ya) {
            ya = max;
        }
        return ya;
    }

    public float clipZCollide(AABB c, float za) {
        float max;
        if (c.max.x <= this.min.x || c.min.x >= this.max.x) {
            return za;
        }
        if (c.max.y <= this.min.y || c.min.y >= this.max.y) {
            return za;
        }
        if (za > 0.0f && c.max.z <= this.min.z && (max = this.min.z - c.max.z) < za) {
            za = max;
        }
        if (za < 0.0f && c.min.z >= this.max.z && (max = this.max.z - c.min.z) > za) {
            za = max;
        }
        return za;
    }
    
    public AABB expand() {
        return new AABB(new Vector3f(this.min.x - 1.5f, this.min.y - 1.5f, this.min.z - 1.5f), new Vector3f(this.max.x + 1.5f, this.max.y + 1.5f, this.max.z + 1.5f));
    }
}
