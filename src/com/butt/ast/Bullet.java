package com.butt.ast;

public class Bullet extends Sprite
{
	private double shotSpeed;
	public Bullet(String imgLoc, double vxShip, double vyShip, double rotation)
	{
		super(imgLoc);
		shotSpeed=Globals.g_bulletMaxSpeed;
		this.vx=shotSpeed*Math.cos(rotation)+vxShip;
		this.vy=shotSpeed*Math.sin(rotation)+vyShip;
	}
}
