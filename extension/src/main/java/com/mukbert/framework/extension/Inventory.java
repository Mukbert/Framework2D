package com.mukbert.framework.extension;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.mukbert.framework.EntityBasic;
import com.mukbert.framework.EntityColor;
import com.mukbert.framework.Framework;

public class Inventory extends EntityColor
{
	public static final int TOP = 1;
	public static final int BOTTOM = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;
	
	private Font font;
	
	private double diff;
	private double sizeW;
	private double sizeH;
	
	private boolean canMoveItems;
	private boolean canMoveInventory;
	
	private boolean isMoving;
	private double moveX;
	private double moveY;
	
	private boolean isVisible;
	
	private List<Slot> slots;
	private int sizeX;
	private int sizeY;
	
	private Slot moveSlot;
	private MenuBar menuBar;
	
	public Inventory(int sizeX, int sizeY, String[] buttons)
	{
		super(EntityBasic.RECTANGLE);

		setSizeX(sizeX);
		setSizeY(sizeY);
		setDiff(5);
		setSize(50);
		createMenu(buttons);
		setColor(Color.darkGray);
		setPosition(RIGHT);
		setPosition(BOTTOM);
		setCanMoveInventory(true);
		setCanMoveItem(true);	
		setVisible(true);
	}
	
	public void update()
	{
		if(isVisible())
		{
			if(canMoveItems)
			{
				moveItems();
			}
			if(canMoveInventory)
			{
				moveInventory();
			}
			
			menuBar.update();
		}
	}
	
	public void draw(Graphics2D g)
	{
		if(isVisible())
		{
			super.draw(g);
			
			for(Slot slot : slots)
			{
				slot.draw(g);
			}
			
			if(menuBar != null)
			{
				menuBar.draw(g);
			}
			
			if(moveSlot != null)
			{
				Point2D p = Framework.getMouse().getPoint();
				g.translate(p.getX(), p.getY());
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
				g.drawImage(moveSlot.getItem().getImage(), (int)(-sizeW / 2), (int)(-sizeH / 2), (int)sizeW, (int)sizeH, null);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
				g.translate(-p.getX(), -p.getY());
			}
		}
	}
	
	public void updateBounds()
	{
		updateSlotBounds();
		menuBar.updateButtonBounds();
	}
	
	private void createMenu(String[] buttons)
	{
		menuBar = new MenuBar();
		menuBar.setInventory(this);
		
		if(buttons == null || buttons.length == 0)
		{
			buttons = new String[]{""};
		}
		
		for(String button : buttons)
		{			
			menuBar.addMenu(button);
			
		}
		
		menuBar.setFocus(menuBar.getButtons().get(0));
		
		updateBounds();
	}
	
	private void moveInventory()
	{
		if(isMoving)
		{
			setX(Framework.getMouse().getPoint().getX() - moveX);
			setY(Framework.getMouse().getPoint().getY() - moveY);
			
			if(getX() < getDiff()) setX(getDiff());
			else if(getX() > Framework.getWidth() - getWidth() - getDiff()) setX(Framework.getWidth() - getWidth() - getDiff());
			
			
			if(getY() < getDiff() + menuBar.getButtons().get(0).getHeight()) setY(getDiff() + menuBar.getButtons().get(0).getHeight());
			else if(getY() > Framework.getHeight() - getHeight() - getDiff()) setY(Framework.getHeight() - getHeight() - getDiff());
			
			updateBounds();
		}
		
		if(Framework.getMouse().isRightDown())
		{
			if(!isMoving && contains(Framework.getMouse().getPoint()))
			{
				isMoving = true;
				
				moveX = Framework.getMouse().getPoint().getX() - getX();
				moveY = Framework.getMouse().getPoint().getY() - getY();
			}
		}
		else
		{
			isMoving = false;
		}
		
		
	}
	
	public boolean isEmpty()
	{
		for(Slot slot : slots)
		{
			if(!slot.isEmpty()) return false;
		}
		
		return true;
	}
	
	public Slot getSlot(Point2D point)
	{
		for(Slot slot : slots)
		{
			if(slot.contains(point)) return slot;
		}
		
		return null;
	}
	
	public List<Slot> getSlots()
	{
		return slots;
	}
	
	@Override
	public boolean contains(Point2D point) 
	{
		boolean b = super.contains(point);
		
		if(b) return true;
		
		for(Menu menu : menuBar.getButtons())
		{
			b = menu.contains(point);
			
			if(b) return true;
		}
		
		return false;
	}
	
