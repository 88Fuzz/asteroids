package com.butt.ast;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

//contains the player's ship
public class Player extends Sprite
{
	public static final boolean LEFT=false;
	public static final boolean RIGHT=true;
	public static final int bulletDelay=75;
	public static final int burstDelay=400;
	private static final int MAXBULLETS=30;
	private static final int SIDEHITWIDTH=5;
	private static final int HITWIDTH=20;
	private static final int HITHEIGHT=20;
	
	
	private double friction;
	private ArrayList<Bullet> bullets;
	private int numBulletsBurst;
	private int bulletDelayDiff;
	private int burstDelayDiff;
	private String bulletImg;
	private BufferedImage lifeImg;
	private boolean burstWait;
	private int spawnCnt;
	private int spawnTime;
	private double xSpawn;
	private double ySpawn;
	private int xscoreLoc;
	private int score;
	private Color scoreColor;
	private boolean neverAlive;
	private int lives;
	private boolean showScore;
	private int playerNum;
	Sound hitSound;
	Sound thrustSound;
	
	public Player(String imgLoc, String bullet, String imgLife)
	{
		super(imgLoc);
		bulletImg=bullet;
		maxSpeed=5.0;
		vRotate=.25;
		vVelocity=.8;
		friction=.00004;
		bulletDelayDiff=bulletDelay;
		burstDelayDiff=burstDelay;
		numBulletsBurst=0;
		spawnTime=1000;//set spawntime to two seconds
		bullets=new ArrayList<Bullet>();
		burstWait=false;
		neverAlive=false;
		showScore=true;
		score=0;
		lives= 3;
		
		try
		{
			lifeImg=ImageIO.read(new File(imgLife));
		}
		catch (IOException e) {	}
	}
	
	public void increaseScore(int val)
	{
		score+=val;
	}
	
	public Player(String imgLoc, String bullet, double xpos, double ypos, 
					int hitCode, int scoreLoc, Color scoreColor,
					String imgLife, int player)
	{
		this(imgLoc, bullet, imgLife);
		x=xpos;
		y=ypos;
		xSpawn=xpos;
		ySpawn=ypos;
		this.hitCode=hitCode;
		this.scoreColor=scoreColor;
		xscoreLoc=scoreLoc;
		playerNum=player;
	}
	
	public void setNeverAlive()
	{
		neverAlive=true;
		showScore=false;
		lives=0;
	}
	
	//player is accelerating
	public void thrustOn(long diff)
	{
		thrustSound=new Sound(Globals.thrustSoundStr);
		thrustSound.start();
		
		vx+=vVelocity*Math.sin(Math.toRadians(rotate));
		vy+=-vVelocity*Math.cos(Math.toRadians(rotate));
		
		//check speedlimit of ship
		if(vx>maxSpeed)
			vx=maxSpeed;
		else if(vx<-maxSpeed)
			vx=-maxSpeed;
		
		if(vy>maxSpeed)
			vy=maxSpeed;
		else if(vy<-maxSpeed)
			vy=-maxSpeed;
	}
	
	public int getLives()
	{
		return lives;
	}
	
	//activates the friction force to eventually slow down the ship
	public void thrustOff(long diff)
	{
		if(vx<0)
			vx+=diff*friction;
		else
			vx-=diff*friction;
		
		if(vy<0)
			vy+=diff*friction;
		else
			vy-=diff*friction;
	}
	
	//rotates the ship left or right
	public void rotate(long diff, boolean dir)
	{
		if(dir==LEFT)
			rotate-=diff*vRotate;
		if(dir==RIGHT)
			rotate+=diff*vRotate;
	}
	
	public double getRotate()
	{
		return rotate;
	}
	
	//shoots a bullet from the ship
	public void shootOn(long diff)
	{
		bulletDelayDiff+=diff;
		burstDelayDiff+=diff;
		
		if(burstWait && burstDelayDiff>burstDelay)
		{
			burstDelayDiff=0;
			burstWait=false;
		}
		
		if(bulletDelayDiff>bulletDelay && !burstWait)
		{
			bulletDelayDiff=0;
			bullets.add(new Bullet(bulletImg, x, y ,
									img.getWidth()/2, img.getHeight()/2, 
									vVelocity, rotate, bullets.size()+1, hitCode));
			numBulletsBurst++;
		}
		
		
		//limits the number of bullets that can be on screen
		if(bullets.size()>MAXBULLETS)
			bullets.remove(0);
		
		if(numBulletsBurst==4)
		{
			burstWait=true;
			numBulletsBurst=0;
			burstDelayDiff=0;
		}
	}
	
