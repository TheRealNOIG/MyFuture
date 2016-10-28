package com.tylersjunk.engine.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

public class DisplayManager {

	private static int WIDTH = 720;
	private static int HEIGHT = 720;
	private static final int FPS_CAP = 60;
	
	private static long lastFrameTime;
	private static float delta;
	
	
	public static void createDisplay(int width, int height) 
	{
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		WIDTH = width;
		HEIGHT = height;
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("lwjgl");
		} catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay() 
	{
		Display.sync(FPS_CAP);
		Display.update();
		long currentFramTime = getCurrentTime();
		delta = (currentFramTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFramTime;
	}
	
	public static float getFramTimeSecounds()
	{
		return delta;
	}
	
	public static void closeDisplay()
	{
		Display.destroy();
	}
	
	private static long getCurrentTime()
	{
		return Sys.getTime()*1000/ Sys.getTimerResolution();
	}
	
}
