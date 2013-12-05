package com.butt.ast;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.Random;

public class Asteroids extends Sprite
{

	private Random generator;
	private double cx; 
	private double cy;
	private double radius; 
	
	public Asteroids(String imgLoc)
	{
		super(imgLoc);
		maxSpeed=5;
		generator = new Random();
		genPos();
		setxy();
		genDir(); 
		alive=true;
		radius = img.getWidth()/2;
		cx = 0; 
		cy = 0; 
		
	}
	
	public void genDir()
	{
		rotate=generator.nextInt(360);
		
		vx=maxSpeed*Math.sin(Math.toRadians(rotate));
		vy=-maxSpeed*Math.cos(Math.toRadians(rotate));
	}
	
	public void updatePos()
	{
		x+=vx;
		y+=vy;
		cx = x + radius; 
		cy = y + radius; 
		
	}
	
	public double get_cx()
	{
		return cx;
	}
	public double get_cy()
	{
		return cy;
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
		

	}
	
	public void draw(Graphics2D g)
	{
		BufferedImageOp ops = null;
		AffineTransform tx=AffineTransform.getRotateInstance(0, img.getWidth()/2, img.getHeight()/2);
		AffineTransformOp op=new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		
		g.drawImage(op.filter(img, null), ops, (int)Math.round(x), (int)Math.round(y));		
	}
	
	//gets the x position of hitbox
	public int getHit_xBody()
	{
		return (int)Math.round(x);
	}
		
	//gets the y position of hitbox
	public int getHit_yBody()
	{
		return (int)Math.round(y)+7;
	}
	
	//return hitbox width
	public int getHitWidthBody()
	{
		return 30;
	}
	
	//return hitbox height
	public int getHitHeightBody()
	{
		return 8;
	}
	
	public void genPos()
	{
		x= generator.nextInt(Globals.HEIGHT);
		y=generator.nextInt(Globals.HEIGHT);
	}
	
	
	
	public void hit()
	{
		alive=false;

		genPos();
	}
	
	
	public void setxy()
	{
		x = generator.nextInt(Globals.WIDTH); 
		y = generator.nextInt(Globals.HEIGHT);
	}
	
	public double getrad()
	{	
		return radius; 
	}
	public void spawnLittles()
	{
		for (int k = 0; k < 3; k++)
		{
            Globals.smasts.add(new Smallasteroids(Globals.smallAst, cx, cy));
        }
	}
	
	public double getcx()
	{
		return cx; 
	}
	
	public double getcy()
	{
		return cy; 
	}
	
	public static void addast(int astcount)
	{
		for (int i = 0; i < astcount; i++){
			Globals.asts.add(new Asteroids(Globals.bigAst));
			}
	}
}
