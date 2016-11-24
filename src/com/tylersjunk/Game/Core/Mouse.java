package com.tylersjunk.Game.Core;

/**
 * Created by Tyler on 11/23/2016.
 */
public class Mouse {

    public static boolean enabled;

    public static void setGrabbed(boolean bool)
    {
        org.lwjgl.input.Mouse.setGrabbed(bool);
    }

    public static int getDX()
    {
        return org.lwjgl.input.Mouse.getDX();
    }

    public static int getDY()
    {
        return org.lwjgl.input.Mouse.getDY();
    }
}
