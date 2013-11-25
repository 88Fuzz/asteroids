package com.butt.ast;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

//contains the player's ship
public class Player extends Sprite
{
	public static final boolean LEFT=false;
	public static final boolean RIGHT=true;
	public static final int bulletDelay=75;
	public static final int burstDelay=400;
	private static final int MAXBULLETS=30;
	private static final int HITWIDTH=20;
	private static final int HITHEIGHT=20;
	
	
	private double friction;
	private ArrayList<Bullet> bullets;
	private int numBulletsBurst;
	private int bulletDelayDiff;
	private int burstDelayDiff;
	private String bulletImg;
	private boolean burstWait;
	private int spawnCnt;
	private int spawnTime;
	private double xSpawn;
	private double ySpawn;
	
	public Player(String imgLoc, String bullet)
	{
		super(imgLoc);
		bulletImg=bullet;
		maxSpeed=5.0;
		vRotate=.25;
		vVelocity=.8;
		friction=.00004;
		bulletDelayDiff=bulletDelay;
		burstDelayDiff=burstDelay;
		numBulletsBurst=0;
		spawnTime=10000;//swt spawntime to two seconds
		bullets=new ArrayList<Bullet>();
		burstWait=false;
	}
	
	public Player(String imgLoc, String bullet, double xpos, double ypos, int hitCode)
	{
		this(imgLoc, bullet);
		x=xpos;
		y=ypos;
		xSpawn=xpos;
		ySpawn=ypos;
		this.hitCode=hitCode;
	}
	
	//player is accelerating
	public void thrustOn(long diff)
	{
		vx+=vVelocity*Math.sin(Math.toRadians(rotate));
		vy+=-vVelocity*Math.cos(Math.toRadians(rotate));
		
		//check speedlimit of ship
		if(vx>maxSpeed)
			vx=maxSpeed;
		else if(vx<-maxSpeed)
			vx=-maxSpeed;
		
		if(vy>maxSpeed)
			vy=maxSpeed;
		else if(vy<-maxSpeed)
			vy=-maxSpeed;
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
	public void shootOn(long diff)
	{
		bulletDelayDiff+=diff;
		burstDelayDiff+=diff;
		
		if(burstWait && burstDelayDiff>burstDelay)
		{
			burstDelayDiff=0;
			burstWait=false;
		}
		
		if(bulletDelayDiff>bulletDelay && !burstWait)
		{
			bulletDelayDiff=0;
			bullets.add(new Bullet(bulletImg, x, y ,
									img.getWidth()/2, img.getHeight()/2, 
									vVelocity, rotate, bullets.size()+1, hitCode));
			numBulletsBurst++;
		}
		
		
		//limits the number of bullets that can be on screen
		if(bullets.size()>MAXBULLETS)
			bullets.remove(0);
		
		if(numBulletsBurst==4)
		{
			burstWait=true;
			numBulletsBurst=0;
			burstDelayDiff=0;
		}
	}
	
	public void shootOff(long diff)
	{
		bulletDelayDiff=600;
		burstDelayDiff=0;
		numBulletsBurst=0;
		burstWait=false;
	}
	
	public void checkEdges()
	{
		if(Globals.wrapObjs)
		{
			if(x<0)
				x=Globals.WIDTH;
			else if(x>Globals.WIDTH)
				x=0;
		
			if(y<0)
				y=Globals.HEIGHT;
			else if(y>Globals.HEIGHT)
				y=0;
		}
		else
		{
			if(x<0)
			{
				x=0;
				vx*=-1;
			}
			else if(x>Globals.WIDTH)
			{
				x=Globals.WIDTH;
				vx*=-1;
			}
		
			if(y<0)
			{
				y=0;
				vy*=-1;
			}
			else if(y>Globals.HEIGHT)
			{
				y=Globals.HEIGHT;
				vy*=-1;
			}
		}
		
		if(rotate>360)
			rotate=0;
		else if(rotate<-360)
			rotate=0;
		
		for(Bullet tmp:bullets)
			tmp.checkEdges();
	}
	
	public void gravityPull(double dx, double dy)
	{
		vx+=dx;
		vy+=dy;
	}
	
	public void updatePos()
	{
		x+=vx;
		y+=vy;
		
		//update bullet positions
		updateBullets();
	}
	
	public void updateBullets()
	{
		//update bullet positions
		for(int j=0; j<bullets.size(); j++)
		{
			if(bullets.get(j).checkHits())
			{
				bullets.remove(j);
			}
			//if bullet has traveled greater than the width of the screen, then updatePosMax will be true
			else if(bullets.get(j).updatePosMax())
				bullets.remove(j);			
		}
	}
	
	//gets the x position of hitbox
	public int getHit_x()
	{
		return (int)Math.round(x)+5;
	}
	
	//gets the y position of hitbox
	public int getHit_y()
	{
		return (int)Math.round(y)+5;
	}
	
	//return hitbox width
	public int getHitWidth()
	{
		return HITWIDTH;
	}
	
	//return hitbox height
	public int getHitHeight()
	{
		return HITHEIGHT;
	}
	
	public void checkSpawn(long diff)
	{
		spawnCnt+=diff;
		if(spawnCnt>spawnTime)
			alive=true;
		
		updateBullets();
	}
	
	public void draw(Graphics2D g)
	{
		BufferedImageOp ops = null;
		AffineTransform tx=AffineTransform.getRotateInstance(Math.toRadians(rotate), img.getWidth()/2, img.getHeight()/2);
		AffineTransformOp op=new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		
		if(alive)
			g.drawImage(op.filter(img, null), ops, (int)Math.round(x), (int)Math.round(y));
		
		for(Bullet tmp:bullets)
			tmp.draw(g);
			//g.drawImage(tmp.getImage(), ops, (int)tmp.get_x(), (int)tmp.get_y());
	}
	
	public void hit()
	{
		alive=false;
		x=xSpawn;
		y=ySpawn;
		rotate=0;
		vx=0;
		vy=0;
		spawnCnt=0;
	}
}
