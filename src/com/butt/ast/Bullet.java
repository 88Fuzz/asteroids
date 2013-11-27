package com.butt.ast;

public class Bullet extends Sprite
{
	private int index;
	private double dist;
	public Bullet(String imgLoc, double xPos, double yPos, double xWidthHalf, double yWidthHalf, double vShip, double rotation, int index, int hitCode)
	{
		super(imgLoc);
		dist=0;
		x=xPos+xWidthHalf;//+Math.sin(Math.toRadians(rotation));
		y=yPos+yWidthHalf;//-Math.cos(Math.toRadians(rotation));
		maxSpeed=8;
		vx=(maxSpeed+vShip)*Math.sin(Math.toRadians(rotation));
		vy=(maxSpeed+vShip)*-Math.cos(Math.toRadians(rotation));
		
		this.index=index;
		this.hitCode=hitCode;
	}
	
	public boolean updatePosMax()
	{
		x+=vx;
		y+=vy;
		dist+=Math.sqrt(vx*vx+vy*vy);
		
		if(dist>Globals.WIDTH)
			return true;
		return false;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public int checkHits()
	{
		if(hitCode==Globals.HITALLBUTPLAYER1)
		{
			//check player2
			if(Globals.player2.isAlive() && Globals.player2.getHit_x() < x && x < Globals.player2.getHit_x()+Globals.player2.getHitWidth()//check the x coordinates
					&& Globals.player2.getHit_y() < y && y < Globals.player2.getHit_y()+Globals.player2.getHitHeight())
			{
				Globals.player2.hit();
				return 100;
			}
			
			//check alien
			if(Globals.alien.isAlive() && Globals.alien.getHit_xBody() < x && x < Globals.alien.getHit_xBody()+Globals.alien.getHitWidthBody()//check the x coordinates
					&& Globals.alien.getHit_yBody() < y && y < Globals.alien.getHit_yBody()+Globals.alien.getHitHeightBody())
			{
				Globals.alien.hit();
				return 100;
			}
		}
		else if(hitCode==Globals.HITALLBUTPLAYER2)
		{
			//check player1
			if(Globals.player1.isAlive() && Globals.player1.getHit_x() < x && x < Globals.player1.getHit_x()+Globals.player1.getHitWidth()//check the x coordinates
					&& Globals.player1.getHit_y() < y && y < Globals.player1.getHit_y()+Globals.player1.getHitHeight())
			{
				Globals.player1.hit();
				return 100;
			}
			
			//check alien
			if(Globals.alien.isAlive() && Globals.alien.getHit_xBody() < x && x < Globals.alien.getHit_xBody()+Globals.alien.getHitWidthBody()//check the x coordinates
					&& Globals.alien.getHit_yBody() < y && y < Globals.alien.getHit_yBody()+Globals.alien.getHitHeightBody())
			{
				Globals.alien.hit();
				return 100;
			}
		}
		else if(hitCode==Globals.HITPLAYER1N2)
		{
			//check player2
			if(Globals.player2.isAlive() && Globals.player2.getHit_x() < x && x < Globals.player2.getHit_x()+Globals.player2.getHitWidth()//check the x coordinates
					&& Globals.player2.getHit_y() < y && y < Globals.player2.getHit_y()+Globals.player2.getHitHeight())
			{
				Globals.player2.hit();
				return 1;
			}
			
			//check player1
			if(Globals.player1.isAlive() && Globals.player1.getHit_x() < x && x < Globals.player1.getHit_x()+Globals.player1.getHitWidth()//check the x coordinates
					&& Globals.player1.getHit_y() < y && y < Globals.player1.getHit_y()+Globals.player1.getHitHeight())
			{
				Globals.player1.hit();
				return 1;
			}
		}
		return 0;
	}
}
