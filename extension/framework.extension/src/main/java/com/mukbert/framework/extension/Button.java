package com.mukbert.framework.extension;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.mukbert.framework.EntityBasic;
import com.mukbert.framework.EntityImage;
import com.mukbert.framework.Framework;
import com.mukbert.framework.Handler;

public class Button extends EntityImage
{
	private String string;
	private Color fontColor;
	private Font font;
	private Handler handler;
	private Color color;
	
	private boolean isUnselected;
	private boolean isPressed;
	private boolean isInside;
	private boolean isHandling;
	private boolean showBorder;
	
	public Button(String string, double x, double y, double width, double height)
	{
		super(EntityBasic.RECTANGLE);
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setString(string);
		setColor(Color.lightGray);
		setFontColor(Color.black);
		setShowBorder(true);
		setFont(new Font(Framework.getFont(), Font.PLAIN, (int)(getHeight() / 2)));
	}
	
	public void updateButton()
	{
		isInside = contains(Framework.getMouse().getPoint());
		
		isPressed = isInside && Framework.getMouse().isLeftDown();
		
		if(isInside)
		{
			if(Framework.getMouse().isLeftDown())
			{
				isHandling = true;
			}
			else
			{
				if(isHandling)
				{
					isHandling = false;
					
					if(handler != null)
					{
						handler.handle(this);
					}
				}
			}
		}
		else
		{
			isHandling = false;
		}
	}
	
	public void draw(Graphics2D g)
	{
		Color background = getColor();
		
		if(isPressed)
		{
			background = background.darker().darker();
		}
		else if(isInside)
		{
			background = background.darker();
		}
		else if(isUnselected)
		{
			background = background.brighter();
		}
		
		g.setColor(background);
		g.fill(getBounds());
		
		if(getImage() == null)
		{
			g.setFont(font);
			FontMetrics fm = g.getFontMetrics();
			
			double x = getX() + (getWidth() - fm.stringWidth(string)) / 2;
			double y = getY() + (getHeight() - fm.getStringBounds(string, g).getHeight()) / 2 + fm.getAscent();
			
			g.translate(x, y);
			g.setColor(fontColor);
			g.drawString(string, 0, 0);
			g.translate(-x, -y);
		}
		else
		{
			double v = (double)getImage().getWidth() / (double)getImage().getHeight();
			double h = getHeight();
			double w = v * getHeight();
			
			if(w > getWidth())
			{
				w = getWidth();
				h = w * ((double)getImage().getHeight() / (double)getImage().getWidth());
			}
			translate(g);
			
			g.drawImage(getImage(), (int)(getWidth() / 2 - w / 2), (int)(getHeight() / 2 - h / 2), (int)w, (int)h, null);
			
			translateBack(g);
		}
		
		if(showBorder)
		{
			translate(g);
			g.setColor(Color.black);
			g.drawRect(0, 0, (int)getWidth() - 1, (int)getHeight() - 1);
			translateBack(g);
		}
	}
	
	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}
	
	public String getString()
	{
		return string;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setString(String string)
	{
		this.string = string;
	}
	
	public void setFontColor(Color color)
	{
		this.fontColor = color;
	}
	
	public void setFont(Font font)
	{
		this.font = font;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void setShowBorder(boolean showBorder)
	{
		this.showBorder = showBorder;
	}
	
	public void setUnselected(boolean isUnselected)
	{
		this.isUnselected = isUnselected;
	}
}
