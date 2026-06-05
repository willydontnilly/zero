


//START
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.*;
public class zero extends JPanel implements KeyListener {
	boolean ACCELERATING = false;
	boolean CHANGELOGGING = true;
	boolean DEBUG = true;
	boolean DECELERATING = false;
	boolean DIRTY = true;
	boolean KEYLOGGING = false;
	boolean LOADLOGGING = true;
	boolean PURE = true;
	int ACCELERATION = 0;
	int BOOST;
	int BOUNCE = 0;
	int FORCE;
	int FRICTION;
	int GRAVITY;
	int HEIGHT;
	int LIGHTSPEED;	
	int OBJECTS;
	int TICKS = 0;
	int WIDTH;
	int X;
	int Y;
	String FILE = "maps/0.bin";
	int[][] MAP = {{0}};
	
	
	
	//Name: main (m)
	//Runs: at startup.
	//Does: set up the window, start the painting and key listening, and start physics' timer.
	public static void main(String[] args) {
		JFrame mWINDOW=new JFrame("");
		zero mPANEL = new zero();
		mWINDOW.add(mPANEL);
		mWINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mWINDOW.setVisible(true);	
		mWINDOW.addKeyListener(mPANEL);
		mWINDOW.setSize(1000,1000);
		mWINDOW.setLocationRelativeTo(null);
		mWINDOW.setExtendedState(JFrame.MAXIMIZED_BOTH);
		new Timer(1, e -> mPANEL.physics()).start();
	}
		
		
		
	//Name: paintComponent (p)
	//Runs: when you call repaint() in a mPANEL function.
	//Does: display what is supposed to be displayed on the window.
	protected void paintComponent(Graphics pDRAW) {
		super.paintComponent(pDRAW);
		int pOBJECTS = OBJECTS;
		while(0<pOBJECTS) {
			if(MAP[pOBJECTS][0] == 5) {
				pDRAW.setColor(new Color(222, 208, 224));
				pDRAW.fillRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
			}
			if(MAP[pOBJECTS][0] == 7) {
				pDRAW.setColor(new Color(222, 208, 224));
				pDRAW.drawRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
			}
			if(MAP[pOBJECTS][0] < 0) {
				pDRAW.setColor(new Color(0, 255, 0));
				pDRAW.fillRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
			}
			if(MAP[pOBJECTS][0] == 0) {
				pDRAW.setColor(new Color(0, 0, 0));
				pDRAW.fillRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
			}
			if(MAP[pOBJECTS][0] == 1) {
				pDRAW.setColor(new Color(255, 0, 0));
				pDRAW.fillRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
			}
			if(MAP[pOBJECTS][0] == 2) {
				pDRAW.setColor(new Color(0, 128, 255));
				pDRAW.fillRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
			}
			if(MAP[pOBJECTS][0] == 3) {
				pDRAW.setColor(new Color(255, 153, 0));
				pDRAW.fillRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
			}
			if(MAP[pOBJECTS][0] == 4) {
				pDRAW.setColor(new Color(128, 0, 128));
				pDRAW.fillRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
			}
			if(MAP[pOBJECTS][0] == 6) {
				pDRAW.setColor(new Color(128, 0, 128));
				pDRAW.drawRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
			}
			if(MAP[pOBJECTS][0] == 8) {
				pDRAW.setColor(new Color(11, 133, 0));
				pDRAW.fillRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
			}
			pOBJECTS = pOBJECTS - 1;
		}
		pDRAW.setColor(Color.BLACK);
		pDRAW.drawRect(X, Y, WIDTH, HEIGHT);
	}
		
		
		
