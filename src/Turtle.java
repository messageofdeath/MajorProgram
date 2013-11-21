import javax.swing.JOptionPane;
import java.util.StringTokenizer;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*; //for files						

public class Turtle {
	// direction constants
	final static int N = 0, NE = 1, E = 2, SE = 3, S = 4, SW = 5, W = 6,
			NW = 7, STILL = 8;
	// movement change constants
	final static int X = 0, Y = 1, Z = 2;
	final static int SCREENW = 1024, SCREENH = 768;
	final String PATH = "n:\\";
	final static int STRT = 0, DIAG = 1;
	final double PI = 3.141592636;
	public Color color, saveColor;
	Image image[] = new Image[8];
	Image pic;
	String[] picture = new String[60];
	String[] pictureD = new String[60];
	private int // instance fields
			size = 2,
			eraseColor = 15, picHeight = 0, picWidth = 0,
			turtleX = 0, // current x coordinate
			turtleY = 0, // current y coordinate
			turtleZ = 0, // current y coordinate
			width = 0,
			speedX = 0, // speed -- size of steps
			speedY = 0, speedZ = 0, turtleColor = 0, hitBy = 100,
			score = 0,
			team = -1, heading = N;
	private String name = "";
	private double direction = 0;
	private static int numTurtles = 0;

	// constructors
	Turtle() {
		heading = N;
		turtleX = SCREENW / 2;
		turtleY = SCREENH / 2;
		turtleZ = 0;
		width = 1;
		color = Color.black;
		init();
	}

	Turtle(int x, int y, int z) {
		heading = N;
		turtleX = x;
		turtleY = y;
		turtleZ = z;
		width = 1;
		color = Color.black;
		init();
	}

	Turtle(int x, int y, int z, int dir) {
		heading = dir;
		turtleX = x;
		turtleY = y;
		turtleZ = z;
		width = 1;
		color = Color.black;
		init();
	}

	Turtle(int x, int y, int z, int dir, int s, Color c) {
		heading = dir;
		turtleX = x;
		turtleY = y;
		turtleZ = z;
		width = 1;
		color = c;
		size = s;
		init();
	}

	Turtle(int x, int y, int z, int spX, int spY, int spZ, int col, String nm) {
		heading = N;
		turtleX = x;
		turtleY = y;
		turtleZ = z;
		speedX = spX;
		speedY = spY;
		speedZ = spZ;
		name = nm;
		turtleColor = col;
		init();
	}

	Turtle(int x, int y, int z, int spX, int spY, int spZ, int col, String nm,
			int sz) {
		heading = N;
		turtleX = x;
		turtleY = y;
		turtleZ = z;
		speedX = spX;
		speedY = spY;
		speedZ = spZ;
		name = nm;
		turtleColor = col;
		size = sz;
		init();
	}

	public void init() {
		// readSprite(PATH +"ballc.data",0);
		// readSprite(PATH+"ballcD.data",1);
		// for (int x = 0;x<8;x++)
		// image[x]=Toolkit.getDefaultToolkit().getImage(PATH+"testpic.jpg");
		numTurtles++;
	}

	public void showTurtle(Graphics g) {
		double h = getHead();
		int x = getTurtleX();
		int y = getTurtleY();
		g.drawOval(x - 3, y - 3, 6, 6);

		forward(5, g);
		g.drawOval(getTurtleX() - 2, getTurtleY() - 2, 4, 4);
		setHeading(h);
		setTurtleX(x);
		setTurtleY(y);

	}

	public void setHeading(int x, int y) {
		int spdx = x / 3;
		int spdy = y / 3;
		if (spdx == 0) {
			if (spdy > 0)
				heading = S;
			if (spdy < 0)
				heading = N;
		}
		if (spdx > 0) {
			if (spdy > 0)
				heading = SE;
			if (spdy < 0)
				heading = NE;
			if (spdy == 0)
				heading = E;
		}
		if (spdx < 0) {
			if (spdy > 0)
				heading = SW;
			if (spdy < 0)
				heading = NW;
			if (spdy == 0)
				heading = W;
		}
		// heading = STILL;
	}

