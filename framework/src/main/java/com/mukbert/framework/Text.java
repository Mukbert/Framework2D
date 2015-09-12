package com.mukbert.framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Text extends EntityColor
{
	public static final int CENTERX = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int CENTERY = 3;
	public static final int TOP = 4;
	public static final int BOTTOM = 5;
	
	private String text;
	private Font font;
	private int dirX;
	private int dirY;
	
	public Text() 
	{
		super(RECTANGLE);
		setOrientationX(CENTERX);
		setOrientationY(CENTERY);
		setColor(Color.black);
	}
	
	public void draw(Graphics2D g)
	{
		translate(g);
		
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D bounds = fm.getStringBounds(text, g);
		double x = 0;
		double y = 0;
		
		if(dirX == LEFT) x = 0;
		else if(dirX == RIGHT) x = getWidth() - bounds.getWidth();
		else x = getWidth() / 2 - bounds.getWidth() / 2;
		
		if(dirY == TOP) y = bounds.getHeight();
		else if(dirY == BOTTOM) y = getHeight();
		else y = getHeight() / 2 + bounds.getHeight() / 2;
		
		g.setColor(getColor());
		if(font != null)
		{
			g.setFont(font);
		}
		g.drawString(text, (float)x, (float)y);
		
		translateBack(g);
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void setText(int text)
	{
		this.text = String.valueOf(text);
	}
	
	public void setFont(Font font)
	{
		this.font = font;
	}
	
	public void setOrientationX(int i)
	{
		if(i == CENTERX || i == LEFT || i == RIGHT)
		{
			dirX = i;
		}
	}
	
	public void setOrientationY(int i)
	{
		if(i == CENTERY || i == TOP || i == BOTTOM)
		{
			dirY = i;
		}
	}
}
