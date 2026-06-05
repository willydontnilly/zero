


//START
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
public class zero extends JPanel implements KeyListener {
	boolean ACCELERATING = false;
	boolean DECELERATING = false;
	boolean DEBUG = true;
	boolean DIRTY = true;
	boolean KEYLOGGING = false;
	boolean LOADLOGGING = true;
	int ACCELERATION = 0;
	int BOOST;
	int BOUNCE = 0;
	int FORCE;
	int FRICTION;
	int GRAVITY;
	int HEIGHT;
	int LIGHTSPEED;
	int OBJECTS;
	int WIDTH;
	int X;
	int Y;
	int[][] MAP = {{6,100,100,50,50,8,3,1,25,6},{0,100,200,700,10},{0,100,350,800,10},{0,100,650,1000,10},{1,400,625,10,10},{2,350,575,10,10},{1,-5000,800,10000,10000}};
	
	
	
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
		pDRAW.drawRect(X, Y, WIDTH, HEIGHT);
		int pOBJECTS = OBJECTS;
		while(0<pOBJECTS) {
			if(MAP[pOBJECTS][0] == 0) {
				pDRAW.drawRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
			}
			if(MAP[pOBJECTS][0] == 1) {
				pDRAW.setColor(Color.RED);
				pDRAW.fillRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
				pDRAW.setColor(Color.BLACK);
			}
			if(MAP[pOBJECTS][0] == 2) {
				pDRAW.setColor(Color.GREEN);
				pDRAW.fillRect(MAP[pOBJECTS][1], MAP[pOBJECTS][2], MAP[pOBJECTS][3], MAP[pOBJECTS][4]);
				pDRAW.setColor(Color.BLACK);
			}
			pOBJECTS = pOBJECTS - 1;
		}
	}
		
		
		
	//Name: physics (h)
	//Runs: every milisecond.
	//Does: physics.
	public void physics() {
		if(MAP[0][0] == 0) {
			if(LOADLOGGING) {
				System.out.println("[LOADLOGGING] 0 objects are stated in MAP, so the game will assume that the map needs reloading.");
			}
			try {
				try(ObjectInputStream hSTREAM = new ObjectInputStream(new FileInputStream("map.bin"))) {
					Object hOBJECT = hSTREAM.readObject();
					MAP = (int[][]) hOBJECT;
					DIRTY = true;
					if(LOADLOGGING) {
						System.out.println("[LOADLOGGING] Successfully loaded map.bin.");
					}
				}
			} catch(IOException | ClassNotFoundException e) {
				if(LOADLOGGING) {
					System.out.println("[LOADLOGGING] Failed to load map.bin.");
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
		else if(true) {
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
			if(true) {
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
			}
			ACCELERATION = ACCELERATION-FRICTION;
		}
		if(ACCELERATION < 0) {
			if(true) {
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
		if(kKEY.equals("F1")) {
			if(LOADLOGGING) {
				System.out.println("[LOADLOGGING] F1 was pressed, which will set the ammount of objects stated to 0 in order to trigger a refresh.");
			}
			MAP[0][0] = 0;
		}
		if(kKEY.equals("F2")) {
			DIRTY = true;
		}
		if(kKEY.equals("F3")) {
			if(DEBUG) {
				System.out.println("[DEBUG] ACCELERATING = " + ACCELERATING);
			}
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
			if(Y-MAP[iOBJECTS][4] == MAP[iOBJECTS][2]   &   MAP[iOBJECTS][1]-WIDTH < X   &   X < MAP[iOBJECTS][1]+MAP[iOBJECTS][3]) {
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
			if(Y+HEIGHT == MAP[uOBJECTS][2]   &   MAP[uOBJECTS][1]-WIDTH < X   &   X < MAP[uOBJECTS][1]+MAP[uOBJECTS][3]) {
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
			if(X+WIDTH == MAP[nOBJECTS][1]   &   MAP[nOBJECTS][2]-HEIGHT < Y   &   Y < MAP[nOBJECTS][2]+MAP[nOBJECTS][4]) {
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
			if(X-MAP[dOBJECTS][3] == MAP[dOBJECTS][1]   &   MAP[dOBJECTS][2]-HEIGHT < Y   &   Y < MAP[dOBJECTS][2]+MAP[dOBJECTS][4]) {
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
		if(cTYPE == 1) {
			DIRTY = true;
		}
		if(cTYPE == 2) {
			JOptionPane.showMessageDialog(null,"You win!","",JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}
	
		
		
	//Name: keyTyped (y)
	//Runs: something about keys. I actually have no idea what this is used for, but the code doesn't run when I delete it.
	//Does: nothing.
	public void keyTyped(KeyEvent key) {
	}
	
	
	
	//END
}