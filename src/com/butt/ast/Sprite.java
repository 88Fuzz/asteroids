package com.butt.ast;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Sprite extends BaseClass
{
	private Image img;
	
	public Sprite(String imgLoc)
	{
		img=loadImage(imgLoc);
		x=100;
		y=100;
	}
	
	private Image loadImage(String fileName)
	{
		return new ImageIcon(fileName).getImage();
	}
	
	public Image getImage()
	{
		return img;
	}
}
