package com.butt.ast;

import java.awt.Color;
import java.awt.Font;
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
	//private Player player2;
	private InputManager inMan;
	private GameAction p1Thrust;
	private GameAction p2Thrust;
	private GameAction p1RotateL;
	private GameAction p2RotateL;
	private GameAction p1RotateR;
	private GameAction p2RotateR;
	private GameAction p1Shoot;
	private GameAction p2Shoot;
	
	//private Alien alien;//WILL NEED TO BE CHANGED
	
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
		Globals.player1=new Player(Globals.p1Img, Globals.p1Bullet, 
				(double)Globals.WIDTH/2-Globals.WIDTH/4, (double)Globals.HEIGHT/2, 
				Globals.HITALLBUTPLAYER1, Globals.WIDTH/100, Color.WHITE);
		Globals.player2=new Player(Globals.p2Img, Globals.p2Bullet, 
				(double)Globals.WIDTH/2+Globals.WIDTH/4, (double)Globals.HEIGHT/2, 
				Globals.HITALLBUTPLAYER2, Globals.WIDTH/100*99, Color.YELLOW);
		
		Globals.alien = new Alien(Globals.alienShip, Globals.alienBullet, Globals.HITPLAYER1N2);
		Globals.ralien = new HardAlien(Globals.ralienShip, Globals.ralienBullet, Globals.HITPLAYER1N2);
		
		Globals.gravity = new Gravity(Globals.gravityImg);
		Globals.ast = new Asteroids(Globals.bigAst); 
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
		Graphics2D g;
		while(Globals.g_play>0)
		{
			//Game is not paused
			if(Globals.g_play==Globals.PLAY)
			{
				elapsedTime=System.currentTimeMillis()-currTime;
				currTime+=elapsedTime;
			
				updateGraphicsPos(elapsedTime);
			
				g=getGraphics();
				draw(g);
				g.dispose();
				update();
			
				try
				{
					Thread.sleep(20);
				}
				catch (InterruptedException ex) { }
			}
			else if(Globals.g_play==Globals.START)//game is paused
			{
				elapsedTime=System.currentTimeMillis()-currTime;
				currTime+=elapsedTime;
				
				g=getGraphics();
				drawStart(g);
				g.dispose();
				update();
				try
				{
					Thread.sleep(20);
				}
				catch (InterruptedException ex) { }
			}
		}
	}
	
	//draws starting screen
	public synchronized void drawStart(Graphics2D g)
	{
		int titleFont=Globals.WIDTH/10;
		int optionFont=titleFont/7;
		int hOffset=Globals.HEIGHT/10+titleFont;
		int wOffset=Globals.WIDTH/100;
		Window window=device.getFullScreenWindow();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(window.getBackground());
		g.fillRect(0, 0, Globals.WIDTH, Globals.HEIGHT);
		g.setColor(window.getForeground());
		
		g.setFont(new Font("dialog", Font.PLAIN, titleFont));
		g.drawString("ASTEROIDS", wOffset, hOffset);
		hOffset+=titleFont;
		
		g.setFont(new Font("dialog", Font.PLAIN, optionFont));
		if(Globals.optionsNum==0)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("Play Game", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==0)
		{
			g.setColor(Color.WHITE);
		}
		
		
		if(Globals.optionsNum==1)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("option2", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==1)
		{
			g.setColor(Color.WHITE);
		}
		
		
		if(Globals.optionsNum==2)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("option3", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==2)
		{
			g.setColor(Color.WHITE);
		}
		
		
		if(Globals.optionsNum==3)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("option4", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==3)
		{
			g.setColor(Color.WHITE);
		}
		
		
		if(Globals.optionsNum==4)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("option5", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==4)
		{
			g.setColor(Color.WHITE);
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
		Globals.player2.draw(g);
		
		Globals.alien.draw(g);
		Globals.ralien.draw(g);
		Globals.gravity.draw(g);
		Globals.ast.draw(g); 
	}
	
	//checks if keys are pressed and takes the actions if keys are pressed
	public void updateGraphicsPos(long diff)
	{
		
		if(Globals.gravity.isAlive())
		{
			Globals.gravity.moveShipes(diff);
		}
		
		if(Globals.player1.isAlive())
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
		}
		
		if(Globals.player2.isAlive())
		{
			if(p2Thrust.isPressed())
				Globals.player2.thrustOn(diff);
			else
				Globals.player2.thrustOff(diff);
		
			if(p2RotateL.isPressed())
				Globals.player2.rotate(diff, Player.LEFT);
			if(p2RotateR.isPressed())
				Globals.player2.rotate(diff, Player.RIGHT);
		
			//TODO THIS COULD BE DONE BETTER????
			if(p2Shoot.isPressed())
				Globals.player2.shootOn(diff);
			else
				Globals.player2.shootOff(diff);
		}
			
		if(Globals.alien.isAlive())
		{
			Globals.alien.updatePos();
			Globals.alien.checkEdges();
		}
		
		if(Globals.ast.isAlive())
		{
			Globals.ast.updatePos();
			Globals.ast.checkEdges();
		}
		else//do spawn checking stuff
		{
			Globals.alien.checkSpawn(diff);
		}
		
		if(Globals.ralien.isAlive())
		{
			Globals.ralien.updatePos();
			Globals.ralien.checkEdges();
		}
		else//do spawn checking stuff
		{
			Globals.ralien.checkSpawn(diff);
		}
		
		if(Globals.player1.isAlive())
		{
			Globals.player1.updatePos();
			Globals.player1.checkEdges();
		}
		else
		{
			Globals.player1.checkSpawn(diff);
		}
		
		if(Globals.player2.isAlive())
		{
			Globals.player2.updatePos();
			Globals.player2.checkEdges();
		}
		else
		{
			Globals.player2.checkSpawn(diff);
		}
	}
}