package com.mukbert.framework;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter
{
	private boolean[] isKeyPressed;
	
	public Keyboard()
	{
		isKeyPressed = new boolean[1024];
	}
	
	public void keyPressed(java.awt.event.KeyEvent e) 
	{
		int code = e.getKeyCode();
		
		if(code >= 0 && code < isKeyPressed.length)
		{
			isKeyPressed[code] = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{
		int code = e.getKeyCode();
		
		if(code >= 0 && code < isKeyPressed.length)
		{
			isKeyPressed[code] = false;
		}
	}
	
	public boolean isKeyDown(int code)
	{
		if(code >= 0 && code < isKeyPressed.length)
		{
			return isKeyPressed[code];
		}
		else return false;
	}
}
