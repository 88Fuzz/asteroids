package com.butt.ast;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.Random;

public class Alien extends Sprite {
	protected double bulletWait;
	protected double bulletWaitCount;
	protected String bulletImg;
	private Random generator;
	protected int dist;
	protected double distCount;
	protected ArrayList<Bullet> bullets;
	private int spawnCnt;
	private int spawnTime;
	protected int lives;
	Sound hitSound;

	public Alien(String imgLoc, String bullet) {
		super(imgLoc);
		bulletImg = bullet;
		maxSpeed = 2.75 + (Globals.level * .25);
		generator = new Random();
		genDirFirst();
		genDist();
		genBulletDist();
		bullets = new ArrayList<Bullet>();
		genPos();
		genRespawnTime();
		alive = false;
		lives = 3;
	}

	public Alien(String imgLoc, String bullet, int hitCode) {
		this(imgLoc, bullet);
		this.hitCode = hitCode;
	}

	// does not let the ship move left and right the first time
	public void genDirFirst() {
		rotate = generator.nextInt(8);// 8 different angles

		switch ((int) rotate) {
		// case 0: rotate=0;
		// break;
		case 1:
			rotate = 45;
			break;
		case 2:
			rotate = 90;
			break;
		case 3:
			rotate = 135;
			break;
		// case 4: rotate=180;
		// break;
		case 5:
			rotate = 225;
			break;
		case 6:
			rotate = 270;
			break;
		case 7:
			rotate = 315;
			break;
		default:
			rotate = 315;
		}

		vx = maxSpeed * Math.sin(Math.toRadians(rotate));
		vy = -maxSpeed * Math.cos(Math.toRadians(rotate));
	}

	public void genDir() {
		rotate = generator.nextInt(8);// 8 different angles

		switch ((int) rotate) {
		case 0:
			rotate = 0;
			break;
		case 1:
			rotate = 45;
			break;
		case 2:
			rotate = 90;
			break;
		case 3:
			rotate = 135;
			break;
		case 4:
			rotate = 180;
			break;
		case 5:
			rotate = 225;
			break;
		case 6:
			rotate = 270;
			break;
		case 7:
			rotate = 315;
			break;
		default:
			rotate = 0;
		}

		vx = maxSpeed * Math.sin(Math.toRadians(rotate));
		vy = -maxSpeed * Math.cos(Math.toRadians(rotate));
	}

	public void updatePos() {
		double tmpDist = Math.sqrt(vx * vx + vy * vy);
		double tmpAngle;
		double dx;
		double dy;

		x += vx;
		y += vy;
		distCount += tmpDist;
		bulletWaitCount += tmpDist;

		if (bulletWaitCount > bulletWait) {
			genBulletDist();

			if (Globals.player1.isAlive()) {
				// find angle between alien and player1
				dx = x - Globals.player1.get_x();
				dy = y
						- (Globals.player1.get_y() + Globals.player1
								.getHeight() / 2);
				tmpAngle = -Math.toDegrees(Math.atan(dx / dy));

				if (dy < 0)
					tmpAngle = -(180 - tmpAngle);

				bullets.add(new Bullet(bulletImg, x, y, img.getWidth() / 2, 0,
						vVelocity, tmpAngle, bullets.size() + 1, hitCode));
			}

			if (Globals.player2.isAlive()) {
				dx = x - Globals.player2.get_x();
				dy = y
						- (Globals.player2.get_y() + Globals.player2
								.getHeight() / 2);
				tmpAngle = -Math.toDegrees(Math.atan(dx / dy));

				if (dy < 0)
					tmpAngle = -(180 - tmpAngle);

				bullets.add(new Bullet(bulletImg, x, y, img.getWidth() / 2, 0,
						vVelocity, tmpAngle, bullets.size() + 1, hitCode));
			}
		}

		if (distCount > dist) {
			genDir();
			genDist();
		}

		updateBullets();
	}

	public void updateBullets() {
		// update bullet positions
		for (int j = 0; j < bullets.size(); j++) {
			if (bullets.get(j).checkHits() > 0) {
				bullets.remove(j);
			}
			// if bullet has traveled greater than the width of the screen, then
			// updatePosMax will be true
			else if (bullets.get(j).updatePosMax())
				bullets.remove(j);
		}
	}

	public void genDist() {
		dist = generator.nextInt(Globals.WIDTH + 200) + 200;
		distCount = 0;
	}

	// generates random distance at which a bullet will shoot
	public void genBulletDist() {
		bulletWait = generator.nextInt(Globals.HEIGHT) + 200;
		bulletWaitCount = 0;
	}

	public void checkEdges() {
		if (Globals.wrapObjs) {
			if (x < 0)
				x = Globals.WIDTH;
			else if (x > Globals.WIDTH)
				x = 0;

			if (y < 0)
				y = Globals.HEIGHT;
			else if (y > Globals.HEIGHT)
				y = 0;
		} else {
			if (x < 0) {
				x = 0;
				vx *= -1;
			} else if (x > Globals.WIDTH) {
				x = Globals.WIDTH;
				vx *= -1;
			}

			if (y < 0) {
				y = 0;
				vy *= -1;
			} else if (y > Globals.HEIGHT) {
				y = Globals.HEIGHT;
				vy *= -1;
			}
		}

		for (Bullet tmp : bullets)
			tmp.checkEdges();
	}

	public void draw(Graphics2D g) {
		BufferedImageOp ops = null;
		AffineTransform tx = AffineTransform.getRotateInstance(0,
				img.getWidth() / 2, img.getHeight() / 2);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);

		if (alive)
			g.drawImage(op.filter(img, null), ops, (int) Math.round(x),
					(int) Math.round(y));

		for (Bullet tmp : bullets)
			tmp.draw(g);
		// g.drawImage(tmp.getImage(), ops, (int)tmp.get_x(), (int)tmp.get_y());
	}

	// gets the x position of hitbox
	public int getHit_xBody() {
		return (int) Math.round(x);
	}

	// gets the y position of hitbox
	public int getHit_yBody() {
		return (int) Math.round(y) + 7;
	}

	// return hitbox width
	public int getHitWidthBody() {
		return 30;
	}

	// return hitbox height
	public int getHitHeightBody() {
		return 8;
	}

	public void genPos() {
		x = Globals.WIDTH;
		y = generator.nextInt(Globals.HEIGHT);
	}

	public void genRespawnTime() {
		spawnCnt = 0;
		spawnTime = generator.nextInt(30000) + 30000;// will spawn between 30
														// and 60 seconds
	}

	public void checkSpawn(long diff) {
		spawnCnt += diff;
		if (spawnCnt > spawnTime) {
			alive = true;
			maxSpeed = 2.75 + (Globals.level * .25);
		}

		updateBullets();
	}

	public double getRotate() {
		return rotate;
	}

	public void startNew(int lives, double x, double y, double xvel,
			double yvel, double angle) {
		this.lives = lives;
		this.x = x;
		this.y = y;
		this.vx = xvel;
		this.vy = yvel;
		this.rotate = angle;
		this.alive=true;
	}

	public int getLives() {
		return lives;
	}

	public void hit() {
		lives--;
		if (lives == 0) {
			hitSound = new Sound(Globals.hitSoundStr);
			hitSound.start();
			alive = false;
			genDirFirst();
			genDist();
			genBulletDist();
			genPos();
			genRespawnTime();
			lives = 3;
		}
	}
}
