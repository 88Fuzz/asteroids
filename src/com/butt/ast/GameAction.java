package com.butt.ast;

public class GameAction
{
	//returns true for as long as the key is held down
	public static final int NORMAL=0;
	public static final int DETECT_INITIAL_ONLY=1;
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
	
	public synchronized void press()
	{
		if(state != WAIT_FOR_RELEASE)
		{
			state=PRESSED;
		}
	}
	
	public synchronized void release()
	{
		state=RELEASED;
	}
	
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