	public void setHeading() {
		int spdx = speedX;
		int spdy = speedY;
		// spdx = x /3;
		// spdy = y /3;
		if (spdx == 0) {
			if (spdy > 0)
				heading = S;
			if (spdy < 0)
				heading = N;
		}
		if (spdx > 0) {
			if (spdy > 0)
				heading = SE;
			if (spdy < 0)
				heading = NE;
			if (spdy == 0)
				heading = E;
		}
		if (spdx < 0) {
			if (spdy > 0)
				heading = SW;
			if (spdy < 0)
				heading = NW;
			if (spdy == 0)
				heading = W;
		}
		// speedX = spdx;
		// speedY = spdy;
		// heading = STILL;
	}

	public void drawPic(int x, int y, int width, int height, Graphics pen) {
		pen.drawImage(pic, x - width / 2 * size, y - height * size / 2, width
				* size, height * size, null);
	}

	public void drawPic(Graphics pen) {
		pen.drawImage(pic, turtleX - picWidth / 2 * size, turtleY - picHeight
				* size / 2, picWidth * size, picHeight * size, null);
	}

	public void drawPic(int w, Graphics pen) {
		picWidth = w;
		picHeight = w;
		pen.drawImage(pic, turtleX - picWidth / 2 * size, turtleY - picHeight
				* size / 2, picWidth * size, picHeight * size, null);
	}

	public void setSize(int s) // mutators
	{
		size = s;
	}

	public void setEraseColor(char e) // mutators
	{
		eraseColor = e;
	}

	public void setTurtleColor(int e) // mutators
	{
		setcolor(e);
		turtleColor = e;
	}

	public void setPicHeight(int n) // mutators
	{
		picHeight = n;
	}

	public void setPicture(String[] n) // mutators
	{
		for (int x = 0; x < n.length; x++)
			picture[x] = n[x];
	}

	public void setPicWidth(int n) // mutators
	{
		picWidth = n;
	}

	public void setTurtleX(int n) // mutators
	{
		turtleX = n;
	}

	public void setTurtleY(int n) // mutators
	{
		turtleY = n;
	}

	public void setTurtleZ(int n) // mutators
	{
		turtleZ = n;
	}

	public void setX(int n) // mutators
	{
		turtleX = n;
	}

	public void setY(int n) // mutators
	{
		turtleY = n;
	}

	public void setZ(int n) // mutators
	{
		turtleZ = n;
	}

	public void setSpeedX(int n) // mutators
	{
		speedX = n;
	}

	public void setSpeedY(int n) // mutators
	{
		speedY = n;
	}

	public void setSpeedZ(int n) // mutators
	{
		speedZ = n;
	}

	public void addScore(int n) // mutators
	{
		score += n;
	}

	public void deductScore(int n) // mutators
	{
		score -= n;
	}

	public int getHeading() {
		return heading;
	}

	public double getHead() {
		return direction;
	}

	public int getSize() // accessors
	{
		return size;
	}

	public int getEraseColor() // accessors
	{
		return eraseColor;
	}

	public int getTurtleColor() // accessors
	{
		return turtleColor;
	}

	public int getPicHeight() // accessors
	{
		return picHeight;
	}

	public String[] getPicture() // accessors
	{
		return picture;
	}

	public int getPicWidth() // accessors
	{
		return picWidth;
	}

	public int getTurtleX() // accessors
	{
		return turtleX;
	}

	public int getTurtleY() // accessors
	{
		return turtleY;
	}

	public int getTurtleZ() // accessors
	{
		return turtleZ;
	}

	public int getX() // accessors
	{
		return turtleX;
	}

	public int getY() // accessors
	{
		return turtleY;
	}

	public int getZ() // accessors
	{
		return turtleZ;
	}

	public int getSpeedX() // accessors
	{
		return speedX;
	}

	public int getSpeedY() // accessors
	{
		return speedY;
	}

	public int getSpeedZ() // accessors
	{
		return speedZ;
	}

	public int getScore() // mutators
	{
		return score;
	}

	public void getImage(String fileName) // mutators
	{
		pic = Toolkit.getDefaultToolkit().getImage(fileName);
		// picHeight = pic.getHeight(this);
		// picWidth = pic.getWidth(this);
	}

	public void readImage(String fileName) // mutators
	{
		pic = Toolkit.getDefaultToolkit().getImage(fileName);
		// picHeight = pic.getHeight(this);
		// picWidth = pic.getWidth(this);
	}