	//Name: physics (h)
	//Runs: every milisecond.
	//Does: physics.
	public void physics() {
		TICKS = TICKS + 1;
		if(MAP[0][0] == 0) {
			if(LOADLOGGING) {
				System.out.println("[LOADLOGGING] 0 objects are stated in MAP, so the game will assume that the map needs reloading.");
			}
			try {
				try(ObjectInputStream pSTREAM = new ObjectInputStream(new FileInputStream(FILE))) {
					Object pOBJECT = pSTREAM.readObject();
					MAP = (int[][]) pOBJECT;
					if(LOADLOGGING) {
						System.out.println("[LOADLOGGING] Successfully loaded "+FILE+".");
					}
				}
			} catch(IOException | ClassNotFoundException e) {
				if(LOADLOGGING) {
					System.out.println("[LOADLOGGING] Failed to load "+FILE+".");
				}
			}
		}
		if(DIRTY) {
			OBJECTS = MAP[0][0];
			X = MAP[0][1];
			Y = MAP[0][2];
			WIDTH = MAP[0][3];
			HEIGHT = MAP[0][4];
			BOOST = MAP[0][5];
			FORCE = MAP[0][6];
			FRICTION = MAP[0][7];
			GRAVITY = MAP[0][8];
			LIGHTSPEED = MAP[0][9];
			DIRTY = false;
			if(!PURE) {
				MAP[0][0] = 0;
				PURE = true;
				if(LOADLOGGING) {
					System.out.println("[LOADLOGGING] PURE was set to false, so the game stated that there are 0 objects in order to trigger a map reload.");
				}
			}
		}
		if(ACCELERATING&ACCELERATION<LIGHTSPEED) {
			ACCELERATION = ACCELERATION + FORCE;
		}
		if(DECELERATING&LIGHTSPEED*-1<ACCELERATION) {
			ACCELERATION = ACCELERATION - FORCE;
		}
		if(0<BOUNCE) {
			int hRISE = GRAVITY;
			while(0<hRISE) {
				if(irrisable()) {
					BOUNCE = 0;
					hRISE = 0;
				}
				else {
					Y = Y-1;
					hRISE = hRISE-1;
				}
			}
			BOUNCE = BOUNCE-1;
		}
		else {
			int hFALL = GRAVITY;
			while(0 < hFALL) {
				if(unfallable()) {
					hFALL = 0;
				}
				else {
					Y = Y+1;
					hFALL = hFALL-1;
				}
			}
		}
		if(0 < ACCELERATION) {
			int hRIGHT = ACCELERATION;
			while(0 < hRIGHT) {
				if(unaccelerable()) {
					hRIGHT = 0;
				}
				else {
					X = X+1;
					hRIGHT = hRIGHT-1;
				}
			}
			ACCELERATION = ACCELERATION-FRICTION;
		}
		if(ACCELERATION < 0) {
			int hLEFT = ACCELERATION;
			while(hLEFT < 0) {
				if(undeccelerable()) {
					hLEFT = 0;
				}
				else {
					X = X-1;
					hLEFT = hLEFT+1;
				}
			}
			ACCELERATION = ACCELERATION+FRICTION;
		}
		repaint();
	}
	
	
	
	//Name: keyPressed (k)
	//Runs: when a key is pressed.
	//Does: save the pressed key as kKEY and run if statements to do something depending on what kKEY is.
	public void keyPressed(KeyEvent key) {
		String kKEY = KeyEvent.getKeyText(key.getKeyCode());
		if(KEYLOGGING) {
			System.out.println("[KEYLOGGING] "+kKEY);
		}
		if(kKEY.equals("Escape")) {
			System.exit(0);
		}
		if(kKEY.equals("Up")) {
			if(unfallable()) {
				BOUNCE = BOOST;	
			}
		}
		if(kKEY.equals("Left")) {
			DECELERATING = true;
		}
		if(kKEY.equals("Right")) {
			ACCELERATING = true;
		}
		if(kKEY.equals("F3")&DEBUG) {
			System.out.println("[DEBUG] ACCELERATING = "+ACCELERATING);
			System.out.println("[DEBUG] CHANGELOGGING = "+CHANGELOGGING);
			System.out.println("[DEBUG] DEBUG = "+DEBUG);
			System.out.println("[DEBUG] DECELERATING = "+DECELERATING);
			System.out.println("[DEBUG] DIRTY = "+DIRTY);
			System.out.println("[DEBUG] KEYLOGGING = "+KEYLOGGING);
			System.out.println("[DEBUG] LOADLOGGING = "+LOADLOGGING);
			System.out.println("[DEBUG] PURE = "+PURE);
			System.out.println("[DEBUG] ACCELERATION = "+ACCELERATION);
			System.out.println("[DEBUG] BOOST = "+BOOST);
			System.out.println("[DEBUG] BOUNCE = "+BOUNCE);
			System.out.println("[DEBUG] FORCE = "+FORCE);
			System.out.println("[DEBUG] FRICTION = "+FRICTION);
			System.out.println("[DEBUG] GRAVITY = "+GRAVITY);
			System.out.println("[DEBUG] HEIGHT = "+HEIGHT);
			System.out.println("[DEBUG] LIGHTSPEED = "+LIGHTSPEED);
			System.out.println("[DEBUG] OBJECTS = "+OBJECTS);
			System.out.println("[DEBUG] TICKS = "+TICKS);
			System.out.println("[DEBUG] WIDTH = "+WIDTH);
			System.out.println("[DEBUG] X = "+X);
			System.out.println("[DEBUG] Y = "+Y);
			System.out.println("[DEBUG] FILE = "+FILE);
			System.out.println("[DEBUG] MAP = "+MAP);
		}
	}
	
	
	