	public void shootOff(long diff)
	{
		bulletDelayDiff=600;
		burstDelayDiff=0;
		numBulletsBurst=0;
		burstWait=false;
	}
	
	public void checkEdges()
	{
		if(Globals.wrapObjs)
		{
			if(x<0)
				x=Globals.WIDTH;
			else if(x>Globals.WIDTH)
				x=0;
		
			if(y<0)
				y=Globals.HEIGHT;
			else if(y>Globals.HEIGHT)
				y=0;
		}
		else
		{
			if(x<0)
			{
				x=0;
				vx*=-1;
			}
			else if(x>Globals.WIDTH)
			{
				x=Globals.WIDTH;
				vx*=-1;
			}
		
			if(y<0)
			{
				y=0;
				vy*=-1;
			}
			else if(y>Globals.HEIGHT)
			{
				y=Globals.HEIGHT;
				vy*=-1;
			}
		}
		
		if(rotate>360)
			rotate=0;
		else if(rotate<-360)
			rotate=0;
		
		for(Bullet tmp:bullets)
			tmp.checkEdges();
	}
	
	public void gravityPull(double dx, double dy)
	{
		vx+=dx;
		vy+=dy;
	}
	
	public void updatePos()
	{
		x+=vx;
		y+=vy;

		checkCollisions();
		
		//update bullet positions
		updateBullets();
	}
	
	public void updateBullets()
	{
		int tmp=0;
		//update bullet positions
		for(int j=0; j<bullets.size(); j++)
		{
			if((tmp=bullets.get(j).checkHits())>0)
			{
				score+=tmp;
				bullets.remove(j);
			}
			//if bullet has traveled greater than the width of the screen, then updatePosMax will be true
			else if(bullets.get(j).updatePosMax())
				bullets.remove(j);			
		}
	}
	
	//gets the x position of hitbox
	public int getHit_x()
	{
		return (int)Math.round(x)+SIDEHITWIDTH;
	}
	
	//gets the y position of hitbox
	public int getHit_y()
	{
		return (int)Math.round(y)+SIDEHITWIDTH;
	}
	
	//return hitbox width
	public int getHitWidth()
	{
		return HITWIDTH;
	}
	
	public int getHitWidthSum()
	{
		return HITWIDTH+getHit_x(); 
	}
	
	//return hitbox height
	public int getHitHeight()
	{
		return HITHEIGHT;
	}
	
	public int getHitHeightSum()
	{
		return HITHEIGHT+getHit_y();
	}
	
	public void checkSpawn(long diff)
	{
		spawnCnt+=diff;
		if(spawnCnt>spawnTime)
			alive=true;
		
		updateBullets();
	}
	
	public void setLives(int lifes)
	{
		lives = lifes; 
	}
	
	public void draw(Graphics2D g)
	{
		BufferedImageOp ops = null;
		AffineTransform tx=AffineTransform.getRotateInstance(Math.toRadians(rotate), 
				img.getWidth()/2, img.getHeight()/2);
		AffineTransform tx2;
		AffineTransformOp op=new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
		int i;
		
		if(alive && !neverAlive)
			g.drawImage(op.filter(img, null), ops, (int)Math.round(x), (int)Math.round(y));
		
		for(Bullet tmp:bullets)
			tmp.draw(g);
			//g.drawImage(tmp.getImage(), ops, (int)tmp.get_x(), (int)tmp.get_y());
		
		if(showScore)
		{
			g.setColor(scoreColor);
			g.drawString(""+score, xscoreLoc, 25);
			
			tx2=AffineTransform.getRotateInstance(0, img.getWidth()/2, img.getHeight()/2);
			op=new AffineTransformOp(tx2,AffineTransformOp.TYPE_BILINEAR);
			
			for(i=0; i<lives; i++)
			{
				g.drawImage(op.filter(lifeImg, null), ops, 
						xscoreLoc+(lifeImg.getWidth()+lifeImg.getWidth()/2)*i, 35);
			}
		}
	}
	
