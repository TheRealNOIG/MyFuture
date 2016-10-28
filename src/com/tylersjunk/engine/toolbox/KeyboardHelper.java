package com.tylersjunk.engine.toolbox;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

public class KeyboardHelper {
	
	private static final KeyboardHelper KEYBOARD = new KeyboardHelper();
	
	private List<Integer> keysDown = new ArrayList<Integer>();
	private List<Integer> keysUp = new ArrayList<Integer>();
	private List<Integer> lastKeysDown = new ArrayList<Integer>();
	
	private boolean enabled = true;
	
	public void update()
	{
		if(!Display.isActive())
		{
			return;
		}
		while(Keyboard.next())
		{
			int key = Keyboard.getEventKey();
			if(Keyboard.getEventKeyState())
			{
				keysDown.add(key);
			}else
			{
				keysUp.add(key);
				keysDown.remove((Integer)key);
			}
		}
	}
	
	public boolean isKeyDown(int key)
	{
		if(keysDown.contains(key))
		{
			return enabled;
		}else
		{
			return false;
		}
	}
	
	public boolean isKeyUp(int key)
	{
		if(keysUp.contains(key))
		{
			keysUp.clear();
			return enabled;
		}else
		{
			return false;
		}
	}
	
	public boolean isKeyPressed(int key)
	{
		//NOT WORKING RIGHT NOW
		return false;
		/*
		if(keysUp.contains(key))
		{
			lastKeysDown.clear();
		}
		if(keysDown.contains(key) && !lastKeysDown.contains(key))
		{
			lastKeysDown = keysDown;
			return enabled;
		}else
		{
			return false;
		}*/
	}
	
	public void setEnabled(boolean bool)
	{
		this.enabled = bool;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public static KeyboardHelper getKeyboard()
	{
		return KEYBOARD;
	}
}
