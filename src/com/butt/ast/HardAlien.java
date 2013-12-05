package com.butt.ast;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.Random;

public class HardAlien extends Alien
{
	public HardAlien(String imgLoc, String bullet)
	{
		super(imgLoc, bullet);
		/*bulletImg=bullet;
		maxSpeed=5;
		generator = new Random();
		genDirFirst();
		genDist();
		genBulletDist();
		bullets=new ArrayList<Bullet>();
		genPos();
		genRespawnTime();
		alive=false;*/
	}
	
	public HardAlien(String imgLoc, String bullet, int hitCode)
	{
		this(imgLoc, bullet);
		this.hitCode=hitCode;
		lives=1;
	}
	
	public void updatePos()
	{
		double tmpDist=Math.sqrt(vx*vx+vy*vy);
		double tmpAngle;
		double dx;
		double dy;
		
		x+=vx;
		y+=vy;
		distCount+=tmpDist;
		bulletWaitCount+=tmpDist;
		
		if(bulletWaitCount>bulletWait)
		{
			genBulletDist();
			
			if(Globals.player1.isAlive())
			{
				//find angle between alien and player1
				dx=x-Globals.player1.get_x();
				dy=y-(Globals.player1.get_y()+Globals.player1.getHeight()/2);
				tmpAngle=-Math.toDegrees(Math.atan(dx/dy));
			
				if(dy<0)
					tmpAngle=-(180-tmpAngle);

			
			bullets.add(new Bullet(bulletImg, x, y,
						img.getWidth()/2, 0, 
						vVelocity, tmpAngle, bullets.size()+1, hitCode));
			}
			
			if(Globals.player2.isAlive())
			{
				dx=x-Globals.player2.get_x();
				dy=y-(Globals.player2.get_y()+Globals.player2.getHeight()/2);
				tmpAngle=-Math.toDegrees(Math.atan(dx/dy));
			
				if(dy<0)
					tmpAngle=-(180-tmpAngle);
			
				bullets.add(new Bullet(bulletImg, x, y,
						img.getWidth()/2, 0, 
						vVelocity, tmpAngle, bullets.size()+1, hitCode));
			}
			
			if(Globals.ralien.isAlive())
			{
				dx=x-Globals.ralien.get_x();
				dy=y-(Globals.ralien.get_y()+Globals.ralien.getHeight()/2);
				tmpAngle=-Math.toDegrees(Math.atan(dx/dy));
			
				if(dy<0)
					tmpAngle=-(180-tmpAngle);
			
				bullets.add(new Bullet(bulletImg, x, y,
						img.getWidth()/2, 0, 
						vVelocity, tmpAngle, bullets.size()+1, hitCode));
			}
		}
		
		if(distCount>dist)
		{
			genDir();
			genDist();
		}
		
		updateBullets();
	}
	
	public void hit()
	{
		alive=false;
		genDirFirst();
		genDist();
		genBulletDist();
		genPos();
		genRespawnTime();
	}
}
