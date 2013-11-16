package com.butt.ast;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends GameWindow implements KeyListener
{
	public static void main(String[] args)
	{
		new Main().run();
	}
	
	private static final long DEMO_TIME = 50;
	private boolean play=true;
	private Sprite player1;
	
	public void init()
	{
		super.init();
		player1=new Sprite(Globals.shipImg);
		Window window=device.getFullScreenWindow();
		window.setFocusTraversalKeysEnabled(false);
		window.addKeyListener(this);
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
	
	
	
	public void keyPressed(KeyEvent e)
	{
		int keyCode=e.getKeyCode();
		
		if(keyCode==KeyEvent.VK_ESCAPE)
			play=false;
		
		e.consume();
	}
	public void gameLoop()
	{
		while(play)
		{
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
		
		g.drawImage(player1.getImage(), 277,107,null);//Math.round(player1.get_x()), Math.round(player1.get_y()), null);
		g.drawString("HELLO THERE\n", 20, 50);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void LoadPlayers()
	{
		
	}
	
	public void update(long val)
	{
		
	}
}