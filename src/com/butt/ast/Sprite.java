package com.butt.ast;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite extends BaseClass
{
	protected BufferedImage img;
	
	public Sprite(String imgLoc)
	{
		super();
		loadImage(imgLoc);
	}
	
	private void loadImage(String fileName)
	{
		//return new ImageIcon(fileName).getImage();
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
}
