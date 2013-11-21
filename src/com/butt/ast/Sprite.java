package com.butt.ast;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//An object that is drawn on screen
public class Sprite extends BaseClass
{
	protected BufferedImage img;
	
	public Sprite(String imgLoc)
	{
		super();
		loadImage(imgLoc);
	}
	
	public Sprite(String imgLoc, double xpos, double ypos)
	{
		super();
		loadImage(imgLoc);
		x=xpos;
		y=ypos;
	}
	
	private void loadImage(String fileName)
	{
		try
		{
			img=ImageIO.read(new File(fileName));
		}
		catch (IOException e) {	}
	}
	
	public BufferedImage getImage()
	{
		return img;
	}
	
	public double getWidth()
	{
		return img.getWidth();
	}
	
	public double getHeight()
	{
		return img.getHeight();
	}
	
	public void draw(Graphics2D g)
	{
		BufferedImageOp ops = null;
		AffineTransform tx=AffineTransform.getRotateInstance(0, img.getWidth()/2, img.getHeight()/2);
		AffineTransformOp op=new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		
		g.drawImage(op.filter(img, null), ops, (int)Math.round(x), (int)Math.round(y));
	}
}
