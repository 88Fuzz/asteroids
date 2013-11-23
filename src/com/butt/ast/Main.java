package com.butt.ast;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.KeyEvent;

//initializes everything and stuff
public class Main extends GameWindow// implements KeyListener
{
	public static void main(String[] args)
	{
		new Main().run();
	}
	
	//player 1 and 2 classes
	//private Player player1;
	private Player player2;
	private InputManager inMan;
	private GameAction p1Thrust;
	private GameAction p2Thrust;
	private GameAction p1RotateL;
	private GameAction p2RotateL;
	private GameAction p1RotateR;
	private GameAction p2RotateR;
	private GameAction p1Shoot;
	private GameAction p2Shoot;
	
	private Alien alien;//WILL NEED TO BE CHANGED
	
	public void init()
	{
		super.init();
		Window window=device.getFullScreenWindow();
		window.setFocusTraversalKeysEnabled(false);


		inMan=new InputManager(window);
		createSprites();
		createGameActions();
	}
	
	private void createSprites()
	{
		Globals.player1=new Player(Globals.p1Img, Globals.p1Bullet, (double)Globals.WIDTH/2, (double)Globals.HEIGHT/2);
		player2=new Player(Globals.p2Img, Globals.p2Bullet, (double)Globals.WIDTH/4, (double)Globals.HEIGHT/4);
		
		alien= new Alien(Globals.alienShip, Globals.alienBullet, (double)Globals.WIDTH/4*3, (double)Globals.HEIGHT/4*3);
	}
	
	//initializes keyboard inputs for the game actions
	private void createGameActions()
	{
		p1Thrust=new GameAction("thrust");
		p1RotateL=new GameAction("rotate left");
		p1RotateR=new GameAction("rotate right");
		p1Shoot=new GameAction("shoot");
		
		p2Thrust=new GameAction("thrust");
		p2RotateL=new GameAction("rotate left");
		p2RotateR=new GameAction("rotate right");
		p2Shoot=new GameAction("shoot");
		
		
		inMan.mapActToKey(p1Thrust, KeyEvent.VK_W);
		inMan.mapActToKey(p1RotateL, KeyEvent.VK_A);
		inMan.mapActToKey(p1RotateR, KeyEvent.VK_D);
		inMan.mapActToKey(p1Shoot, KeyEvent.VK_SPACE);
		
		inMan.mapActToKey(p2Thrust, KeyEvent.VK_UP);
		inMan.mapActToKey(p2RotateL, KeyEvent.VK_LEFT);
		inMan.mapActToKey(p2RotateR, KeyEvent.VK_RIGHT);
		inMan.mapActToKey(p2Shoot, KeyEvent.VK_CONTROL);
		
	}
	
	//main runloop
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
	}
	
	//game logic
	public void gameLoop()
	{
		long currTime=System.currentTimeMillis();
		long elapsedTime;
		while(Globals.g_play>0)
		{
			//Game is not paused
			if(Globals.g_play==Globals.PLAY)
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
			else//game is paused
			{
				
			}
		}
	}
	
	//draws the images on screen
	public synchronized void draw(Graphics2D g)
	{
		Window window=device.getFullScreenWindow();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(window.getBackground());
		g.fillRect(0, 0, Globals.WIDTH, Globals.HEIGHT);
		g.setColor(window.getForeground());

		Globals.player1.draw(g);
		player2.draw(g);
		
		alien.draw(g);		
		
		g.drawString("HELLO THERE\n", 20, 50);
	}
	
	//checks if keys are pressed and takes the actions if keys are pressed
	public void updateGraphicsPos(long diff)
	{
		if(p1Thrust.isPressed())
			Globals.player1.thrustOn(diff);
		else
			Globals.player1.thrustOff(diff);
		
		if(p1RotateL.isPressed())
			Globals.player1.rotate(diff, Player.LEFT);
		if(p1RotateR.isPressed())
			Globals.player1.rotate(diff, Player.RIGHT);
		
		//TODO THIS COULD BE DONE BETTER????
		if(p1Shoot.isPressed())
			Globals.player1.shootOn(diff);
		else
			Globals.player1.shootOff(diff);
		
		if(p2Thrust.isPressed())
			player2.thrustOn(diff);
		else
			player2.thrustOff(diff);
		
		if(p2RotateL.isPressed())
			player2.rotate(diff, Player.LEFT);
		if(p2RotateR.isPressed())
			player2.rotate(diff, Player.RIGHT);
		
		//TODO THIS COULD BE DONE BETTER????
		if(p2Shoot.isPressed())
			player2.shootOn(diff);
		else
			player2.shootOff(diff);
			
		
		alien.updatePos();
		alien.checkEdges();
			
		Globals.player1.updatePos();
		Globals.player1.checkEdges();
		
		player2.updatePos();
		player2.checkEdges();
	}
}