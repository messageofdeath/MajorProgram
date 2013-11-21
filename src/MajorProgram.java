/*
MP2


Due today
---------------------------------------
Names     Period

Theme of your major program  (What is it about?)


Explain how to play your game.

-------------------------------------------

Requirements

1.  Documentation                                                       5
2.  Create a data file to create your own sprite.                       5
3.  fix the buttons and change to keyboard input (KeyListener interface)5
   NOTE  ************* You must understand the methods in #3, 4 and 5
4.  Write the code to move your sprite around the screen.              10
          in method updateLocation()
5.  Write the code to bounce objects off the edges of the screen.      10
          in method checkEdges()
6.  Write the code to bounce turtles off each other.                   10   
          in method checkCollision()
          
7.  Add three instance variables to the Turtle class       
    and use them  (5 points each)                                      15
       One must be a static variable 
       
   NOTE  ************* copy your instance variable definitions here
      and explain how each is used.
      private String smell = "";    This gives each Turtle object a unique smell.
      
8, Add 5 methods to the Turtle class 
   and use them  (5 points each)                                       25
   
     two must have parameters
     one must have a non-void return type.
     one must be a static method     

   NOTE  ************* copy your methods here
      and explain how each is used.
public void setSmell(String s) //mutators
{
	smell = s;   This allows MP2 to change value of smell instance variable
}	
public String getSmell() //accessors
{
	return smell  This allows MP2 to see value of smell instance variable;
}	

9.  Create and use additional Turtle objects using 
   different constructors separate not part
   of the original array. (5 points each, up to 2)                     10
   
10. Add mouse input to your program                                      5   
   
   
 */
import static java.lang.System.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class MajorProgram {
	public static void main(String args[]) {
		new MadBug(); // Make autonomous Windows object
	}

}

