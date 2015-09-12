package com.mukbert.framework;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Frame extends JFrame
{
	public Frame(String title) 
	{
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setPanel(Panel panel)
	{
		setContentPane(panel);
		pack();
		setLocationRelativeTo(null);
	}
}
