package com.butt.ast;

public class BaseClass
{
	protected float x;
	protected float y;
	protected float vx;
	protected float vy;
	protected float g;
	
	public float get_x()
	{
		return this.x;
	}
	
	public float get_y()
	{
		return this.y;
	}
	
	public void set_x(float val)
	{
		this.x=val;
	}
	
	public void set_y(float val)
	{
		this.y=val;
	}
	
	public float get_vx()
	{
		return this.vx;
	}
	
	public float get_vy()
	{
		return this.vy;
	}
	
	public void set_vx(float val)
	{
		this.vx=val;
	}
	
	public void set_vy(float val)
	{
		this.vy=val;
	}
	
	public float get_g()
	{
		return this.g;
	}
	
	public float set_g(float val)
	{
		return this.g=val;
	}
	
	public void updateLoc(long deltaT)
	{//TODO SCREEN WRAP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//TODO figure out best way to have gravity enabled!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//TODO	maybe overload this function if it should not be affected by gravity
		this.x+=this.vx*deltaT;//+g_gravity;
		this.y+=this.vy*deltaT;//+g_gravity;
	}
}
