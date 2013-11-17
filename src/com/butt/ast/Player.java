package com.butt.ast;

public class Player extends Sprite
{
	public static final boolean LEFT=false;
	public static final boolean RIGHT=true;
	
	public Player(String imgLoc)
	{
		super(imgLoc);
		vx=(float) .25;
		vy=(float) .25;
	}
	
	public void thrust(long diff)
	{
		y-=diff*vy;
		if(y<0)
			y=Globals.HEIGHT;
		else if(y>Globals.HEIGHT)
			y=0;
	}
	
	public void rotate(long diff, boolean dir)
	{
		if(dir==LEFT)
			x-=diff*vx;
		if(dir==RIGHT)
			x+=diff*vx;
		
		if(x<0)
			x=Globals.WIDTH;
		else if(x>Globals.WIDTH)
			x=0;
	}
	public void shoot(long diff)
	{
		
	}
}