	public void startNew(int lives, double x, double y, double xvel,
						double yvel, double angle, int scissor)
	{
		this.lives=lives;
		this.x=x;
		this.y=y;
		this.vx=xvel;
		this.vy=yvel;
		this.rotate=angle;
		this.score=scissor;
	}
	
	public void hit()
	{
		hitSound=new Sound(Globals.hitSoundStr);
		hitSound.start();
		alive=false;
		lives--;
		x=xSpawn;
		y=ySpawn;
		rotate=0;
		vx=0;
		vy=0;
		spawnCnt=0;
		if(lives==0)			
			neverAlive=true;
	}
	
	public void addScore(int val)
	{
		score+=val;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public boolean isAlive()
	{
		return (!neverAlive && alive); 
	}
	
	public void setScore(int points)
	{
		score = points; 
	}
	
	private void checkCollisions()
	{
		Iterator<Asteroids> astit=Globals.asts.iterator();
		Iterator<Asteroids> smastit=Globals.smasts.iterator();
		Asteroids tmp;
		Asteroids tmp2;
		
		//TODO make this better, right now it doesn't take into account the ships rotation
		if(playerNum==1 && Globals.player2.isAlive()
				&& Globals.player2.getHit_x() < getHitWidthSum() 
				&& x < Globals.player2.getHitWidthSum()//check the x coordinates
				&& Globals.player2.getHit_y() < getHitHeightSum()
				&& y < Globals.player2.getHitHeightSum())
		{
			Globals.player2.hit();
			hit();
		}
		
		while(smastit.hasNext())
		{
			tmp2=smastit.next();
			if(alive && (getHit_x() < tmp2.get_cx()//(x < cx < x+w) and (y-r < cy < y+h+r)
					&& tmp2.get_cx() < getHitWidthSum()
					&& getHit_y()-tmp2.getrad() < tmp2.get_cy()
					&& tmp2.get_cy() < getHitHeightSum()+tmp2.getrad())
					||
					(getHit_y() < tmp2.get_cy()//(y < cy < y+h) and (x-r < cx < x+w+r)
					&& tmp2.get_cy() < getHitHeightSum()
					&& getHit_x() - tmp2.getrad() < tmp2.get_cx()
					&& tmp2.get_cx() < getHitWidthSum()+tmp2.getrad()))
			{
				smastit.remove();
				hit();
			}
		}
		
		//check big asteroids
		while(astit.hasNext())
		{
			tmp=astit.next();
			if(alive && (getHit_x() < tmp.get_cx()//(x < cx < x+w) and (y-r < cy < y+h+r)
					&& tmp.get_cx() < getHitWidthSum()
					&& getHit_y()-tmp.getrad() < tmp.get_cy()
					&& tmp.get_cy() < getHitHeightSum()+tmp.getrad())
					||
					(getHit_y() < tmp.get_cy()//(y < cy < y+h) and (x-r < cx < x+w+r)
					&& tmp.get_cy() < getHitHeightSum()
					&& getHit_x() - tmp.getrad() < tmp.get_cx()
					&& tmp.get_cx() < getHitWidthSum()+tmp.getrad()))
			{
				tmp.spawnLittles();
				astit.remove();
				hit();
			}
			
		}
		
		
		//Check ralien
		if(Globals.ralien.isAlive() 
				&& Globals.ralien.getHit_xBody() < getHitWidthSum() 
				&& getHit_x() < Globals.ralien.getHit_xBody()+Globals.ralien.getHitWidthBody()//check the x coordinates
				&& Globals.ralien.getHit_yBody() < getHitHeightSum()
				&& getHit_y() < Globals.ralien.getHit_yBody()+Globals.ralien.getHitHeightBody())
		{
			hit();
		}
		
		//Check alien
		if(Globals.alien.isAlive() 
				&& Globals.alien.getHit_xBody() < getHitWidthSum() 
				&& getHit_x() < Globals.alien.getHit_xBody()+Globals.alien.getHitWidthBody()//check the x coordinates
				&& Globals.alien.getHit_yBody() < getHitHeightSum()
				&& getHit_y() < Globals.alien.getHit_yBody()+Globals.alien.getHitHeightBody())
		{
			hit();
		}
	}
	
	public boolean get_neverAlive()
	{
		return neverAlive;
	}
 
	public void unsetNeverAlive()
	{
		neverAlive=false;
		alive=true;
	}

	public void delBullets()
	{
		bullets.clear(); 
	}

}
