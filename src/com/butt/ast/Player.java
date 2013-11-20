package com.butt.ast;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;

//contains the player's ship
public class Player extends Sprite
{
	public static final boolean LEFT=false;
	public static final boolean RIGHT=true;
	private double vVelocity;
	private double friction;
	private Bullet b1;
	
	public Player(String imgLoc)
	{
		super(imgLoc);
		vRotate=Globals.g_playervRotate;
		vVelocity=Globals.g_playervVelocity;
		friction=Globals.g_playerFriction;
		b1=new Bullet(Globals.p1Bullet, 0, 0, 0);
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
		b1.set_x(x+img.getWidth()/2);
		b1.set_y(y+img.getHeight()/2);
		
		b1.set_vx((Globals.g_bulletMaxSpeed+vVelocity)*Math.sin(Math.toRadians(rotate)));
		b1.set_vy((Globals.g_bulletMaxSpeed+vVelocity)*-Math.cos(Math.toRadians(rotate)));
	}
	
	public void checkEdges()
	{
		if(x<0)
			x=Globals.WIDTH;
		else if(x>Globals.WIDTH)
			x=0;
		
		if(y<0)
			y=Globals.HEIGHT;
		else if(y>Globals.HEIGHT)
			y=0;
		
		if(rotate>360)
			rotate=0;
		else if(rotate<-360)
			rotate=0;
		
		b1.checkEdges();
	}
	
	public void updatePos()
	{
		x+=vx;
		y+=vy;
		b1.updatePos();
	}
	
	public Bullet getBullet()
	{
		return b1;
	}
	
	public void draw(Graphics2D g)
	{
		BufferedImageOp ops = null;
		AffineTransform tx=AffineTransform.getRotateInstance(Math.toRadians(rotate), img.getWidth()/2, img.getHeight()/2);
		AffineTransformOp op=new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		
		g.drawImage(op.filter(img, null), ops, (int)Math.round(x), (int)Math.round(y));
		g.drawImage(b1.getImage(), ops, (int)b1.get_x(), (int)b1.get_y());
	}
}