	public void eraseOSprite(Graphics G) {
		switch (heading) {
		// final int N = 0,NE = 1,E = 2,SE = 3, S = 4, SW = 5, W = 6, NW =7;
		case W:
			eraseUp(turtleX, turtleY, size + 3, G);
			break;
		case E:
			eraseDn(turtleX, turtleY, size + 3, G);
			break;
		case N:
			erase(turtleX, turtleY, size + 3, G);
			break;
		case S:
			eraseR(turtleX, turtleY, size + 3, G);
			break;
		case NW:
			eraseUp(turtleX, turtleY, size + 3, G);
			break;
		case SE:
			eraseDn(turtleX, turtleY, size + 3, G);
			break;
		case NE:
			erase(turtleX, turtleY, size + 3, G);
			break;
		case SW:
			eraseR(turtleX, turtleY, size + 3, G);
			break;
		}
	}

	public void eraseSprite(Turtle t1, Turtle t2, int x, int y, int z,
			Graphics G) {
		switch (heading) {
		// final int N = 0,NE = 1,E = 2,SE = 3, S = 4, SW = 5, W = 6, NW =7;
		case N:
			t1.eraseUp(x, y, size + 1, G);
			break;
		case S:
			t1.eraseDn(x, y, size + 1, G);
			break;
		case E:
			t1.erase(x, y, size + 1, G);
			break;
		case W:
			t1.eraseR(x, y, size + 1, G);
			break;
		case NE:
			t2.erase(x, y, size + 1, G);
			break;
		case NW:
			t2.eraseUp(x, y, size + 1, G);
			break;
		case SE:
			t2.eraseDn(x, y, size + 1, G);
			break;
		case SW:
			t2.eraseR(x, y, size + 1, G);
			break;
		case STILL:
			t2.eraseR(x, y, size + 1, G);
			break;
		}
	}

	public void drawOSprite(Graphics G) {
		switch (heading) {
		// final int N = 0,NE = 1,E = 2,SE = 3, S = 4, SW = 5, W = 6, NW =7;
		case W:
			spriteUp(turtleX, turtleY, size, G);
			break;
		case E:
			spriteDn(turtleX, turtleY, size, G);
			break;
		case N:
			sprite(turtleX, turtleY, size, G);
			break;
		case S:
			spriteR(turtleX, turtleY, size, G);
			break;
		case NW:
			spriteUp(turtleX, turtleY, size, G);
			break;
		case SE:
			spriteDn(turtleX, turtleY, size, G);
			break;
		case NE:
			sprite(turtleX, turtleY, size, G);
			break;
		case SW:
			spriteR(turtleX, turtleY, size, G);
			break;
		}
		G.setColor(Color.white);
		G.drawString(name, turtleX - 10, turtleY);
		G.drawString("" + score, turtleX - 12, turtleY + 12);
	}

	public void drawOSprite1(Graphics G) {
		switch (heading) {
		// final int N = 0,NE = 1,E = 2,SE = 3, S = 4, SW = 5, W = 6, NW =7;
		case W:
			spriteUp(turtleX, turtleY, size, G);
			break;
		case E:
			spriteDn(turtleX, turtleY, size, G);
			break;
		case N:
			sprite(turtleX, turtleY, size, G);
			break;
		case S:
			spriteR(turtleX, turtleY, size, G);
			break;
		case NW:
			spriteUp(turtleX, turtleY, size, G);
			break;
		case SE:
			spriteDn(turtleX, turtleY, size, G);
			break;
		case NE:
			sprite(turtleX, turtleY, size, G);
			break;
		case SW:
			spriteR(turtleX, turtleY, size, G);
			break;
		}
	}

	public void drawSprite(Turtle t1, Turtle t2, Graphics G, int size) {
		switch (heading) {
		// final int N = 0,NE = 1,E = 2,SE = 3, S = 4, SW = 5, W = 6, NW =7;
		case N:
			t1.spriteUp(t1.turtleX, t1.turtleY, size, G);
			break;
		case S:
			t1.spriteDn(t1.turtleX, t1.turtleY, size, G);
			break;
		case E:
			t1.sprite(t1.turtleX, t1.turtleY, size, G);
			break;
		case W:
			t1.spriteR(t1.turtleX, t1.turtleY, size, G);
			break;
		case NE:
			t2.sprite(t1.turtleX, t1.turtleY, size, G);
			break;
		case NW:
			t2.spriteUp(t1.turtleX, t1.turtleY, size, G);
			break;
		case SE:
			t2.spriteDn(t1.turtleX, t1.turtleY, size, G);
			break;
		case SW:
			t2.spriteR(t1.turtleX, t1.turtleY, size, G);
			break;
		case STILL:
			t2.spriteR(t1.turtleX, t1.turtleY, size, G);
			break;
		}
		G.setColor(Color.white);
		G.drawString(name, t1.turtleX - 10, t1.turtleY);
		G.drawString("" + score, t1.turtleX - 12, t1.turtleY + 12);
	}

