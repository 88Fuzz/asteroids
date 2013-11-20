package com.butt.ast;

public class Player extends Sprite
{
	public static final boolean LEFT=false;
	public static final boolean RIGHT=true;
	
	//private double rotate;
	//private double velocity;
	//private double vRotate;
	private double vVelocity;
	private double friction;
	
	public Player(String imgLoc)
	{
		super(imgLoc);
		
		
		
		vRotate=Globals.g_playervRotate;
		vVelocity=Globals.g_playervVelocity;
		friction=Globals.g_playerFriction;
		//vx=(float) .25;
		//vy=(float) .25;
	}
	
	public void thrustOn(long diff)
	{
		//velocity+=vVelocity*diff-friction*diff;
		
		vx+=vVelocity*Math.sin(Math.toRadians(rotate));
		vy+=-vVelocity*Math.cos(Math.toRadians(rotate));
		
		if(vx>Globals.g_player1maxSpeed)
			vx=Globals.g_player1maxSpeed;
		else if(vx<-Globals.g_player1maxSpeed)
			vx=-Globals.g_player1maxSpeed;
		
		if(vy>Globals.g_player1maxSpeed)
			vy=Globals.g_player1maxSpeed;
		else if(vy<-Globals.g_player1maxSpeed)
			vy=-Globals.g_player1maxSpeed;
		
		
		
		//y+=vy;
		//x+=vx;
	}
	
	public void thrustOff(long diff)
	{
		if(vx<0)
			vx+=diff*friction;
		else
			vx-=diff*friction;
		
		if(vy<0)
			vy+=diff*friction;
		else
			vy-=diff*friction;
		
		//velocity-=friction*diff;
		/*vx-=friction*diff;
		vy-=friction*diff;
		System.out.println("vx: "+vx);
		System.out.println("vy: "+vy);
		
		if(vx<-Globals.g_player1maxSpeed)
			vx=-Globals.g_player1maxSpeed;
		
		if(vy<-Globals.g_player1maxSpeed)
			vy=-Globals.g_player1maxSpeed;
		*/
		//if(vx<0)
		//	vx=0;
		//if(vy<0)
		//	vy=0;
		
		//if(velocity>Globals.g_player1maxSpeed)
		//	velocity=Globals.g_player1maxSpeed;
		
		//if(velocity<0)
		//	velocity=0;
		
		
		//vx=velocity*Math.sin(Math.toRadians(rotate));
		//vy=-velocity*Math.cos(Math.toRadians(rotate));
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
