package com.mukbert.framework;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class EntityBasic
{
	public static final Class<? extends RectangularShape> RECTANGLE = Rectangle2D.Double.class;
	public static final Class<? extends RectangularShape> ELLIPSE = Ellipse2D.Double.class;
	
	private RectangularShape bounds;
	private Point2D center;
	
	private boolean isAlive;
	
	public EntityBasic(Class<? extends RectangularShape> shape)
	{
		try 
		{
			this.bounds = shape.newInstance();
			this.center = new Point2D.Double();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		setAlive(true);
	}
	
	public void translate(Graphics2D g)
	{
		g.translate(getX(), getY());
	}
	
	public void translateBack(Graphics2D g)
	{
		g.translate(-getX(), -getY());
	}
	
	public void translateToCenter(Graphics2D g)
	{
		g.translate(getCenterX(), getCenterY());
	}
	
	public void translateFromCenter(Graphics2D g)
	{
		g.translate(-getCenterX(), -getCenterY());
	}
	
	public void moveX(double dX)
	{
		setX(bounds.getX() + dX);
	}
	
	public void moveY(double dY)
	{
		setY(bounds.getY() + dY);
	}
	
	public boolean contains(Point2D point)
	{
		return getBounds().contains(point);
	}
	
	public boolean intersects(EntityBasic entityBasic)
	{
		return entityBasic.getBounds().intersects(bounds.getBounds());
	}
	
	public void setX(double x)
	{
		bounds.setFrame(x, bounds.getY(), bounds.getWidth(), bounds.getHeight());
		center.setLocation(getCenterX(), getCenterY());
	}
	
	public void setY(double y)
	{
		bounds.setFrame(bounds.getX(), y, bounds.getWidth(), bounds.getHeight());
		center.setLocation(getCenterX(), getCenterY());
	}
	
	public void setWidth(double width)
	{
		bounds.setFrame(bounds.getX(), bounds.getY(), width, bounds.getHeight());
	}
	
	public void setHeight(double height)
	{
		bounds.setFrame(bounds.getX(), bounds.getY(), bounds.getWidth(), height);
	}
	
	public void setLocation(double x, double y)
	{
		setX(x);
		setY(y);
	}
	
	public void setLocation(EntityBasic entity)
	{
		setLocation(entity.getX(), entity.getY());
	}
	
	public void setSize(double width, double height)
	{
		setWidth(width);
		setHeight(height);
	}
	
	public void setSize(double size)
	{
		setWidth(size);
		setHeight(size);
	}
	
	public void setSize(EntityBasic entity)
	{
		setSize(entity.getWidth(), entity.getHeight());
	}
	
	public void setBounds(double x, double y, double width, double height)
	{
		setLocation(x, y);
		setSize(width, height);
	}
	
	public void setBounds(EntityBasic entity)
	{
		setBounds(entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
	}
	
	public double getX()
	{
		return bounds.getX();
	}
	
	public double getY()
	{
		return bounds.getY();
	}
	
	public double getWidth()
	{
		return bounds.getWidth();
	}
	
	public double getHeight()
	{
		return bounds.getHeight();
	}
	
	public double getCenterX()
	{
		return bounds.getCenterX();
	}
	
	public double getCenterY()
	{
		return bounds.getCenterY();
	}
	
	public double getRight()
	{
		return getX() + getWidth();
	}
	
	public double getLeft()
	{
		return getX();
	}
	
	public double getTop()
	{
		return getY();
	}
	
	public double getBottom()
	{
		return getY() + getHeight();
	}
	
	public void setAlive(boolean isAlive)
	{
		this.isAlive = isAlive;
	}
	
	public boolean isAlive()
	{
		return isAlive;
	}
	
	public Point2D getCenter()
	{
		return center;
	}
	
	public RectangularShape getBounds()
	{
		return bounds;
	}
}
