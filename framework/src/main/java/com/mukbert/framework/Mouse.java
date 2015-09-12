package com.mukbert.framework;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

public class Mouse implements MouseListener, MouseMotionListener
{
	private Point2D point = new Point2D.Double();
	private boolean isPressedLeft;
	private boolean isPressedRight;
	private boolean isDraggedLeft;
	private boolean isDraggedRight;
	
	public void mouseDragged(MouseEvent e) 
	{
		point = e.getPoint();
		
		if(isLeftDown())
		{
			isDraggedLeft = true;
		}
		if(isRightDown())
		{
			isDraggedRight = true;
		}
	}
	
	public void mouseReleased(MouseEvent e) 
	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			isPressedLeft = false;
			isDraggedLeft = false;
		}
		if(e.getButton() == MouseEvent.BUTTON3)
		{
			isPressedRight = false;
			isDraggedRight = false;
		}
	}
	
	public void mouseMoved(MouseEvent e) 
	{
		point = e.getPoint();
	}
	
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	public void mouseEntered(MouseEvent e)
	{
		
	}
	
	public void mouseExited(MouseEvent e) 
	{
		
	}
	
	public void mousePressed(MouseEvent e) 
	{
		if(e.getButton() == MouseEvent.BUTTON1) isPressedLeft  = true;
		if(e.getButton() == MouseEvent.BUTTON3) isPressedRight = true;
	}
	
	public Point2D getPoint()
	{
		return point;
	}
	
	public boolean isLeftDown()
	{
		return isPressedLeft;
	}
	
	public boolean isRightDown()
	{
		return isPressedRight;
	}
	
	public boolean isDraggedLeft()
	{
		return isDraggedLeft;
	}
	
	public boolean isDraggedRight()
	{
		return isDraggedRight;
	}
}