	private void moveItems()
	{
		if(moveSlot == null)
		{
			if(Framework.getMouse().isDraggedLeft())
			{
				for(Slot slot : slots)
				{
					if(!slot.isEmpty() && slot.contains(Framework.getMouse().getPoint()))
					{
						moveSlot = slot;
						return;
					}
				}
			}
		}
		else
		{
			if(!Framework.getMouse().isDraggedLeft())
			{
				for(Slot slot : slots)
				{
					if(moveSlot != slot && slot.contains(Framework.getMouse().getPoint()))
					{
						Item item = moveSlot.getItem();
						int count = moveSlot.getCount();
						
						moveSlot.setItem(slot.getItem(), slot.getCount());
						slot.setItem(item, count);
						
						moveSlot = null;
						return;
					}
				}
				
				moveSlot = null;
			}
		}
	}
	
	public List<Slot> createSlots()
	{
		slots = new ArrayList<Slot>();
		
		for(int y = 0; y < sizeY; y++)
		{
			for(int x = 0; x < sizeX; x++)
			{
				Slot slot = new Slot(x, y);
				
				slot.setInventory(this);
				
				slots.add(slot);
			}
		}
		
		return slots;
	}
	
	public boolean addItem(Item item)
	{
		for(Slot slot : slots)
		{
			if(slot.getItem() == item)
			{
				slot.addItem(1);
				return true;
			}
		}
		for(Slot slot : slots)
		{
			if(slot.getItem() == null)
			{
				slot.setItem(item, 1);
				return true;
			}
		}
		return false;
	}
	
	public void updateSlotBounds()
	{
		sizeW = (getWidth()  - diff) / (double)sizeX - diff;
		sizeH = (getHeight() - diff) / (double)sizeY - diff;
				
		font = new Font(Framework.getFont(), Font.BOLD, (int)(sizeH / 3D));
		
		for(Menu button : menuBar.getButtons())
		{
			for(Slot slot : button.getSlots())
			{
				slot.setWidth(sizeW);
				slot.setHeight(sizeH);
				slot.setX(getX() + diff + (sizeW + diff) * slot.getPosX());
				slot.setY(getY() + diff + (sizeH + diff) * slot.getPosY());
			}
		}
	}
	
	public void setSize(double size)
	{
		super.setWidth ((size + diff) * sizeX + diff);
		super.setHeight((size + diff) * sizeY + diff);
	}
	
	@Override
	public void setWidth(double width)
	{
		super.setWidth(width);
		
		sizeW = (getWidth() - diff) / (double)sizeX - diff;
		sizeH = sizeW;
		
		super.setHeight((sizeW + diff) * sizeY + diff);
		
		setSize(sizeW);
	}
	
	@Override
	public void setHeight(double height)
	{
		super.setHeight(height);
		
		sizeH = (getHeight() - diff) / (double)sizeY - diff;
		sizeW = sizeH;
		
		super.setWidth((sizeH + diff) * sizeX + diff);
		
		setSize(sizeH);
	}
	
	public void setVisible(boolean isVisible)
	{
		this.isVisible = isVisible;
	}
	
	public void switchVisibility()
	{
		setVisible(!isVisible());
	}
	
	public boolean isVisible()
	{
		return isVisible;
	}
	
	public void setCanMoveItem(boolean canMoveItem)
	{
		this.canMoveItems = canMoveItem;
	}
	
	public void setCanMoveInventory(boolean canMoveInventory)
	{
		this.canMoveInventory = canMoveInventory;
	}
	
	private void setSizeX(int sizeX)
	{
		this.sizeX = sizeX;
	}
	
	private void setSizeY(int sizeY)
	{
		this.sizeY = sizeY;
	}
	
	public void setDiff(int DIFF)
	{
		this.diff = DIFF;
	}
	
	public double getDiff()
	{
		return diff;
	}
	
	public double getDiff(double multi)
	{
		return diff * multi;
	}
	
	public Font getFont()
	{
		return font;
	}

	public int getSizeX() 
	{
		return sizeX;
	}

	public int getSizeY() 
	{
		return sizeY;
	}

	public void setSlots(List<Slot> slots) 
	{
		this.slots = slots;
	}
	
	public void setPosition(int code)
	{
		if(code == LEFT)
		{
			setX(getDiff());
		}
		else if(code == RIGHT)
		{
			setX(Framework.getWidth() - getWidth() - getDiff());
		}
		else if(code == TOP)
		{
			setY(getDiff());
		}
		else if(code == BOTTOM)
		{
			setY(Framework.getHeight() - getHeight() - getDiff());
		}
	}

	public List<Menu> getButtons() 
	{
		return menuBar.getButtons();
	}
}
