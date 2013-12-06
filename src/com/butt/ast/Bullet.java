package com.butt.ast;

public class Bullet extends Sprite
{
	private int index;
	private double dist;
	private double radius;
	private double cx;
	private double cy;
	public Bullet(String imgLoc, double xPos, double yPos, double xWidthHalf, double yWidthHalf, double vShip, double rotation, int index, int hitCode)
	{
		super(imgLoc);
		dist=0;
		x=xPos+xWidthHalf;//+Math.sin(Math.toRadians(rotation));
		y=yPos+yWidthHalf;//-Math.cos(Math.toRadians(rotation));
		maxSpeed=8;
		vx=(maxSpeed+vShip)*Math.sin(Math.toRadians(rotation));
		vy=(maxSpeed+vShip)*-Math.cos(Math.toRadians(rotation));
		radius=img.getWidth()/2;
		cy=0;
		cx=0;
		
		this.index=index;
		this.hitCode=hitCode;
	}
	
	public boolean updatePosMax()
	{
		x+=vx;
		y+=vy;
		dist+=Math.sqrt(vx*vx+vy*vy);
		
		cx=x+radius;
		cy=y+radius;
		
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
		double d;
		if(hitCode==Globals.HITPLAYER1N2)
		{
			//check player2
			if(Globals.player2.isAlive()
					&& Globals.player2.getHit_x() < x+radius 
					&& x < Globals.player2.getHit_x()+Globals.player2.getHitWidth()//check the x coordinates
					&& Globals.player2.getHit_y() < y+radius
					&& y < Globals.player2.getHit_y()+Globals.player2.getHitHeight())
			{
				Globals.player2.hit();
				return 1;
			}
			
			//check player1
			if(Globals.player1.isAlive() 
					&& Globals.player1.getHit_x() < x+radius
					&& x < Globals.player1.getHit_x()+Globals.player1.getHitWidth()//check the x coordinates
					&& Globals.player1.getHit_y() < y+radius
					&& y < Globals.player1.getHit_y()+Globals.player1.getHitHeight())
			{
				Globals.player1.hit();
				return 1;
			}
		}
		else if(hitCode==Globals.HITPLAYER1N2NALIEN)
		{
			//check player2
			if(Globals.player2.isAlive() 
					&& Globals.player2.getHit_x() < x+radius
					&& x < Globals.player2.getHit_x()+Globals.player2.getHitWidth()//check the x coordinates
					&& Globals.player2.getHit_y() < y+radius
					&& y < Globals.player2.getHit_y()+Globals.player2.getHitHeight())
			{
				Globals.player2.hit();
				return 1;
			}
			
			//check player1
			if(Globals.player1.isAlive() 
					&& Globals.player1.getHit_x() < x+radius
					&& x < Globals.player1.getHit_x()+Globals.player1.getHitWidth()//check the x coordinates
					&& Globals.player1.getHit_y() < y+radius
					&& y < Globals.player1.getHit_y()+Globals.player1.getHitHeight())
			{
				Globals.player1.hit();
				return 1;
			}
			
			//check alien
			if(Globals.alien.isAlive() 
					&& Globals.alien.getHit_xBody() < x+radius
					&& x < Globals.alien.getHit_xBody()+Globals.alien.getHitWidthBody()//check the x coordinates
					&& Globals.alien.getHit_yBody() < y+radius
					&& y < Globals.alien.getHit_yBody()+Globals.alien.getHitHeightBody())
			{
				Globals.alien.hit();
				return 1;
			}
		}
		else//Globals.HITALLBUTPLAYER1 and Globals.HITALLBUTPLAYER2
		{
			if(hitCode==Globals.HITALLBUTPLAYER1)
			{
				//check player2
				if(Globals.player2.isAlive() 
						&& Globals.player2.getHit_x() < x+radius
						&& x < Globals.player2.getHit_x()+Globals.player2.getHitWidth()//check the x coordinates
						&& Globals.player2.getHit_y() < y+radius 
						&& y < Globals.player2.getHit_y()+Globals.player2.getHitHeight())
				{
					Globals.player2.hit();
					return 100;
				}
			}
			else//Globals.HITALLBUTPLAYER2
			{
				if(Globals.player1.isAlive() 
						&& Globals.player1.getHit_x() < x +radius
						&& x < Globals.player1.getHit_x()+Globals.player1.getHitWidth()//check the x coordinates
						&& Globals.player1.getHit_y() < y+radius 
						&& y < Globals.player1.getHit_y()+Globals.player1.getHitHeight())
				{
					Globals.player1.hit();
					return 100;
				}
			}
			
			//check alien
			if(Globals.alien.isAlive() 
					&& Globals.alien.getHit_xBody() < x+radius
					&& x < Globals.alien.getHit_xBody()+Globals.alien.getHitWidthBody()//check the x coordinates
					&& Globals.alien.getHit_yBody() < y+radius
					&& y < Globals.alien.getHit_yBody()+Globals.alien.getHitHeightBody())
			{
				Globals.alien.hit();
				return 100;
			}
			
			//Check ralien
			if(Globals.ralien.isAlive() 
					&& Globals.ralien.getHit_xBody() < x+radius 
					&& x < Globals.ralien.getHit_xBody()+Globals.ralien.getHitWidthBody()//check the x coordinates
					&& Globals.ralien.getHit_yBody() < y+radius
					&& y < Globals.ralien.getHit_yBody()+Globals.ralien.getHitHeightBody())
			{
				Globals.ralien.hit();
				return 100;	
			}
			
			for(int i = 0; i < Globals.asts.size(); i++)
			{ 
				d = Math.sqrt(((Globals.asts.get(i).getcx()-cx)*(Globals.asts.get(i).getcx()-cx))  + ((Globals.asts.get(i).getcy()-cy)*(Globals.asts.get(i).getcy()-cy))); 
				if( d < (Globals.asts.get(i).getrad() + radius))
				{
					Globals.asts.get(i).hit();
					Globals.asts.get(i).spawnLittles();
					Globals.asts.remove(i);
					return 5;	
				}
			}
			
			for(int k = 0; k < Globals.smasts.size(); k++)
			{ 
				d = Math.sqrt(((Globals.smasts.get(k).getcx()-cx)*(Globals.smasts.get(k).getcx()-cx))  + ((Globals.smasts.get(k).getcy()-cy)*(Globals.smasts.get(k).getcy()-cy))); 
				if( d < (Globals.smasts.get(k).getrad() + radius))
				{	
					Globals.smasts.get(k).hit();
					Globals.smasts.remove(k);
					return 5;	
				}
			}

		}

		return 0;
	}
}
