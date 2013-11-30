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
	}
}