	public void draw3D(Turtle t1, Turtle camera, Graphics G, int size) {
		double dist = Math
				.sqrt((Math.pow(t1.turtleX - camera.turtleX, 2) + Math.pow(
						t1.turtleY - camera.turtleY, 2)));
		double slope = 50;
		if (t1.turtleX != camera.turtleX)
			slope = (double) (t1.turtleY - camera.turtleY)
					/ (t1.turtleX - camera.turtleX);
		int x = 0;
		size *= (8 - (int) dist / 100);// *(t1.score/12+1);
		int centerOfScreen = 400;
		int yLoc = 500 - (int) (dist / 2);
		if (yLoc < 100)
			yLoc = 100;
		switch (camera.heading) {
		case N:
			if (t1.turtleY <= camera.turtleY)
				if (slope <= 0) {
					if (slope <= -1)
						x = (int) (centerOfScreen - centerOfScreen / slope);
					else
						x = (int) (centerOfScreen + centerOfScreen - size * 12);
				} else {
					if (slope >= 1)
						x = (int) (centerOfScreen - centerOfScreen / slope);
					else
						x = size * 12;
				}
			t1.sprite(x, yLoc, size, G);
			break;

		case S:
			if (t1.turtleY <= camera.turtleY)
				if (slope >= 0) {
					if (slope <= -1)
						x = (int) (centerOfScreen - centerOfScreen / slope);
					else
						x = (int) (centerOfScreen + centerOfScreen - size * 12);
				} else {
					if (slope >= 1)
						x = (int) (centerOfScreen - centerOfScreen / slope);
					else
						x = size * 12;
				}
			t1.sprite(x, yLoc, size, G);
			break;
		}
		G.setColor(Color.black);
		G.drawString(name, x, 70);
		G.drawString("" + score, x, 80);
	}

	public void upDateLocation() {

		turtleX += speedX;
		turtleY += speedY;
		turtleZ += speedZ;
		if (turtleX > 800 - picWidth / 2 * size
				|| turtleX < picWidth / 2 * size + 50) {
			turtleX -= speedX;
			speedX *= -1;
		}
		if (turtleY > 600 - picHeight / 2 * size
				|| turtleY < (picHeight / 2 + 20) * size) {
			turtleY -= speedY;
			speedY *= -1;
		}
		if (turtleZ > 30 || turtleZ < -1) {
			turtleZ -= speedZ;
			speedZ *= -1;
		}
	}

	public void gotoXY(int x, int y, int z) // moves to a location
	{
		turtleX = x;
		turtleY = y;
		turtleZ = z;
	}

	public void gotoXYZ(int x, int y, int z) // moves to a location
	{
		turtleX = x;
		turtleY = y;
		turtleZ = z;
	}

	public void setWidth(int w) // sets width of line
	{
		width = w;
	}

	public void forward(double l, Graphics G) // move forward l distance in
												// pixels
	{
		G.setColor(color);
		int x = 0;
		fd(l, G);
		rt(4);

		for (x = 0; x < width / 2; x++) {
			fd(x * 2, G);
			rt(4);

			fd(l, G);
			rt(4);
			fd(x * 2 + 1, G);
			rt(4);
			fd(l, G);
			rt(4);
		}
		fd((x), G);
		lt(4);
	}

	private void fd(double x, Graphics G) // move forward x distance in pixels
	{
		double sx, sy, sz;
		sx = (x * Math.cos(direction) + (double) turtleX + .5); // .5 to correct
																// rounding
		sy = ((double) turtleY - (x * Math.sin(direction)) + .5);
		sz = 0;
		G.drawLine((int) sx, (int) sy, turtleX, turtleY);
		turtleX = (int) sx;
		turtleY = (int) sy;
		turtleZ = (int) sz;

		gotoXY((int) sx, (int) sy, (int) sz);
	}

