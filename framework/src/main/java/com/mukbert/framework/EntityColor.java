package com.mukbert.framework;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;

public class EntityColor extends EntityBasic
{
	private Color color;
	
	public EntityColor(Class<? extends RectangularShape> shape) 
	{
		super(shape);
		
		color = Color.black;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void draw(Graphics2D g)
	{		
		g.setColor(color);
		g.fill(getBounds());
	}
}
