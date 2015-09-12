package com.mukbert.framework;

import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;

public class EntityAnimation extends EntityImage
{
	private Animation animation;
	
	public EntityAnimation(Class<? extends RectangularShape> shape) 
	{
		super(shape);
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		if(animation == null || !isAlive()) return;
		
		setBufferedImage(animation.getImage());
		super.draw(g);
	}
	
	public Animation getAnimation()
	{
		return animation;
	}
	
	public void setAnimation(Animation animation)
	{
		this.animation = animation;
	}
}