class MadBug extends Frame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 476543692055788809L;

	// ************************ define CONSTANTS and variables global
	// *******************
	private final static int SCHEIGHT = 768, SCWIDTH = 1024;

	Button btnRight = new Button("Right"); // change the names and titles of the
											// buttons
	Button btnLeft = new Button("Left");
	Button btnUp = new Button("Up");
	Button btnDown = new Button("Down");
	Button btnStop = new Button("Stop");

	int speed = 30;
	int numTurtles = 10;
	Turtle[] thing = new Turtle[numTurtles]; // define Turtle array (thing)
	int delay = 130;
	boolean gameNotOver = true;
	private boolean paused = true;
	private Image myScreen;

	public MadBug() {
		super.setSize(SCWIDTH, SCHEIGHT);
		super.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// **************** initialize objects with constructors
		// *****************
		super.setTitle("Major Program");
		for (int x = 0; x < this.numTurtles; x++) {
			this.thing[x] = new Turtle(new Random().nextInt(SCWIDTH), // int that sets the turtleX location
					new Random().nextInt(SCHEIGHT), // int that sets the turtleY location
					0, // sets the Z location
					2, // int that sets the speedX
					2, // speedY
					6, // zSpeed
					x, // color
					"", // name
					3); // size
			this.thing[x].setSpeedX(new Random().nextInt(10));
			this.thing[x].setSpeedY(new Random().nextInt(10));
			this.thing[x].readSprite("C:\\Users\\s440788\\Desktop\\turtle.data");
		}
		//this.thing[0].setSize(10);//TODO Testing Reasons for checkCollisions

		super.setLayout(new FlowLayout(FlowLayout.LEFT));

		/*super.add(btnRight);
		super.add(btnLeft);
		super.add(btnUp);TODO Temporary or Permenant
		super.add(btnDown);
		super.add(btnStop);*/

		super.addKeyListener(this);

		this.btnLeft.addActionListener(this);
		this.btnRight.addActionListener(this);
		this.btnDown.addActionListener(this);
		this.btnUp.addActionListener(this);
		this.btnStop.addActionListener(this);

		super.setVisible(true);

		this.gameLoop();
	}

	// *********************** processing
	// ***************************************
	public void gameLoop() {
		do {
			for (int x = 0; x < this.numTurtles; x++) {
				this.upDateLocation(this.thing[x]);
				this.thing[x].setHeading();
				for(int x1 = 0; x < this.numTurtles; x++) {
					this.checkCollision(this.thing[x], this.thing[x1]);
				}
			}
			pause(this.speed);
			this.paused = true;
			this.repaint(); // executes the paint method
			this.paused = false;
			this.repaint(); // executes the paint method
		} while (this.gameNotOver);
	}// end of constructor
	// ***************************** output
	// ****************************************

	@Override
	public void paint(Graphics pen) // Draws the window
	{
		this.myScreen = createImage(getSize().width, getSize().height);
		Graphics o = this.myScreen.getGraphics();
		doubleBuffer(o);
		pen.drawImage(this.myScreen, 0, 0, null);

	}

	public void doubleBuffer(Graphics pen) // for fast graphics
	{
		for (int x = 0; x < this.numTurtles; x++) {
			this.thing[x].drawOSprite(pen); // Draws the sprite
		}
	}
	// *********************** input *******************************************
	// *********************** abstract (interface) methods overridden
	// **********
	public void keyPressed(KeyEvent e) {
		System.out.println("hit + " + KeyEvent.getKeyText(e.getKeyCode()));
		switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				this.thing[0].setSpeedY(this.thing[0].getSpeedY() + 3);
				break; // control thing 0
			case KeyEvent.VK_RIGHT:
				this.thing[0].setSpeedX(this.thing[0].getSpeedX() + 3);
				break; // control thing 0
			case KeyEvent.VK_UP:
				this.thing[0].setSpeedY(this.thing[0].getSpeedY() - 3);
				break; // control thing 0
			case KeyEvent.VK_LEFT:
				this.thing[0].setSpeedX(this.thing[0].getSpeedX() - 3);
				break; // control thing 0
			case KeyEvent.VK_ESCAPE:
				this.gameNotOver = false;
				break;
			case KeyEvent.VK_END:
				this.thing[0].setSpeedY(0);
				this.thing[0].setSpeedX(0);
				break;
		}
	}

	public void keyReleased(KeyEvent e)// not used but abstract methods must be
										// overridden
	{
	}

	public void keyTyped(KeyEvent e) {
	}

	public void actionPerformed(ActionEvent b) {
		System.out.println("Clicked" + b.toString());
		if (b.getSource() == this.btnRight)
			this.thing[0].setSpeedX(5);
		if (b.getSource() == this.btnLeft)
			this.thing[0].setSpeedX(-5);
		if (b.getSource() == this.btnUp)
			this.thing[0].setSpeedY(-5);
		if (b.getSource() == this.btnDown)
			this.thing[0].setSpeedY(5);
		if (b.getSource() == this.btnStop) {
			this.thing[0].setSpeedY(0);
			this.thing[0].setSpeedX(0);
		}
	}

	public void mouseClicked(MouseEvent m) // not used but abstract methods must
											// be overridden
	{
	}

	public void mouseEntered(MouseEvent m) {
	}

	public void mouseExited(MouseEvent m) {
	}

	public void mousePressed(MouseEvent m) {
	}

	public void mouseReleased(MouseEvent m) {
	}

	// ********************************* processing *******************************

	public void checkCollision(Turtle t1, Turtle t2) {
		Rectangle2D rect1 = new Rectangle(t1.getX(), t1.getY(), t1.getPicWidth(), t1.getPicHeight());
		Rectangle2D rect2 = new Rectangle(t2.getX(), t2.getY(), t2.getPicWidth(), t2.getPicHeight());
		if(rect1.intersects(rect2)) {
			t1.setSpeedX(t1.getSpeedX() * -1);
			t2.setSpeedX(t2.getSpeedX() * -1);
		}
	}

	public void upDateLocation(Turtle t) {
		t.setTurtleX(t.getTurtleX() + t.getSpeedX());
		t.setTurtleY(t.getTurtleY() + t.getSpeedY());

		this.checkEdges(t);
	}

	public void checkEdges(Turtle t) {
		int halfWidth = t.getSize() * t.getPicWidth() / 2;
		int halfHeight = t.getSize() * t.getPicHeight() / 2;
		if (t.getTurtleX() > SCWIDTH - halfWidth) {
			t.setSpeedX(t.getSpeedX() * -1);
		}
		if(t.getTurtleX() - halfWidth < 0) {
			t.setSpeedX(t.getSpeedX() * -1);
		}
		if (t.getTurtleY() > SCHEIGHT - halfHeight) {
			t.setSpeedY(t.getSpeedY() * -1);
		}
		if(t.getTurtleY() - halfHeight < 30) {
			t.setSpeedY(t.getSpeedY() * -1);
		}
	}

	public static void pause(long r) {
		try {
			Thread.sleep(r);
		} catch (Exception e) {
			out.println(" sleep error " + e);
		}
	}
} // end class
// Klein ISD Acceptable use policy:
// 1. Student developed games and other programming projects may not include
// profane references to a deity,
// obscene language, drug abuse or paraphernalia, images of suicide or criminal
// violence, suggested or
// explicit sexuality, partial/full/frontal nudity, or any subject matter
// inappropriate for young adults,
// and any content that may offend the moral standards of the community are
// strictly prohibited. Students
// must receive teacher approval on game and project design before developing
// content within a class
// assignment or project.
//
// 2. All games developed, viewed, or shown/demonstrated on a Klein ISD
// computer, and/or as part of Klein ISD
// assignments and projects must meet the Entertainment Software Rating Board
// rating for Early Childhood or
// Everyone.
// (ESRB: http://www.esrb.org/ratings/ratings_guide.jsp#rating_categories)
//
// 3. Additionally, student created games and projects may not contain content
// that is categorized by the
// following ESRB content descriptors: Alcohol Reference, Animated Blood, Blood,
// Blood and Gore, Crude Humor,
// Drug Reference, Fantasy Violence, Intense Violence, Language, Lyrics, Mature
// Humor, Nudity, Partial Nudity,
// Real Gambling, Sexual Content, Sexual Themes, Sexual Violence, Simulated
// Gambling, and Strong Language.
//