	public void penUp() {
		saveColor = color;
		setcolor(eraseColor);
	}

	public void penDown() {
		color = saveColor;

	}

	public void setHeading(double d) // change turtle heading (radians)
	{
		direction = d;
	}

	public void setHead(int d) // change turtle heading (N NE E SE S SW NW
								// STILL)
	{
		heading = d;
	}

	public void rt(double d) // turn rt -(2PI / d) in radians
	{
		direction -= (2 * PI / d);
	}

	public void right(int d) {
		double dir = 360 / (double) d;// 2 = 180 3 = 120 4 = 90 5 = 72 6 = 60 8
										// = 45
		rt(dir);
	}

	public void left(int d) {
		double dir = 360 / (double) d;// 2 = 180 3 = 120 4 = 90 5 = 72 6 = 60 8
										// = 45
		lt(dir);
	}

	public void lt(double d) // turn lt -(2PI / d) in radians
	{
		direction += (2 * PI / d);
	}

	public void home(Graphics G) // put turtle in the center facing up 320,240
									// PI/2
	{
		direction = PI / 2;
		turtleX = SCREENW / 2;
		turtleY = SCREENH / 2;

	}

	public void draw(Graphics G) // clear screen and home the turtle
	{
		G.clearRect(0, 0, SCREENW, SCREENH);
		home(G);
	}

	public void drawPixel(int x, int y, int s, Graphics G) // draws a single
															// point
	{
		G.setColor(color);
		// System.out.println(s);
		// G.fillOval(x,y,x+s,y+s);
		for (int h = 0; h < s; h++)
			G.drawLine(x, h + y, x + s, h + y);
	}

	public void setcolor(int c) // setcolors for sprites
	{
		Color brown = new Color(156, 94, 75);// combinations of red, green, blue
		Color brtBlue = new Color(35, 190, 235);// Brter
		Color brtGreen = new Color(40, 220, 90);// Brter
		switch (c) {
		case 0:
			color = Color.black;
			break; // A
		case 1:
			color = Color.magenta;
			break; // B
		case 2:
			color = Color.blue;
			break;
		case 3:
			color = brtBlue;
			break;// D
		case 4:
			color = Color.pink;
			break;
		case 5:
			color = Color.green;
			break; // F
		case 6:
			color = brtGreen;
			break;
		case 7:
			color = Color.orange;
			break;// H
		case 8:
			color = Color.red;
			break;
		case 9:
			color = Color.gray;
			break; // J
		case 10:
			color = Color.darkGray;
			break;
		case 11:
			color = Color.yellow;
			break;// L
		case 12:
			color = Color.cyan;
			break;
		case 13:
			color = Color.lightGray;
			break;// N
		case 14:
			color = brown;
			break;
		case 15:
			color = Color.white;
			break;// P Color.white
		default:
			color = Color.lightGray;
		}
	}

	public void setcolor(int c, Graphics g) // setcolors for sprites
	{
		Color brown = new Color(156, 94, 75);// combinations of red, green, blue
		Color brtBlue = new Color(35, 190, 235);// Brter
		Color brtGreen = new Color(40, 220, 90);// Brter
		switch (c) {
		case 0:
			g.setColor(Color.black);
			break; // A
		case 1:
			g.setColor(Color.magenta);
			break; // B
		case 2:
			g.setColor(Color.blue);
			break;
		case 3:
			g.setColor(brtBlue);
			break;// D
		case 4:
			g.setColor(Color.pink);
			break;
		case 5:
			g.setColor(Color.green);
			break; // F
		case 6:
			g.setColor(brtGreen);
			break;
		case 7:
			g.setColor(Color.orange);
			break;// H
		case 8:
			g.setColor(Color.red);
			break;
		case 9:
			g.setColor(Color.gray);
			break; // J
		case 10:
			g.setColor(Color.darkGray);
			break;
		case 11:
			g.setColor(Color.yellow);
			break;// L
		case 12:
			g.setColor(Color.cyan);
			break;
		case 13:
			g.setColor(Color.lightGray);
			break;// N
		case 14:
			g.setColor(brown);
			break;
		case 15:
			g.setColor(brtBlue);
			break;// P
		default:
			g.setColor(Color.lightGray);
		}
	}

