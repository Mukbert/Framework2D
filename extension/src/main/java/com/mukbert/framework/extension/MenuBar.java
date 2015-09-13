package com.mukbert.framework.extension;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.mukbert.framework.Framework;
import com.mukbert.framework.Handler;

public class MenuBar implements Handler
{
	private int buttonHeight;
	
	private List<Menu> buttons;
	
	private Inventory inventory;
	
	public MenuBar()
	{
		buttons = new ArrayList<Menu>();
		setButtonHeight(35);
	}
	
	public void update()
	{
		for(Menu button : buttons)
		{
			button.updateButton();
		}
	}
	
	public void draw(Graphics2D g)
	{		
		for(Menu button : buttons)
		{
			button.draw(g);
		}
	}
	
	public void updateButtonBounds()
	{
		double w = (inventory.getWidth() + inventory.getDiff()) / buttons.size();
		
		for(int i = 0; i < buttons.size(); i++)
		{
			Button button = buttons.get(i);
			button.setWidth(w - inventory.getDiff());
			button.setHeight(buttonHeight);
			button.setX(inventory.getX() + w * i);
			button.setY(inventory.getY() - button.getHeight());
			button.setColor(inventory.getColor());
		}
	}
	
	public void addMenu(String name)
	{
		Menu button = new Menu(name, 0, 0, 0, 30);
		button.setSlots(inventory.createSlots());
		button.setHandler(this);
		button.setColor(inventory.getColor());
		button.setFontColor(Color.white);
		button.setFont(new Font(Framework.getFont(), Font.BOLD, (int)(button.getHeight() / 2)));
		button.setShowBorder(false);
		buttons.add(button);
		updateButtonBounds();
	}
	
	public void setInventory(Inventory inventory)
	{
		this.inventory = inventory;
	}
	
	public List<Menu> getButtons()
	{
		return buttons;
	}
	
	public void handle(Object object) 
	{
		Menu bt = (Menu) object;
		
		setFocus(bt);
	}
	
	public void setFocus(Menu menu)
	{
		for(Menu menu1 : buttons)
		{
			menu1.setUnselected(true);
		}
		
		menu.setUnselected(false);
		inventory.setSlots(menu.getSlots());
	}
	
	public void setButtonHeight(int height)
	{
		this.buttonHeight = height;
	}

	public boolean isEmpty() 
	{
		return buttons.isEmpty();
	}
}