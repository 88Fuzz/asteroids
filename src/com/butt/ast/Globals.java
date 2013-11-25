package com.butt.ast;


//All global variables are kept in here
public class Globals
{
	public static int PLAY=1;
	public static int PAUSE=2;
	public static int START=3;
	public static int KILL=0;
	public static final int g_gravity=3;
	public static int WIDTH=800;
	public static int HEIGHT=600;
	public static boolean g_gravityEN=false;
	public static boolean g_gravityObj=false;
	public static boolean g_gravityShow=false;
	public static int g_play=PLAY;
	
	public static final String p1Img="images/player1.png";
	public static final String p2Img="images/player2.png";
	public static final String bigAst="images/bigAsteroid.png";
	public static final String smallAst="images/smallAsteroid.png";
	public static final String p1Bullet="images/player1Bullet.png";
	public static final String p2Bullet="images/player2Bullet.png";
	public static final String alienShip="images/alien.png";
	public static final String alienBullet="images/alienBullet.png";
	public static final String gravityImg="images/gravity.png";
	
	
	public static Player player1;
	public static Player player2;
	public static Alien alien;
	public static Gravity gravity;
	
	//hit detection codes
	public static final int HITALLBUTPLAYER1=1;
	public static final int HITALLBUTPLAYER2=2;
	public static final int HITPLAYER1N2=3;
	
	//controls which option should be high-lighted yellow
	public static int optionsNum=0;
	
	public static boolean wrapObjs=true;
	
	
	//public static final double g_bulletMaxSpeed=8;
	//public static final double g_player1maxSpeed=5;
	//public static final double g_playervRotate=.25;
	//public static final double g_playervVelocity=.8;
	//public static final double g_playerFriction=.00004;
	
	Globals()
	{
		//g_gravity=3;
		g_gravityEN=false;
		g_gravityObj=false;
		g_gravityShow=false;
		g_play=PLAY;
	}
}
