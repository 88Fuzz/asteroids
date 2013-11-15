package com.butt.ast;

public class BaseClass
{
	private int x;
	private int y;
	private int vx;
	private int vy;
	private int g;
	
	public int get_x()
	{
		return this.x;
	}
	
	public int get_y()
	{
		return this.y;
	}
	
	public void set_x(int val)
	{
		this.x=val;
	}
	
	public void set_y(int val)
	{
		this.y=val;
	}
	
	public int get_vx()
	{
		return this.vx;
	}
	
	public int get_vy()
	{
		return this.vy;
	}
	
	public void set_vx(int val)
	{
		this.vx=val;
	}
	
	public void set_vy(int val)
	{
		this.vy=val;
	}
	
	public int get_g()
	{
		return this.g;
	}
	
	public int set_g(int val)
	{
		return this.g=val;
	}
	
	public void updateLoc()
	{//TODO SCREEN WRAP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//TODO figure out best way to have gravity enabled!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//TODO	maybe overload this function if it should not be affected by gravity
		this.x=this.x+this.vx;//+g_gravity;
		this.y=this.y+this.vy;//+g_gravity;
	}
}
