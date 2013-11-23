package com.butt.ast;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.Random;

public class Alien extends Sprite
{
	private double bulletWait;
	private double bulletWaitCount;
	private String bulletImg;
	private Random generator;
	private int dist;
	private double distCount;
	private Bullet bullet;
	private ArrayList<Bullet> bullets;
	
	public Alien(String imgLoc, String bullet)
	{
		super(imgLoc);
		bulletImg=bullet;
		maxSpeed=5;
		generator = new Random();
		genDir();
		genDist();
		genBulletDist();
		bullets=new ArrayList<Bullet>();
	}
	
	public Alien(String imgLoc, String bullet, double xpos, double ypos)
	{
		this(imgLoc, bullet);
		x=xpos;
		y=ypos;
	}
	
	public void genDir()
	{
		rotate=generator.nextInt(8);//8 different angles
		
		switch((int)rotate)
		{
		case 0: rotate=0;
			break;
		case 1: rotate=45;
			break;
		case 2: rotate=90;
			break;
		case 3: rotate=135;
			break;
		case 4: rotate=180;
			break;
		case 5: rotate=225;
			break;
		case 6: rotate=270;
			break;
		case 7: rotate=315;
			break;
		default: rotate=0;
		}
		
		vx=maxSpeed*Math.sin(Math.toRadians(rotate));
		vy=-maxSpeed*Math.cos(Math.toRadians(rotate));
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
			//find angle between alien and player1
			dx=x-Globals.player1.get_x();
			dy=y-(Globals.player1.get_y()+Globals.player1.getHeight()/2);
			tmpAngle=-Math.toDegrees(Math.atan(dx/dy));
			
			if(dy<0)
				tmpAngle=-(180-tmpAngle);

			genBulletDist();
			bullets.add(new Bullet(bulletImg, x, y,
					img.getWidth()/2, 0, 
					vVelocity, tmpAngle, bullets.size()+1));
		}
		
		if(distCount>dist)
		{
			genDir();
			genDist();
		}
		
		//update bullet positions
		for(int j=0; j<bullets.size(); j++)
		{
			//if bullet has traveled greater than the width of the screen, then updatePosMax will be true
			if(bullets.get(j).updatePosMax())
				bullets.remove(j);			
		}
	}
	
	public void genDist()
	{
		dist=generator.nextInt(Globals.WIDTH+200)+200;
		distCount=0;
	}
	
	//generates random distance at which a bullet will shoot
	public void genBulletDist()
	{
		bulletWait=generator.nextInt(Globals.HEIGHT)+200;
		bulletWaitCount=0;
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
		
		for(Bullet tmp:bullets)
			tmp.checkEdges();
	}
	
	public void draw(Graphics2D g)
	{
		BufferedImageOp ops = null;
		AffineTransform tx=AffineTransform.getRotateInstance(0, img.getWidth()/2, img.getHeight()/2);
		AffineTransformOp op=new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		
		g.drawImage(op.filter(img, null), ops, (int)Math.round(x), (int)Math.round(y));
		for(Bullet tmp:bullets)
			tmp.draw(g);
			//g.drawImage(tmp.getImage(), ops, (int)tmp.get_x(), (int)tmp.get_y());
	}
}
