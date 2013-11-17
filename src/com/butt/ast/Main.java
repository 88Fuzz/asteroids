package com.butt.ast;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.KeyEvent;

public class Main extends GameWindow// implements KeyListener
{
	public static void main(String[] args)
	{
		new Main().run();
	}
	
	private Player player1;//will probably need to extend this class into a play class
	private InputManager inMan;
	private GameAction p1Thrust;
	private GameAction p1RotateL;
	private GameAction p1RotateR;
	private GameAction p1Shoot;
	
	public void init()
	{
		super.init();
		Window window=device.getFullScreenWindow();
		window.setFocusTraversalKeysEnabled(false);
		try changing this thing to match a bunch of set resolutions;
		System.out.print("width: "+window.getWidth()+"\n");
		System.out.print("height: "+window.getHeight()+"\n");
		inMan=new InputManager(window);
		createSprites();
		createGameActions();
		//window.addKeyListener(this);
	}
	
	private void createSprites()
	{
		player1=new Player(Globals.shipImg);
	}
	
	private void createGameActions()
	{
		p1Thrust=new GameAction("thrust");
		p1RotateL=new GameAction("rotate left");
		p1RotateR=new GameAction("rotate right");
		p1Shoot=new GameAction("shoot");
		
		if(p1Thrust==null)
			System.out.print("nulls\n");
		if(inMan==null)
			System.out.print("nullzies\n");
		
		inMan.mapActToKey(p1Thrust, KeyEvent.VK_W);
		inMan.mapActToKey(p1RotateL, KeyEvent.VK_A);
		inMan.mapActToKey(p1RotateR, KeyEvent.VK_D);
		inMan.mapActToKey(p1Shoot, KeyEvent.VK_SPACE);
		
	}
	
	public void run()
	{
		try
		{
			init();
			gameLoop();
		}
		finally
		{
			restoreScreen();
		}
		/*setBackground(Color.black);
		setForeground(Color.white);
		setFont(new Font("Dialog",0,24));
		SimpleScreenManager screen = new SimpleScreenManager();
		
		screen.setFullScreen(displayMode, this);
		
		while(play)
		{
		}
		try
		{
			
			try
			{
				Thread.sleep(DEMO_TIME);
			}
			catch (InterruptedException ex) { }
		}
		finally
		{
		screen.restoreScreen();
		}*/
	}
	
	public void gameLoop()
	{
		long currTime=System.currentTimeMillis();
		long elapsedTime;
		while(Globals.g_play)
		{
			elapsedTime=System.currentTimeMillis()-currTime;
			currTime+=elapsedTime;
			
			updateGraphicsPos(elapsedTime);
			
			Graphics2D g=getGraphics();
			draw(g);
			g.dispose();
			update();
			
			try
			{
				Thread.sleep(20);
			}
			catch (InterruptedException ex) { }
		}
	}
	
	public synchronized void draw(Graphics2D g)
	{
		Window window=device.getFullScreenWindow();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(window.getBackground());
		g.fillRect(0, 0, Globals.WIDTH, Globals.HEIGHT);
		g.setColor(window.getForeground());
		
		g.drawImage(player1.getImage(), Math.round(player1.get_x()),Math.round(player1.get_y()),null);//Math.round(player1.get_x()), Math.round(player1.get_y()), null);
		g.drawString("HELLO THERE\n", 20, 50);
	}
	
	public void updateGraphicsPos(long diff)
	{
		if(p1Thrust.isPressed())
		{
			player1.thrust(diff);
		}
		if(p1RotateL.isPressed())
		{
			player1.rotate(diff, Player.LEFT);
		}
		if(p1RotateR.isPressed())
		{
			player1.rotate(diff, Player.RIGHT);
		}
		if(p1Shoot.isPressed())
		{
			player1.shoot(diff);
		}
	}
}