	//Name: keyReleased (e)
	//Runs: when a key is released.
	//Does: save the released key as eKEY and run if statements to do something depending on what eKEY is.
	public void keyReleased(KeyEvent key) {
		String eKEY = KeyEvent.getKeyText(key.getKeyCode());
		if(eKEY.equals("Right")) {
			ACCELERATING = false;
		}
		if(eKEY.equals("Left")) {
			DECELERATING = false;
		}
	}
	
	
	
	//Name: irrisable (i)
	//Runs: when irrisable() is called.
	//Does: return true if the player cannot rise and return false when the player can rise.
	public boolean irrisable() {
		int iOBJECTS = OBJECTS;
		while(0<iOBJECTS) {
			if(MAP[iOBJECTS][0] != 5   &   MAP[iOBJECTS][0] != 7   &   Y-MAP[iOBJECTS][4] == MAP[iOBJECTS][2]   &   MAP[iOBJECTS][1]-WIDTH < X   &   X < MAP[iOBJECTS][1]+MAP[iOBJECTS][3]) {
				collision(MAP[iOBJECTS][0]);
				return true;
			}
			iOBJECTS = iOBJECTS - 1;
		}
		return false;
	}
	
		
		
	//Name: unfallable (u)
	//Runs: when unfallable() is called.
	//Does: return true if the player cannot fall and return false when the player can fall.
	public boolean unfallable() {
		int uOBJECTS = OBJECTS;
		while(0<uOBJECTS) {
			if(MAP[uOBJECTS][0] != 5   &   MAP[uOBJECTS][0] != 7   &   Y+HEIGHT == MAP[uOBJECTS][2]   &   MAP[uOBJECTS][1]-WIDTH < X   &   X < MAP[uOBJECTS][1]+MAP[uOBJECTS][3]) {
				collision(MAP[uOBJECTS][0]);
				return true;
			}
			uOBJECTS = uOBJECTS - 1;
		}
		return false;
	}
		
		
		
	//Name: unaccelerable (n)
	//Runs: when unaccelerable() is called.
	//Does: return true if the player cannot go right and return false when the player can go left.
	public boolean unaccelerable() {
		int nOBJECTS = OBJECTS;
		while(0<nOBJECTS) {
			if(MAP[nOBJECTS][0] != 5   &   MAP[nOBJECTS][0] != 7   &   X+WIDTH == MAP[nOBJECTS][1]   &   MAP[nOBJECTS][2]-HEIGHT < Y   &   Y < MAP[nOBJECTS][2]+MAP[nOBJECTS][4]) {
				collision(MAP[nOBJECTS][0]);
				return true;
			}
			nOBJECTS = nOBJECTS - 1;
		}
		return false;
	}
	
		
		
	//Name: undeccelerable (d)
	//Runs: when undeccelerable() is called.
	//Does: return true if the player cannot go left and return false when the player can go left.
	public boolean undeccelerable() {
		int dOBJECTS = OBJECTS;
		while(0<dOBJECTS) {
			if(MAP[dOBJECTS][0] != 5   &   MAP[dOBJECTS][0] != 7   &   X-MAP[dOBJECTS][3] == MAP[dOBJECTS][1]   &   MAP[dOBJECTS][2]-HEIGHT < Y   &   Y < MAP[dOBJECTS][2]+MAP[dOBJECTS][4]) {
				collision(MAP[dOBJECTS][0]);
				return true;
			}
			dOBJECTS = dOBJECTS - 1;
		}
		return false;
	}
		
		
		
