package com.butt.ast;

public class Player extends Sprite
{
	public static final boolean LEFT=false;
	public static final boolean RIGHT=true;
	
	private double rotate;
	private double velocity;
	private double vRotate;
	private double vVelocity;
	
	public Player(String imgLoc)
	{
		super(imgLoc);
		
		
		
		vRotate=.25;
		vVelocity=.25;
		//vx=(float) .25;
		//vy=(float) .25;
	}
	
	public void thrust(long diff)
	{
		vx=-Math.cos(Math.toRadians(vRotate))*diff*vVelocity;
		System.out.println("vx: "+vx);
		vy=-Math.cos(Math.toRadians(vRotate))*diff*vVelocity;
		System.out.println("vy: "+vy);
		y+=vy;
		System.out.println("y: "+y);
		x+=vx;
		System.out.println("x: "+x);
	}
	
	public void rotate(long diff, boolean dir)
	{
		if(dir==LEFT)
			rotate-=diff*vRotate;
		if(dir==RIGHT)
			rotate+=diff*vRotate;
	}
	
	public double getRotate()
	{
		return rotate;
	}
	
	public void shoot(long diff)
	{
		
	}
}
