package com.mukbert.framework;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Framework  
{
	private static int FPS;
	
	private static double t;
	
	private static String font;
	private static Mouse mouse;
	private static Keyboard keyboard;
	private static Frame frame;
	private static Panel panel;
	
	private VolatileImage buffer;
	
	private long thisFrame;
	private long lastFrame;
	
	private int fps_counter;
	private double fps_time;
	private long fps_timeMax;
	
	public Framework(String title, int width, int height) 
	{		
		mouse = new Mouse();
		keyboard = new Keyboard();
		panel = new Panel(width, height);
		frame = new Frame(title);
		
				
		thisFrame = System.nanoTime();
		
		setFPS(60);
		setFont(panel.getFont().getName());
	}
	
	public final void start()
	{
		init();
		
		while(true)
		{
			panel.update();
			
			long start = System.nanoTime();
			
			update();
			draw();
			repaint();
			
			long z = System.nanoTime();
			long delay = fps_timeMax - (z - start);
			
			while(z + delay >= System.nanoTime())
			{				
				sleep((z + delay - System.nanoTime()) / 1000000L - 1);
			}
		}
	}
	
	private final void init()
	{
		panel.setFramework(this);
		panel.setMouse(mouse);
		panel.setKeyboard(keyboard);
		frame.setPanel(panel);
		this.init(getWidth(), getHeight());
		frame.setVisible(true);
	}
	
	private final void update()
	{
		lastFrame = thisFrame;
		thisFrame = System.nanoTime();
		
		t = (double)(thisFrame - lastFrame) / 1000000000;
		
		fps_time += t;
		fps_counter++;
		
		if(fps_time > 1D)
		{
			fps_time -= 1D;
			FPS = fps_counter;
			fps_counter = 0;
		}
		
		this.update(t);
	}
	
	private final void draw()
	{
		try 
		{
			if(buffer == null) createBuffer();
			
			do
			{
				int valCode = buffer.validate(panel.getGraphicsConfiguration());
				
				if(valCode == VolatileImage.IMAGE_RESTORED)
				{
//					System.err.println("Framework.draw() - DoubleBuffering - IMAGE_RESTORED");
				}
				else if(valCode == VolatileImage.IMAGE_INCOMPATIBLE)
				{
//					System.err.println("Framework.draw() - DoubleBuffering - IMAGE_INCOMPATIBLE");
					createBuffer();
				}
				
				Graphics2D g = buffer.createGraphics();
				
				panel.paint(g);
				g.dispose();
			}
			while(buffer.contentsLost());
		}
		catch (Exception e)
		{
			System.err.println("Framework.draw()");
			System.err.println(e.getMessage());
		}
	}
	
	private final void repaint()
	{
		try
		{
			Graphics g = panel.getGraphics();
			g.drawImage(buffer, 0, 0, null);
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		} 
		catch (Exception e) 
		{
			System.err.println("Framework.repaint()");
			System.err.println(e.getMessage());
		}
		
	}
	
	private final void sleep(long sleep)
	{
		if(sleep > 0)
		{
			try 
			{
				Thread.sleep(sleep);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	protected final void createBuffer()
	{
		int w = panel.getWidth();
		int h = panel.getHeight();
		
		if(w == 0 || h == 0) return;
		
		buffer = panel.getGraphicsConfiguration().createCompatibleVolatileImage(w, h);
	}
	
	public final void setFPS(int FPS)
	{
		this.fps_timeMax = 1000000000L / (long)FPS;
	}
	
	public void init(int width, int height)
	{
		
	}
	
	public void update(double t)
	{
		
	}
	
	public void draw(Graphics2D g)
	{
		
	}
	
	public void resize(int width, int height)
	{
		
	}
	
	public static final BufferedImage getImage(File file)
	{
		try 
		{
			return ImageIO.read(file);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	public static final Sound getSound(File file)
	{
		return new Sound(file);
	}
	
	public static final void setFont(String font)
	{
		Framework.font = font;
	}
	
	public static final Mouse getMouse()
	{
		return mouse;
	}
	
	public static final Keyboard getKeyboard()
	{
		return keyboard;
	}
	
	public static final String getFont()
	{
		return font;
	}
	
	public static final int getWidth()
	{
		return panel.getWidth();
	}
	
	public static final int getHeight()
	{
		return panel.getHeight();
	}
	
	public static final int getFPS()
	{
		return FPS;
	}
	
	public static final double getTimeDelta()
	{
		return t;
	}
}
