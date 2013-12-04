package com.butt.ast;

public class Gravity extends Sprite
{
	//private boolean enable;
	private boolean visible;
	private double gravityForce;
	private int centerx;
	private int centery;
	
	Gravity(String imgLoc)
	{
		super(imgLoc);
		//enable=true;
		alive=true;
		visible=true;
		gravityForce=-.05;
		x=Globals.WIDTH/2-15;
		y=Globals.HEIGHT/2-15;
		
		centerx=Globals.WIDTH/2;
		centery=Globals.HEIGHT/2;
	}
	
	public void setAlive()
	{
		alive=true;
	}
	
	public void setVisible(boolean val)
	{
		visible=val;
	}
	
	public boolean getVisible()
	{
		return visible;
	}
	
	public void moveShips(long diff)
	{
		double dx;
		double dy;
		double tmpAngle;
		if(alive)
		{
			if(Globals.player1.isAlive())
			{
				dx=centerx-Globals.player1.get_x();
				dy=centery-(Globals.player1.get_y()+Globals.player1.getHeight()/2);
				tmpAngle=-Math.toDegrees(Math.atan(dx/dy));
		
				if(dy<0)
					tmpAngle=-(180-tmpAngle);
			
				dx=gravityForce*Math.sin(Math.toRadians(tmpAngle));
				dy=-gravityForce*Math.cos(Math.toRadians(tmpAngle));
			
				Globals.player1.gravityPull(dx, dy);
			}
		
		
			if(Globals.player2.isAlive())
			{
				dx=centerx-Globals.player2.get_x();
				dy=centery-(Globals.player2.get_y()+Globals.player2.getHeight()/2);
				tmpAngle=-Math.toDegrees(Math.atan(dx/dy));
		
				if(dy<0)
					tmpAngle=-(180-tmpAngle);
			
				dx=gravityForce*Math.sin(Math.toRadians(tmpAngle));
				dy=-gravityForce*Math.cos(Math.toRadians(tmpAngle));
			
				Globals.player2.gravityPull(dx, dy);
			}
		}
	}
}