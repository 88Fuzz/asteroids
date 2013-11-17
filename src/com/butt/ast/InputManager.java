package com.butt.ast;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener
{
	//about 600 key codes
	private static final int NUM_KEY_CODES=600;
	private GameAction[] keyActs=new GameAction[NUM_KEY_CODES];
	private Component comp;
	
	public InputManager(Component comp)
	{
		this.comp=comp;
		
		//this may not be needed
		for(int j=0;j<NUM_KEY_CODES;j++)
		{
			keyActs[j]=null;
		}
		
		comp.addKeyListener(this);
		comp.setFocusTraversalKeysEnabled(false);
	}
	
	public void mapActToKey(GameAction ga, int keyCode)
	{
		keyActs[keyCode]=ga;
	}
	
	private GameAction getKeyAction(int keyCode)
	{
		if(keyCode<NUM_KEY_CODES)
			return keyActs[keyCode];
		return null;
	}
	
	public void keyPressed(KeyEvent e)
	{
		int keyCode=e.getKeyCode();
		if(keyCode==KeyEvent.VK_ESCAPE)
			Globals.g_play=false;
		
		GameAction ga=getKeyAction(keyCode);
		if(ga!=null)
		{
			ga.press();
		}
		e.consume();
	}
	
	public void keyReleased(KeyEvent e)
	{
		GameAction ga=getKeyAction(e.getKeyCode());
		if(ga!=null)
		{
			ga.release();
		}
		e.consume();
	}
	
	public void keyTyped(KeyEvent e)
	{
		e.consume();
	}
}