	public static void enter(String title) {
		System.out.println(title + " Press enter");
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			input.readLine();
		} catch (IOException e) {
		}
	}

	public static void enter() {
		System.out.println("Press enter");
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			input.readLine();
		} catch (IOException e) {
		}
	}

	public void readSprite(String filename) {
		BufferedReader picfile;
		FileReader picf;
		try {
			picf = new FileReader(filename);
			picfile = new BufferedReader(picf);
		} catch (FileNotFoundException exc) {
			JOptionPane.showMessageDialog(null, "Input " + filename
					+ "File Not found");
			return;
		}
		String inVal = ""; // declare data variable
		boolean readInt = true;
		try {
			inVal = picfile.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error reading sprite file "
					+ filename);
			return;
		}
		try {
			picHeight = Integer.parseInt(inVal);
		} catch (Exception e) {
			readInt = false;
		}
		if (readInt) {
			try {
				inVal = picfile.readLine();
				for (int x = 0; x < picHeight; x++) // straight picture
				{
					inVal = picfile.readLine();
					if (inVal != null) {
						picture[x] = inVal;
						if (picWidth < picture[x].length())
							picWidth = picture[x].length();
					}
				}
				for (int x = 0; x < picHeight; x++) // diagonal picture
				{
					inVal = picfile.readLine();

					if (inVal != null) {
						pictureD[x] = inVal;
						if (picWidth < pictureD[x].length())
							picWidth = pictureD[x].length();
					} else
						pictureD[x] = picture[x];
					// JOptionPane.showMessageDialog(null,"Error -- readSprite expects TWO views -- straight AND diagonal in "+filename);
					// System.out.println(pictureD[x]);
				}
				picfile.close();
			} catch (Exception e) {
				readInt = false;
			}

		} else {
			Scanner s = new Scanner(System.in);
			try {
				s = new Scanner(new File(filename));
			} catch (Exception e) {
			}
			int x = 0;
			while (s.hasNext()) {
				s.nextLine();
				x++;
			}
			s.close();
			picHeight = x / 2;
			try {
				s = new Scanner(new File(filename));
			} catch (Exception e) {
			}
			for (x = 0; x < picHeight; x++) {
				picture[x] = s.nextLine();
			}
			for (x = 0; x < picHeight; x++) {
				pictureD[x] = s.nextLine();
			}
			s.close();
			// count strings
		}

	} // end of code to read picture

	public void sprite(int x, int y, int size, Graphics G) {
		x -= (size * picWidth) / 2; // calculate upper lt x to center image
		y -= (size * picHeight) / 2; // calculate upper lt y to center image
		int v, h;
		char lt = 0;
		String whichPic = "";

		for (v = 0; v < picHeight; v++) {
			if (heading % 2 == 0)
				whichPic = picture[v];
			else
				whichPic = pictureD[v];
			// System.out.println("  Sprite "+heading+"  "+pictureD[v]);

			for (h = 0; h < whichPic.length(); h++) {

				lt = whichPic.charAt(h);
				if (lt == 'X')
					setcolor(turtleColor);
				else
					setcolor((int) lt - 65);
				if (lt != ' ')
					drawPixel(h * size + x, v * size + y, size, G);
			}
		}
	}

	public void spriteR(int x, int y, int size, Graphics G) {
		x -= (size * picWidth) / 2; // calculate upper lt x to center image
		y -= (size * picHeight) / 2; // calculate upper lt y to center image
		int v, h;
		char lt = 0;
		String whichPic = "";

		for (v = 0; v < picHeight; v++) {
			if (heading % 2 == 0)
				whichPic = picture[v];
			else
				whichPic = pictureD[v];
			// System.out.println("  Sprite "+heading+"  "+pictureD[v]);
			for (h = 0; h < whichPic.length(); h++) {
				lt = whichPic.charAt(h);
				if (lt == 'X')
					setcolor(turtleColor);
				else
					setcolor((int) lt - 65);

				if (lt != ' ')
					drawPixel(picWidth * size - h * size + x, picHeight * size
							- v * size + y, size, G);
			}
		}
	}

	public void spriteDn(int x, int y, int size, Graphics G) {
		x -= (size * picWidth) / 2; // calculate upper lt x to center image
		y -= (size * picHeight) / 2; // calculate upper lt y to center image
		int v, h;
		char lt = 0;
		String whichPic = "";
		for (v = 0; v < picHeight; v++) {
			if (heading % 2 == 0)
				whichPic = picture[v];
			else
				whichPic = pictureD[v];
			// System.out.println("  Sprite "+heading+"  "+pictureD[v]);

			for (h = 0; h < whichPic.length(); h++)

			{
				lt = whichPic.charAt(h);
				if (lt == 'X')
					setcolor(turtleColor);
				else
					setcolor((int) lt - 65);

				if (lt != ' ')
					drawPixel(picWidth * size - v * size + x, h * size + y,
							size, G);
			}
		}
	}

	public void spriteUp(int x, int y, int size, Graphics G) {
		x -= (size * picWidth) / 2; // calculate upper lt x to center image
		y -= (size * picHeight) / 2; // calculate upper lt y to center image
		int v, h;
		char lt = 0;
		String whichPic = "";
		for (v = 0; v < picHeight; v++) {
			if (heading % 2 == 0)
				whichPic = picture[v];
			else
				whichPic = pictureD[v];
			for (h = 0; h < whichPic.length(); h++) {
				lt = whichPic.charAt(h);
				if (lt == 'X')
					setcolor(turtleColor);
				else
					setcolor((int) lt - 65);

				if (lt != ' ')
					drawPixel(v * size + x, picHeight * size - h * size + y,
							size, G);
			}
		}
	}

	public void erase(int x, int y, int size, Graphics G) {
		x -= (size * picWidth) / 2; // calculate upper lt x to center image
		y -= (size * picHeight) / 2; // calculate upper lt y to center image
		int v, h;
		char lt = 0;
		setcolor(eraseColor);
		String whichPic = "";

		for (v = 0; v < picHeight; v++) {
			if (heading % 2 == 0)
				whichPic = picture[v];
			else
				whichPic = pictureD[v];
			for (h = 0; h < whichPic.length(); h++) {

				lt = whichPic.charAt(h);

				if (lt != ' ')
					drawPixel(h * size + x, v * size + y, size, G);
			}
		}
	}

	public void eraseR(int x, int y, int size, Graphics G) {
		x -= (size * picWidth) / 2; // calculate upper lt x to center image
		y -= (size * picHeight) / 2; // calculate upper lt y to center image
		int v, h;
		char lt = 0;
		setcolor(eraseColor);
		String whichPic = "";
		for (v = 0; v < picHeight; v++) {
			if (heading % 2 == 0)
				whichPic = picture[v];
			else
				whichPic = pictureD[v];

			for (h = 0; h < whichPic.length(); h++) {
				lt = whichPic.charAt(h);
				if (lt != ' ')
					drawPixel(picWidth * size - h * size + x, picHeight * size
							- v * size + y, size, G);
			}
		}
	}

	public void eraseDn(int x, int y, int size, Graphics G) {
		x -= (size * picWidth) / 2; // calculate upper lt x to center image
		y -= (size * picHeight) / 2; // calculate upper lt y to center image
		int v, h;
		setcolor(eraseColor);
		char lt = 0;
		String whichPic = "";
		for (v = 0; v < picHeight; v++) {
			if (heading % 2 == 0)
				whichPic = picture[v];
			else
				whichPic = pictureD[v];

			for (h = 0; h < whichPic.length(); h++) {
				lt = whichPic.charAt(h);
				if (lt != ' ')
					drawPixel(picWidth * size - v * size + x, h * size + y,
							size, G);
			}
		}
	}

	public void eraseUp(int x, int y, int size, Graphics G) {
		x -= (size * picWidth) / 2; // calculate upper lt x to center image
		y -= (size * picHeight) / 2; // calculate upper lt y to center image
		int v, h;
		char lt = 0;
		String whichPic = "";
		setcolor(eraseColor);
		for (v = 0; v < picHeight; v++) {
			if (heading % 2 == 0)
				whichPic = picture[v];
			else
				whichPic = pictureD[v];

			for (h = 0; h < whichPic.length(); h++) {
				lt = whichPic.charAt(h);
				if (lt != ' ')
					drawPixel(v * size + x, picHeight * size - h * size + y,
							size, G);
			}
		}
	}

}
