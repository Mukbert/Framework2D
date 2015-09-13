package com.mukbert.framework.extension;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import com.mukbert.framework.EntityBasic;
import com.mukbert.framework.EntityColor;

public class Slot extends EntityColor
{
	private int posX;
	private int posY;
	
	private int count;
	private Item item;
	
	private Inventory inventory;
	
	public Slot(int posX, int posY) 
	{
		super(EntityBasic.RECTANGLE);
		setPosX(posX);
		setPosY(posY);
		setColor(Color.gray);
	}
	
	public void draw(Graphics2D g)
	{
		super.draw(g);
		int sizeW = (int) (getWidth()  / 15);
		int sizeH = (int) (getHeight() / 15);
		
		sizeW = (int)(inventory.getDiff() * 0.75);
		sizeH = (int)(inventory.getDiff() * 0.75);
		
		g.setColor(getColor().darker());
		g.fillRect((int)getX(), (int)getBottom() - sizeH, (int)getWidth(), sizeH);
		g.fillRect((int)getRight() - sizeW, (int)getY(), sizeW, (int)getHeight());
		g.setColor(getColor().brighter());
		g.fillRect((int)getX(), (int)getY(), (int)getWidth(), sizeH);
		g.fillRect((int)getX(), (int)getY(), sizeW, (int)getHeight());
		
		if(item != null)
		{
			translate(g);
			
			double d = inventory.getDiff() / 2;
			
			g.translate(d, d);
			g.drawImage(item.getImage(), 0, 0, (int)(getWidth() - inventory.getDiff()), (int)(getHeight() - inventory.getDiff()), null);
			g.translate(-d, -d);
			
			if(count > 1)
			{
				g.setFont(inventory.getFont());
				FontMetrics fm = g.getFontMetrics();
				String s = String.valueOf(count);
				
				double xString = getWidth() - fm.stringWidth(s) - inventory.getDiff() / 2;
				double yString = getHeight() - inventory.getDiff() / 2;
				
				g.setColor(Color.black);
				g.translate(xString, yString);
				g.drawString(s, 0, 0);
				g.translate(-xString, -yString);
			}				
			
			translateBack(g);
		}
	}
	
	public Item removeItem()
	{
		count--;
		
		if(count <= 0)
		{
			Item out = item;
			item = null;
			return out;
		}
		
		return item;
	}
	
	private void setPosX(int posX)
	{
		this.posX = posX;
	}
	
	private void setPosY(int posY)
	{
		this.posY = posY;
	}
	
	public void addItem(int countDiff)
	{
		this.count += countDiff;
	}
	
	public void setItem(Item item, int count)
	{
		this.item = item;
		this.count = count;
	}
	
	public boolean isEmpty()
	{
		return item == null;
	}
	
	public void setCount(int count)
	{
		this.count = count;
	}
	
	public int getPosX()
	{
		return posX;
	}
	
	public int getPosY()
	{
		return posY;
	}
	
	public int getCount()
	{
		return count;
	}
	
	public Item getItem()
	{
		return item;
	}
	
	public void setInventory(Inventory inventory)
	{
		this.inventory = inventory;
	}
}
