package com.butt.ast;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;

//initializes everything and stuff
public class Main extends GameWindow// implements KeyListener
{
	public static void main(String[] args)
	{
		new Main().run();
	}
	
	//player 1 and 2 classes
	private Player player1;
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
		player1=new Player(Globals.p1Img);
		player1.set_x(Globals.WIDTH/2);
		player1.set_y(Globals.HEIGHT/2);
		
		player2=new Player(Globals.p2Img);
		player2.set_x(Globals.WIDTH/4);
		player2.set_y(Globals.HEIGHT/4);
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
		inMan.mapActToKey(p2Shoot, KeyEvent.VK_SLASH);
		
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
		
		BufferedImageOp ops = null;
		
		
		AffineTransform tx=AffineTransform.getRotateInstance(Math.toRadians(player1.getRotate()), player1.getWidth()/2, player1.getHeight()/2);
		AffineTransformOp op=new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		
		g.drawImage(op.filter(player1.getImage(), null), ops, (int)Math.round(player1.get_x()), (int)Math.round(player1.get_y()));
		
		tx=AffineTransform.getRotateInstance(Math.toRadians(player2.getRotate()), player2.getWidth()/2, player2.getHeight()/2);
		op=new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);//maybe change this to something that doesn't use new
		g.drawImage(op.filter(player2.getImage(), null), ops, (int)Math.round(player2.get_x()), (int)Math.round(player2.get_y()));
		
		
		g.drawString("HELLO THERE\n", 20, 50);
	}
	
	//checks if keys are pressed and takes the actions if keys are pressed
	public void updateGraphicsPos(long diff)
	{
		if(p1Thrust.isPressed())
			player1.thrustOn(diff);
		else
			player1.thrustOff(diff);
		
		if(p1RotateL.isPressed())
			player1.rotate(diff, Player.LEFT);
		if(p1RotateR.isPressed())
			player1.rotate(diff, Player.RIGHT);
		if(p1Shoot.isPressed())
			player1.shoot(diff);
		
		if(p2Thrust.isPressed())
			player2.thrustOn(diff);
		else
			player2.thrustOff(diff);
		
		if(p2RotateL.isPressed())
			player2.rotate(diff, Player.LEFT);
		if(p2RotateR.isPressed())
			player2.rotate(diff, Player.RIGHT);
		if(p2Shoot.isPressed())
			player2.shoot(diff);
			
			
		player1.updatePos();
		player1.checkEdges();
		
		player2.updatePos();
		player2.checkEdges();
	}
}