package com.tylersjunk.engine.entities;

import com.tylersjunk.engine.models.RawModel;
import com.tylersjunk.engine.renderEngine.Loader;
import com.tylersjunk.engine.textures.ModelTexture;
import com.tylersjunk.engine.toolbox.Maths;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Terrain {

	private static final float SIZE = 800;
	private static final float MAX_HEIGHT = 40;
	private static final float MAX_PIXELCOLOUR = 256 * 256 *256;
	private Vector3f location;
	private RawModel model;
	private ModelTexture texture;
	private float[][] heights;

	public int gridX, gridZ;
	
	public Terrain(int gridX, int gridZ, Loader loader, ModelTexture texture, String heightMapFileName)
	{
		this.location = new Vector3f(gridX * SIZE, 0, gridZ * SIZE);
		this.gridX = gridX; this.gridZ = gridZ;
		this.texture = texture;
		this.model = generateTerrain(loader, heightMapFileName);
	}	
	
	public Vector3f getLocation() {
		return location;
	}

	public RawModel getModel() {
		return model;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	private RawModel generateTerrain(Loader loader, String heightMap){
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("res/" + heightMap + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int VERTEX_COUNT = image.getHeight();
		heights = new float[VERTEX_COUNT][VERTEX_COUNT];
		int count = VERTEX_COUNT * VERTEX_COUNT;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count*2];
		int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT-1)];
		int vertexPointer = 0;
		for(int i=0;i<VERTEX_COUNT;i++){
			for(int j=0;j<VERTEX_COUNT;j++){
				float height = getHeightFromHeightMap(j, i, image);
				vertices[vertexPointer*3] = (float)j/((float)VERTEX_COUNT - 1) * SIZE;
				vertices[vertexPointer*3+1] = height;
				heights[j][i] = height;
				vertices[vertexPointer*3+2] = (float)i/((float)VERTEX_COUNT - 1) * SIZE;
				Vector3f normal  = calculateNormalFromHeightMap(j, i, image);
				normals[vertexPointer*3] = normal.x;
				normals[vertexPointer*3+1] = normal.y;
				normals[vertexPointer*3+2] = normal.z;
				textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
				textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for(int gz=0;gz<VERTEX_COUNT-1;gz++){
			for(int gx=0;gx<VERTEX_COUNT-1;gx++){
				int topLeft = (gz*VERTEX_COUNT)+gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz+1)*VERTEX_COUNT)+gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}

	private float getHeightFromHeightMap(int x, int z, BufferedImage image)
	{
		if(x<0 | x>=image.getHeight() || z<0 || z>=image.getHeight())
		{
			return 0;
		}
		float height = image.getRGB(x, z);
		height += MAX_PIXELCOLOUR/2f;
		height /= MAX_PIXELCOLOUR/2f;
		height *= MAX_HEIGHT;
		return height;
	}

	private Vector3f calculateNormalFromHeightMap(int x, int z, BufferedImage image)
	{
		float heightL = getHeightFromHeightMap(x-1, z, image);
		float heightR = getHeightFromHeightMap(x+1, z, image);
		float heightD = getHeightFromHeightMap(x, z-1, image);
		float heightU = getHeightFromHeightMap(x, z+1, image);
		Vector3f normal = new Vector3f(heightL - heightR, 2, heightD - heightU);
		normal.normalise();
		return normal;
	}

	public float getHeightOfTerrain(float wx, float wz)
	{
		float returnValue;
		float tx = wx - this.gridX;
		float tz = wz - this.gridZ;
		float gridSquareSize = SIZE / ((float)heights.length - 1);
		int squareX = (int) Math.floor(tx / gridSquareSize);
		int squareZ = (int) Math.floor(tz / gridSquareSize);
		if(squareX >= heights.length - 1 || squareZ >= heights.length - 1 || squareX < 0 || squareZ < 0)
		{
			return 0;
		}
		float xCoord = (tx % gridSquareSize) / gridSquareSize;
		float zCoord = (tz % gridSquareSize) / gridSquareSize;
		if(xCoord <= (1-zCoord))
		{
			returnValue = Maths.barryCentric(new Vector3f(0, heights[squareX][squareZ], 0), new Vector3f(1,
							heights[squareX + 1][squareZ], 0), new Vector3f(0,
							heights[squareX][squareZ + 1], 1), new Vector2f(xCoord, zCoord));
		}else
		{
			returnValue = Maths.barryCentric(new Vector3f(1, heights[squareX + 1][squareZ], 0), new Vector3f(1,
							heights[squareX + 1][squareZ + 1], 1), new Vector3f(0,
							heights[squareX][squareZ + 1], 1), new Vector2f(xCoord, zCoord));
		}
		return returnValue;
	}
}
