package com.mukbert.framework;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Panel extends JPanel
{
	private Framework framework;
	
	public Panel(int width, int height) 
	{
		Dimension size = new Dimension(width, height);
		
		setIgnoreRepaint(true);
		setPreferredSize(size);
		setSize(size);
		setDoubleBuffered(false);
		setFocusable(true);
	}
	
	public void update()
	{
	}
	
	@Override
	public void addNotify() 
	{
		super.addNotify();
		
		addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent evt)
		    {
		        framework.createBuffer();
		        framework.resize(getWidth(), getHeight());
		    }
		});
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		try 
		{
			framework.draw((Graphics2D) g);
		} 
		catch (NullPointerException e) 
		{
			System.err.println("NullPointerException");
		}
	}
	
	public void setFramework(Framework framework)
	{
		this.framework = framework;
	}
	
	public void setMouse(Mouse mouse)
	{
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public void setKeyboard(Keyboard keyboard)
	{
		addKeyListener(keyboard);
	}
}
