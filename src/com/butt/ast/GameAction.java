package com.butt.ast;

//contains information about user input
public class GameAction
{
	//returns true for as long as the key is held down
	public static final int NORMAL=0;
	//only return true the first time the key is hit
	public static final int DETECT_INITIAL_ONLY=1;
	//key press states
	private static final int RELEASED=0;
	private static final int PRESSED=1;
	private static final int WAIT_FOR_RELEASE=2;
	
	private String name;
	private int type;
	private int state;
	
	public GameAction(String name)
	{
		this(name, NORMAL);
	}
	
	public GameAction(String name, int type)
	{
		this.name=name;
		this.type=type;
		this.state=RELEASED;
	}
	
	public String getName()
	{
		return name;
	}
	
	//sets the state of the key to PRESSED, if the key should only detected once, it is not set to PRESSED
	public synchronized void press()
	{
		if(state != WAIT_FOR_RELEASE)
		{
			state=PRESSED;
		}
	}
	
	//sets state of key to RELEASED
	public synchronized void release()
	{
		state=RELEASED;
	}
	
	//gets state of key and sets the state to WAITING FOR RELEASE if it should only be detected once
	public synchronized boolean isPressed()
	{
		if(state==PRESSED)
		{
			if(type==DETECT_INITIAL_ONLY)
				state=WAIT_FOR_RELEASE;
			return true;
		}
		return false;
	}
	
	
}
