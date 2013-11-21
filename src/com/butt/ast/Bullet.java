package com.butt.ast;

public class Bullet extends Sprite
{
	private int index;
	private double dist;
	public Bullet(String imgLoc, double xPos, double yPos, double vShip, double rotation, int index)
	{
		super(imgLoc);
		dist=0;
		x=xPos;
		y=yPos;
		maxSpeed=8;
		vx=(maxSpeed+vShip)*Math.sin(Math.toRadians(rotation));
		vy=(maxSpeed+vShip)*-Math.cos(Math.toRadians(rotation));
		
		this.index=index;
	}
	
	public boolean updatePosMax()
	{
		x+=vx;
		y+=vy;
		dist+=Math.sqrt(vx*vx+vy*vy);
		
		if(dist>Globals.WIDTH)
			return true;
		return false;
	}
	
	public int getIndex()
	{
		return index;
	}
}
