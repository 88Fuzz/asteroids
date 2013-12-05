package com.butt.ast;

public class Gravity extends Sprite
{
	//private boolean enable;
	private boolean visible;
	private double gravityForce;
	private boolean killShips;
	private int centerx;
	private int centery;
	private int radius;
	
	Gravity(String imgLoc)
	{
		super(imgLoc);
		//enable=true;
		alive=false;
		visible=true;
		killShips=true;
		gravityForce=-.05;
		x=Globals.WIDTH/2-15;
		y=Globals.HEIGHT/2-15;
		
		centerx=Globals.WIDTH/2;
		centery=Globals.HEIGHT/2;
		radius=img.getHeight()/2;
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
				
				if(killShips && (Globals.player1.getHit_x() < centerx //(x < cx < x+w) and (y-r < cy < y+h+r)
						&& centerx < Globals.player1.getHitWidthSum()
						&& Globals.player1.getHit_y()-radius < centery
						&& centery < Globals.player1.getHitHeightSum()+radius)
						||
						(Globals.player1.getHit_y() < centery//(y < cy < y+h) and (x-r < cx < x+w+r)
						&& centery < Globals.player1.getHitHeightSum()
						&& Globals.player1.getHit_x() - radius < centerx
						&& centerx < Globals.player1.getHitWidthSum()+radius))
				{
					Globals.player1.hit();
				}
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
				
				if(killShips && (Globals.player2.getHit_x() < centerx //(x < cx < x+w) and (y-r < cy < y+h+r)
						&& centerx < Globals.player2.getHitWidthSum()
						&& Globals.player2.getHit_y()-radius < centery
						&& centery < Globals.player2.getHitHeightSum()+radius)
						||
						(Globals.player2.getHit_y() < centery//(y < cy < y+h) and (x-r < cx < x+w+r)
						&& centery < Globals.player2.getHitHeightSum()
						&& Globals.player2.getHit_x() - radius < centerx
						&& centerx < Globals.player2.getHitWidthSum()+radius))
				{
					Globals.player2.hit();
				}
			}
		}
	}
}