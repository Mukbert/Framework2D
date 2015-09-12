package com.mukbert.framework;

import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;
import java.awt.image.BufferedImage;

public class EntityImage extends EntityBasic
{
	private BufferedImage image;
	
	private boolean flipX;
	private boolean flipY;
	
	public EntityImage(Class<? extends RectangularShape> shape) 
	{
		super(shape);
	}
	
	public void draw(Graphics2D g)
	{
		if(!isAlive()) return;
		
		translateToCenter(g);
		
		double x = -getWidth()  / 2;
		double y = -getHeight() / 2;
		double w = getWidth();
		double h = getHeight();
		
		if(flipX)
		{
			x *= -1;
			w *= -1;
		}
		if(flipY)
		{
			y *= -1;
			h *= -1;
		}
		
		g.translate(x, y);
		g.drawImage(getImage(), 0, 0, (int)w, (int)h, null);
		g.translate(-x, -y);
		
		translateFromCenter(g);
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public void setBufferedImage(BufferedImage image)
	{
		this.image = image;
	}

	public void setFlipX(boolean flipX)
	{
		this.flipX = flipX;
	}

	public void setFlipY(boolean flipY)
	{
		this.flipY = flipY;
	}
	
	public boolean getFlipX()
	{
		return flipX;
	}
	
	public boolean getFlipY()
	{
		return flipY;
	}
}
