package com.butt.ast;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Sprite extends BaseClass
{
	protected Image img;
	
	public Sprite(String imgLoc)
	{
		super();
		img=loadImage(imgLoc);
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
