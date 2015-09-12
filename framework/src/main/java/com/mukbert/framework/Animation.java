package com.mukbert.framework;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Animation
{
	private BufferedImage[] images;
	private Integer[] indices;
	private Handler handler;
	
	private int ID;
	private double time;
	private double timeMax;
	private boolean hasFinished;
	private boolean doLoop;
	
	public Animation() 
	{
		images = new BufferedImage[0];
		indices = new Integer[0];
		doLoop = true;
	}
	
	public Animation(BufferedImage image) 
	{
		this();
		setAnimationImage(image);
	}
	
	public Animation(BufferedImage image, String order) 
	{
		this(image);
		setAnimationOrder(order);
	}
	
	public void update()
	{
		if(images.length > 1)
		{
			time += Framework.getTimeDelta();
			
			if(time >= timeMax)
			{
				time = 0;
				ID++;
			}
			
			if(ID >= indices.length)
			{
				ID = indices.length - 1;
				hasFinished = true;
				
				if(handler != null)
				{
					handler.handle(this);
				}
				if(doLoop)
				{
					reset();
				}
			}
		}
	}
	
	public boolean isEmpty()
	{
		return images.length == 0;
	}
	
	public boolean hasFinished()
	{
		boolean out = hasFinished;
		hasFinished = false;
		return out;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void reset()
	{
		time = 0;
		ID = 0;
	}
	
	public void setLoop(boolean doLoop)
	{
		this.doLoop = doLoop;
	}
	
	public void setTime(double time)
	{
		this.timeMax = time;
	}
	
	public void setImages(BufferedImage[] images)
	{
		this.images = images;
	}
	
	public void setIndices(Integer[] indices)
	{
		this.indices = indices;
	}
	
	public void setHandler(Handler handler)
	{
		this.handler = handler;
	}
	
	public Handler getHandler()
	{
		return handler;
	}
	
	public void setRandomID()
	{
		ID = new Random().nextInt(indices.length);
	}
	
	public void setAnimationOrder(String order)
	{
		indices = new Integer[order.length()];
		
		for(int i = 0; i < indices.length; i++)
		{
			indices[i] = Integer.valueOf(String.valueOf(order.charAt(i)));
		}
	}
	
	public void setAnimationImage(BufferedImage image)
	{
		if(image == null) return;
		
		int size = image.getHeight();
		int count = image.getWidth() / size;
		
		images = new BufferedImage[count];
		
		for(int i = 0; i < images.length; i++)
		{
			images[i] = image.getSubimage(size * i, 0, size, size);
		}
		
		if(indices == null || indices.length == 0)
		{
			indices = new Integer[images.length];
			
			for(int i = 0; i < indices.length; i++)
			{
				indices[i] = i;
			}
		}
	}
	
	public BufferedImage getImage()
	{
		int index;
		
		if(images == null || indices == null || ID < 0 || ID >= indices.length || (index = indices[ID]) < 0 || index >= images.length)  return null;
				
		return images[indices[ID]];
	}
	
	public Animation getClone()
	{
		Animation animation = new Animation();
		animation.setLoop(doLoop);
		animation.setImages(images);
		animation.setTime(timeMax);
		animation.setIndices(indices);
		return animation;
	}
	
	public interface AnimationInterface 
	{
		public void setAnimationImage(BufferedImage image);
		public void setAnimationTime(double time);
		public void setScale(double scale);
		public void setAnimationOrder(String string);
	}
}
