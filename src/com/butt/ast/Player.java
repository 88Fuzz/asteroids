package com.butt.ast;

//contains the player's ship
public class Player extends Sprite
{
	public static final boolean LEFT=false;
	public static final boolean RIGHT=true;
	private double vVelocity;
	private double friction;
	
	public Player(String imgLoc)
	{
		super(imgLoc);
		vRotate=Globals.g_playervRotate;
		vVelocity=Globals.g_playervVelocity;
		friction=Globals.g_playerFriction;
	}
	
	//player is accelerating
	public void thrustOn(long diff)
	{		
		vx+=vVelocity*Math.sin(Math.toRadians(rotate));
		vy+=-vVelocity*Math.cos(Math.toRadians(rotate));
		
		//check speedlimit of ship
		if(vx>Globals.g_player1maxSpeed)
			vx=Globals.g_player1maxSpeed;
		else if(vx<-Globals.g_player1maxSpeed)
			vx=-Globals.g_player1maxSpeed;
		
		if(vy>Globals.g_player1maxSpeed)
			vy=Globals.g_player1maxSpeed;
		else if(vy<-Globals.g_player1maxSpeed)
			vy=-Globals.g_player1maxSpeed;
	}
	
	//activates the friction force to eventually slow down the ship
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
	}
	
	//rotates the ship left or right
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
	
	//shoots a bullet from the ship
	public void shoot(long diff)
	{
		
	}
}
