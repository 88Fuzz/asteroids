package com.butt.ast;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

import com.butt.ast.SimpleScreenManager;

public class Main extends JFrame
{
	public static void main(String[] args)
	{
		DisplayMode displayMode;
		
		displayMode=new DisplayMode(800,600,16,DisplayMode.REFRESH_RATE_UNKNOWN);
		
		Main test=new Main();
		test.run(displayMode);
	}
	
	private static final long DEMO_TIME = 5000;
	
	public void run(DisplayMode displayMode)
	{
		setBackground(Color.black);
		setForeground(Color.white);
		setFont(new Font("Dialog",0,24));
		SimpleScreenManager screen = new SimpleScreenManager();
		try
		{
			screen.setFullScreen(displayMode, this);
			try
			{
				Thread.sleep(DEMO_TIME);
			}
		catch (InterruptedException ex) { }
		}
		finally
		{
		screen.restoreScreen();
		}
	}
	
	public void paint(Graphics g)
	{
		g.drawString("Hello World!", 20, 50);
	}

}