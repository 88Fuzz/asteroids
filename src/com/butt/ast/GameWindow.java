package com.butt.ast;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

//creates the game window
public abstract class GameWindow extends JFrame
{
	protected static final DisplayMode displayMode=new DisplayMode(Globals.WIDTH,Globals.HEIGHT,16,DisplayMode.REFRESH_RATE_UNKNOWN);
	final JFrame frame=new JFrame();
	protected boolean play;
	protected GraphicsDevice device;
	
	public void init()
	{
		GraphicsEnvironment environ=GraphicsEnvironment.getLocalGraphicsEnvironment();
		device=environ.getDefaultScreenDevice();
		
		//final JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setIgnoreRepaint(true);
		frame.setResizable(false);
		
		device.setFullScreenWindow(frame);
		
		System.out.print("IN HERE width: "+frame.getWidth()+"\n");
		System.out.print("IN HERE height: "+frame.getHeight()+"\n");
		
		Globals.WIDTH=frame.getWidth();
		Globals.HEIGHT=frame.getHeight();
		
		
		try
		{
			EventQueue.invokeAndWait(new Runnable() {
				public void run() { frame.createBufferStrategy(2);}
			});
		}
		catch (InterruptedException ex) {}
		catch (InvocationTargetException ex) {}
		
		
		Window window=device.getFullScreenWindow();
		window.setFont(new Font("Dialog", 0, 24));
		window.setBackground(Color.black);
		window.setForeground(Color.white);
		play=true;
	}
	
	public Graphics2D getGraphics()
	{
		Window window=device.getFullScreenWindow();
		if(window!=null)
		{
			BufferStrategy strat=window.getBufferStrategy();
			return (Graphics2D)strat.getDrawGraphics();
		}
		else
			return null;
	}
	
	public void update()
	{
		Window window = device.getFullScreenWindow();
		if(window!=null)
		{
			BufferStrategy strat=window.getBufferStrategy();
			if(!strat.contentsLost())
			{
				strat.show();
			}
		}
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void restoreScreen()
	{
		Window window=device.getFullScreenWindow();
		if(window!=null)
		{
			window.dispose();
		}
		device.setFullScreenWindow(null);
	}
	
	public abstract void run();
	public abstract void gameLoop();
	public abstract void draw(Graphics2D g);
}
