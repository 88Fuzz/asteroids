package com.butt.ast;

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
		x+=vx;
		y+=vy;
		distCount+=Math.sqrt(vx*vx+vy*vy);
		
		if(distCount>dist)
		{
			genDir();
			genDist();
		}
	}
	
	public void genDist()
	{
		dist=generator.nextInt(Globals.WIDTH+200)+200;
		distCount=0;
	}
}
