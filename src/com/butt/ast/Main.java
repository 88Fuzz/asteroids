package com.butt.ast;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

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
	private GameAction selDown;
	private GameAction select;
	private GameAction selUp;
	private GameAction selLeft; 
	private GameAction selRight; 
	private GameAction pause;
	private int astcount=3;
	private int levelNum;
	
	//private Alien alien;//WILL NEED TO BE CHANGED
	
	public void init()
	{
		super.init();
		Window window=device.getFullScreenWindow();
		window.setFocusTraversalKeysEnabled(false);
		
		//JOptionPane.showMessageDialog(null,"Test String" ,"High Score", 1);

		inMan=new InputManager(window);
		createSprites();
		createGameActions();
	}
	
	private void createSprites()
	{
		Globals.player1=new Player(Globals.p1Img, Globals.p1Bullet, 
				(double)Globals.WIDTH/2-Globals.WIDTH/4, (double)Globals.HEIGHT/2, 
				Globals.HITALLBUTPLAYER1, Globals.WIDTH/100, Color.WHITE, Globals.p1life, 1);
		Globals.player2=new Player(Globals.p2Img, Globals.p2Bullet, 
				(double)Globals.WIDTH/2+Globals.WIDTH/4, (double)Globals.HEIGHT/2, 
				Globals.HITALLBUTPLAYER2, Globals.WIDTH/100*99, Color.YELLOW, Globals.p2life, 2);
		
		Globals.alien = new Alien(Globals.alienShip, Globals.alienBullet, Globals.HITPLAYER1N2);
		Globals.ralien = new HardAlien(Globals.ralienShip, Globals.ralienBullet, Globals.HITPLAYER1N2NALIEN);
		
		Globals.gravity = new Gravity(Globals.gravityImg);
		//Globals.ast = new Asteroids(Globals.bigAst);
		Globals.asts = new ArrayList<Asteroids>();
		Globals.smasts = new ArrayList<Smallasteroids>();
		
		Asteroids.addast(astcount);
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
		
		selUp=new GameAction("up", GameAction.DETECT_INITIAL_ONLY);
		selDown=new GameAction("down", GameAction.DETECT_INITIAL_ONLY);
		selLeft = new GameAction("left", GameAction.DETECT_INITIAL_ONLY); 
		selRight = new GameAction("right", GameAction.DETECT_INITIAL_ONLY);
		select=new GameAction("select");
		pause=new GameAction("pause", GameAction.DETECT_INITIAL_ONLY);
		
		/*inMan.mapActToKey(p1Thrust, KeyEvent.VK_W);
		inMan.mapActToKey(p1RotateL, KeyEvent.VK_A);
		inMan.mapActToKey(p1RotateR, KeyEvent.VK_D);
		inMan.mapActToKey(p1Shoot, KeyEvent.VK_SPACE);
		
		inMan.mapActToKey(p2Thrust, KeyEvent.VK_UP);
		inMan.mapActToKey(p2RotateL, KeyEvent.VK_LEFT);
		inMan.mapActToKey(p2RotateR, KeyEvent.VK_RIGHT);
		inMan.mapActToKey(p2Shoot, KeyEvent.VK_CONTROL);*/
		
		inMan.mapActToKey(selUp, KeyEvent.VK_UP);
		inMan.mapActToKey(selDown, KeyEvent.VK_DOWN);
		inMan.mapActToKey(selLeft, KeyEvent.VK_LEFT);
		inMan.mapActToKey(selRight, KeyEvent.VK_RIGHT); 
		inMan.mapActToKey(select, KeyEvent.VK_ENTER);
		inMan.mapActToKey(pause, KeyEvent.VK_ESCAPE);
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
				
				updateStartGraphicsPos(elapsedTime);
				
				g=getGraphics();
				drawStart(g);
				g.dispose();
				update();
				try
				{
					Thread.sleep(80);
				}
				catch (InterruptedException ex) { }
			}
			else if(Globals.g_play==Globals.PAUSE)
			{
				elapsedTime=System.currentTimeMillis()-currTime;
				currTime+=elapsedTime;
				
				updatePauseGraphicsPos(elapsedTime);
				
				g=getGraphics();
				drawPause(g);
				g.dispose();
				update();
				try
				{
					Thread.sleep(80);
				}
				catch (InterruptedException ex) { }
			}
		}
	}
	
	public void drawPause(Graphics2D g)
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
		
		//resume
		g.setFont(new Font("dialog", Font.PLAIN, optionFont));
		if(Globals.optionsNum==0)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("Resume", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==0)
		{
			g.setColor(Color.WHITE);
		}
		
		//gravity
		g.setFont(new Font("dialog", Font.PLAIN, optionFont));
		if(Globals.optionsNum==1)
		{
			g.setColor(Color.YELLOW);
		}
		if(Globals.gravity.alive)
		{
		g.drawString("Set gravity On", wOffset, hOffset); 
		}
		else
		{
			g.drawString("Set gravity Off", wOffset, hOffset);	
		}
		hOffset+=titleFont/3;
		if(Globals.optionsNum==1)
		{
			g.setColor(Color.WHITE);
		}
		
		//visible gravity
		if(Globals.optionsNum==2)
		{
			g.setColor(Color.YELLOW);
		}
		if(Globals.gravity.visible)
		{
		g.drawString("Gravity Ball On", wOffset, hOffset); 
		}
		else
		{
		g.drawString("Gravity Ball Off", wOffset, hOffset);	
		}
		hOffset+=titleFont/3;
		if(Globals.optionsNum==2)
		{
			g.setColor(Color.WHITE);
		}
		
		//infinite lives
		if(Globals.optionsNum==3)
		{
			g.setColor(Color.YELLOW);
		}
		
		if (Globals.player1.getLives() < 0)
		{
		g.drawString("Infinite Lives On", wOffset, hOffset);	
		}
		else 
		{
		g.drawString("Infinite Lives Off", wOffset, hOffset); 
		}
		hOffset+=titleFont/3;
		if(Globals.optionsNum==3)
		{
			g.setColor(Color.WHITE);
		}
		
		//screen wrap
		if(Globals.optionsNum==4)
		{
			g.setColor(Color.YELLOW);
		}
		if(Globals.wrapObjs)
			g.drawString("Edge type \tWrap", wOffset, hOffset);
		else
			g.drawString("Edge type \tBounce", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==4)
		{
			g.setColor(Color.WHITE);
		}
		
		//number of asteroids
		if(Globals.optionsNum==5)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("Number of asteroids" + "  "+ astcount, wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==5)
		{
			g.setColor(Color.WHITE);
		}
		
		//reset high score
		if(Globals.optionsNum==6)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("Reset high score", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==6)
		{
			g.setColor(Color.WHITE);
		}
		
		//save
		if(Globals.optionsNum==7)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("Save", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==7)
		{
			g.setColor(Color.WHITE);
		}
		
		//continue from save
		if(Globals.optionsNum==8)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("Continue from save", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==8)
		{
			g.setColor(Color.WHITE);
		}
		
		//starting level
		if(Globals.optionsNum==9)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("Start new level" + "  " + Globals.level, wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==9)
		{
			g.setColor(Color.WHITE);
		}
		

		
		//exit
		if(Globals.optionsNum==10)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("Exit", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==10)
		{
			g.setColor(Color.WHITE);
		}
	}
	
	public void updatePauseGraphicsPos(long diff)
	{
		if(selUp.isPressed())
		{
			Globals.optionsNum-=1;
			if(Globals.optionsNum<0)
				Globals.optionsNum=10;
		}
		
		if(selDown.isPressed())
		{
			Globals.optionsNum+=1;
			Globals.optionsNum=Globals.optionsNum%11;
		}
		
		if(selLeft.isPressed())
		{
			if(Globals.optionsNum == 5)
			{
				Globals.asts.clear(); 
				astcount--; 
				Asteroids.addast(astcount); 
				
			}
			
			if(Globals.optionsNum == 9)
			{
				Globals.asts.clear(); 
				Globals.level = Globals.level - 1; 
				astcount = Globals.level + 1; 
				Asteroids.addast(astcount); 
				
			}
		}
		
		if(selRight.isPressed())
		{
			if(Globals.optionsNum == 5)
			{
				Globals.asts.clear();
				astcount++; 
				Asteroids.addast(astcount);
				Globals.player1.lives = 3; 
				Globals.player1.setScore(0); 
			 
			}
			
			if(Globals.optionsNum == 9)
			{
				Globals.asts.clear(); 
				Globals.level = Globals.level + 1; 
				astcount = Globals.level + 1; 
				Asteroids.addast(astcount); 
				Globals.player1.lives = 3; 
				Globals.player1.setScore(0);
				
			}
		}
		
		if(select.isPressed())
		{
			//resume
			if(Globals.optionsNum==0)
			{
				Globals.optionsNum=0;
				inMan.mapActToKey(p1Thrust, KeyEvent.VK_W);
				inMan.mapActToKey(p1RotateL, KeyEvent.VK_A);
				inMan.mapActToKey(p1RotateR, KeyEvent.VK_D);
				inMan.mapActToKey(p1Shoot, KeyEvent.VK_SPACE);
				
				inMan.mapActToKey(p2Thrust, KeyEvent.VK_UP);
				inMan.mapActToKey(p2RotateL, KeyEvent.VK_LEFT);
				inMan.mapActToKey(p2RotateR, KeyEvent.VK_RIGHT);
				inMan.mapActToKey(p2Shoot, KeyEvent.VK_CONTROL);
				Globals.g_play=Globals.PLAY;
			}
			else if(Globals.optionsNum==1)//gravity on
			{
				if(Globals.gravity.alive)
				{
					Globals.gravity.setAlive(false);  
					 
				}
				
				else if(!Globals.gravity.alive)
				{
					Globals.gravity.setAlive(true);	
				
				}
			}
			
			else if(Globals.optionsNum==2)//gravity visible
			{
				if(Globals.gravity.visible)
				{
					Globals.gravity.setVisible(false);  
					 
				}
				
				else if(!Globals.gravity.visible)
				{
					Globals.gravity.setVisible(true);	
					 
				}
			}
			
			else if(Globals.optionsNum==3)//infinite lives
			{
				if(Globals.player1.getLives() < 0)
				{
					Globals.player1.setLives(3);
					Globals.player2.setLives(3);
							 
				}
				else 
				{
					Globals.player1.setLives(-1);
					Globals.player2.setLives(-1);
				}
			}
			else if(Globals.optionsNum==4)//edge type select
			{
				Globals.wrapObjs=!Globals.wrapObjs;
			}
			
			else if(Globals.optionsNum==9)
			{
				
			}
			
			else if(Globals.optionsNum==10)//end game
			{
				Globals.g_play=Globals.KILL;
			}
			
			/*if(Globals.optionsNum==0)//one player
			{
				Globals.player2.setNeverAlive();
				Globals.g_play=Globals.PLAY;
			}
			else//two player
			{
				Globals.g_play=Globals.PLAY;
			}*/
			
			
		}
	}
	
	public void updateStartGraphicsPos(long diff)
	{
		if(selUp.isPressed())
		{
			Globals.optionsNum-=1;
			if(Globals.optionsNum<0)
				Globals.optionsNum=1;
		}
		
		if(selDown.isPressed())
		{
			Globals.optionsNum+=1;
			Globals.optionsNum=Globals.optionsNum%2;
		}
		
		if(select.isPressed())
		{
			if(Globals.optionsNum==0)//one player
			{
				Globals.player2.setNeverAlive();
				Globals.g_play=Globals.PLAY;
			}
			else//two player
			{
				Globals.g_play=Globals.PLAY;
			}
			
			inMan.mapActToKey(p1Thrust, KeyEvent.VK_W);
			inMan.mapActToKey(p1RotateL, KeyEvent.VK_A);
			inMan.mapActToKey(p1RotateR, KeyEvent.VK_D);
			inMan.mapActToKey(p1Shoot, KeyEvent.VK_SPACE);
			
			inMan.mapActToKey(p2Thrust, KeyEvent.VK_UP);
			inMan.mapActToKey(p2RotateL, KeyEvent.VK_LEFT);
			inMan.mapActToKey(p2RotateR, KeyEvent.VK_RIGHT);
			inMan.mapActToKey(p2Shoot, KeyEvent.VK_CONTROL);
			
			Globals.optionsNum=0;
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
		
		//one player
		g.setFont(new Font("dialog", Font.PLAIN, optionFont));
		if(Globals.optionsNum==0)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("One player", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==0)
		{
			g.setColor(Color.WHITE);
		}
		
		//two player
		if(Globals.optionsNum==1)
		{
			g.setColor(Color.YELLOW);
		}
		g.drawString("Two player", wOffset, hOffset);
		hOffset+=titleFont/3;
		if(Globals.optionsNum==1)
		{
			g.setColor(Color.WHITE);
		}
		
		
		/*if(Globals.optionsNum==2)
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
		}*/
		
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
		
		//Globals.ast.draw(g);
		for (int i = 0; i < Globals.asts.size(); i++){
			Globals.asts.get(i).draw(g); 
		}
		
		for (int k = 0; k < Globals.smasts.size(); k++){
            Globals.smasts.get(k).draw(g);
		}


	}
	
	//checks if keys are pressed and takes the actions if keys are pressed
	public void updateGraphicsPos(long diff)
	{
		if(pause.isPressed())
		{
			Globals.g_play=Globals.PAUSE;
			inMan.mapActToKey(selUp, KeyEvent.VK_UP);
			inMan.mapActToKey(selLeft, KeyEvent.VK_LEFT);
			inMan.mapActToKey(selRight, KeyEvent.VK_RIGHT);
		}


		
		for (int i = 0; i < Globals.asts.size(); i++){
			if(Globals.asts.get(i).isAlive())
			{
				Globals.asts.get(i).updatePos();
				Globals.asts.get(i).checkEdges();
			}
		}
		
		for (int i = 0; i < Globals.smasts.size(); i++){
            if(Globals.smasts.get(i).isAlive())
            {
            Globals.smasts.get(i).updatePos();
            Globals.smasts.get(i).checkEdges();
            }
		}

		
		if(Globals.gravity.isAlive())
		{
			Globals.gravity.moveShips(diff);
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
		
		if (Globals.asts.size() == 0 && Globals.smasts.size() == 0)
		{
			if(Globals.player1.getLives()!=0)
				Globals.player1.increaseScore(Globals.level*100);
			
			if(Globals.player2.getLives()!=0)
				Globals.player2.increaseScore(Globals.level*100);
			
			Globals.level = Globals.level + 1;
			astcount++;
			Asteroids.addast(astcount);
		}
		
	//	if (Globals.player1.getLives() == 0)
	//	{
	//		Globals.g_play = 2; 
	//	}
	}
}