	//Name: collision (c)
	//Runs: when collision(cTYPE) is called with cTYPE being an integer declaring the type of object the player is colliding with.
	//Does: any action that is supposed to be done when the player is colliding with that type of object.
	public void collision(int cTYPE) {
		if(cTYPE < 0) {
			FILE = "maps/"+Integer.toString(cTYPE*-1)+".bin";
			MAP[0][0] = 0;
			DIRTY = true;
		}
		if(cTYPE == 1) {
			DIRTY = true;
		}
		if(cTYPE == 2) {
			if(BOOST < MAP[0][5]*2) {
				BOOST = BOOST+1;
				if(CHANGELOGGING) {
					System.out.println("[CHANGELOGGING] collision has increased BOOST by 1.");
				}
			}
			if(GRAVITY < MAP[0][8]*2) {
				GRAVITY = GRAVITY+1;
				if(CHANGELOGGING) {
					System.out.println("[CHANGELOGGING] collision has increased GRAVITY by 1.");
				}
			}
			if(MAP[0][4]/2 < HEIGHT) {
				HEIGHT = HEIGHT-1;
				if(CHANGELOGGING) {
					System.out.println("[CHANGELOGGING] collision has decreased HEIGHT by 1.");
				}
			}
			if(MAP[0][3]/2 < WIDTH) {
				WIDTH = WIDTH-1;
				if(CHANGELOGGING) {
					System.out.println("[CHANGELOGGING] collision has decreased WIDTH by 1.");
				}
			}
		}
		if(cTYPE == 3) {
			if(MAP[0][5]/2 < BOOST) {
				BOOST = BOOST-1;
				if(CHANGELOGGING) {
					System.out.println("[CHANGELOGGING] collision has decreased BOOST by 1.");
				}
			}
			if(MAP[0][8]/2 < GRAVITY) {
				GRAVITY = GRAVITY-1;
				if(CHANGELOGGING) {
					System.out.println("[CHANGELOGGING] collision has decreased GRAVITY by 1.");
				}
			}
			if(HEIGHT < MAP[0][4]*2) {
				HEIGHT = HEIGHT+1;
				if(CHANGELOGGING) {
					System.out.println("[CHANGELOGGING] collision has increased HEIGHT by 1.");
				}
				Y = Y-1;
				if(CHANGELOGGING) {
					System.out.println("[CHANGELOGGING] collision has decreased Y by 1.");
				}
			}
			if(WIDTH < MAP[0][3]*2) {
				WIDTH = WIDTH+1;
				if(CHANGELOGGING) {
					System.out.println("[CHANGELOGGING] collision has increased WIDTH by 1.");
				}
			}
		}
		if(cTYPE == 4) {
			int cOBJECTS = OBJECTS;
			while(0<cOBJECTS) {
				if(MAP[cOBJECTS][0] == 6) {
					MAP[cOBJECTS][0] = 7;
					if(CHANGELOGGING) {
						System.out.println("[CHANGELOGGING] collision has set MAP["+cOBJECTS+"][0] to 7.");
					}
					X = MAP[cOBJECTS][1];
					if(CHANGELOGGING) {
						System.out.println("[CHANGELOGGING] collision has set X to "+MAP[cOBJECTS][1]+".");
					}
					Y = MAP[cOBJECTS][2];
					if(CHANGELOGGING) {
						System.out.println("[CHANGELOGGING] collision has set X to "+MAP[cOBJECTS][2]+".");
					}
					WIDTH = MAP[cOBJECTS][3];
					if(CHANGELOGGING) {
						System.out.println("[CHANGELOGGING] collision has set X to "+MAP[cOBJECTS][3]+".");
					}
					HEIGHT = MAP[cOBJECTS][4];
					if(CHANGELOGGING) {
						System.out.println("[CHANGELOGGING] collision has set X to "+MAP[cOBJECTS][4]+".");
					}
					PURE = false;
					if(LOADLOGGING) {
						System.out.println("[LOADLOGGING] collision has set PURE to false, meaning the map will be refreshed from memory next time the map data needs to be refreshed from disk.");
					}
					break;
				}
				cOBJECTS = cOBJECTS - 1;
			}
			MAP[cTYPE][0] = 5;
		}
		if(cTYPE == 8) {
			FILE = "maps/0.bin";
			MAP[0][0] = 0;
			DIRTY = true;
		}
	}
	
		
		
	//Name: keyTyped (y)
	//Runs: something about keys. I actually have no idea what this is used for, but the code doesn't run when I delete it.
	//Does: nothing.
	public void keyTyped(KeyEvent key) {
	}
	
	
	
	//END
}