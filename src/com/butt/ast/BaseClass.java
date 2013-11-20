package com.butt.ast;


//Base class that will be the super class of all objects that move
public class BaseClass
{
	protected double x;
	protected double y;
	//x velocity, pixels per millimeter
	protected double vx;
	//y velocity, pixels per millimeter
	protected double vy;
	protected double g;
	protected double rotate;
	protected double vRotate;
	
	public double get_x()
	{
		return this.x;
	}
	
	public double get_y()
	{
		return this.y;
	}
	
	public void set_x(double val)
	{
		this.x=val;
	}
	
	public void set_y(double val)
	{
		this.y=val;
	}
	
	public double get_vx()
	{
		return this.vx;
	}
	
	public double get_vy()
	{
		return this.vy;
	}
	
	public void set_vx(double val)
	{
		this.vx=val;
	}
	
	public void set_vy(double val)
	{
		this.vy=val;
	}
	
	public double get_g()
	{
		return this.g;
	}
	
	public double set_g(double val)
	{
		return this.g=val;
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
		
		if(rotate>360)
			rotate=0;
		else if(rotate<-360)
			rotate=0;
	}
	
	public void updatePos()
	{
		x+=vx;
		y+=vy;
	}
	
/*	public void updateLoc(long deltaT)
	{//TODO SCREEN WRAP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//TODO figure out best way to have gravity enabled!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//TODO	maybe overload this function if it should not be affected by gravity
		this.x+=this.vx*deltaT;//+g_gravity;
		this.y+=this.vy*deltaT;//+g_gravity;
	}*/
}
