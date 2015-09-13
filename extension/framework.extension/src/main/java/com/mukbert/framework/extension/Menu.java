package com.mukbert.framework.extension;

import java.awt.Graphics2D;
import java.util.List;

public class Menu extends Button
{
	private List<Slot> slots;
	
	public Menu(String string, double x, double y, double width, double height) 
	{
		super(string, x, y, width, height);
	}
	
	@Override
	public void draw(Graphics2D g) 
	{
		super.draw(g);
	}
	
	public void setSlots(List<Slot> slots)
	{
		this.slots = slots;
	}
	
	public List<Slot> getSlots()
	{
		return slots;
	}
}
