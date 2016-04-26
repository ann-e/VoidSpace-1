package rbadia.voidspace.main;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import rbadia.voidspace.model.Ship;

/**
 * Handles user input events.
 */
public class InputHandler implements KeyListener{
	private boolean leftIsPressed;
	private boolean rightIsPressed;
	private boolean downIsPressed;
	private boolean upIsPressed;
	private boolean spaceIsPressed;
	private boolean shiftIsPressed;
	
	private long lastBulletTime;
	
	private GameLogic gameLogic;
	
	/**
	 * Create a new input handler
	 * @param gameLogic the game logic handler
	 */
	public InputHandler(GameLogic gameLogic){
		this.gameLogic = gameLogic;
	}
	
	/**
	 * Handle user input after screen update.
	 * @param gameScreen he game screen
	 */
	public void handleInput(GameScreen gameScreen){
		GameStatus status = gameLogic.getStatus();
		if(!status.isGameOver() && !status.isNewShip() && !status.isGameStarting()){
			// fire bullet if space is pressed
			if(spaceIsPressed){
				// fire only up to 5 bullets per second
				long currentTime = System.currentTimeMillis();
				if((currentTime - lastBulletTime) > 1000/5){
					lastBulletTime = currentTime;
					gameLogic.fireBullet();
				}
			}

			Ship ship = gameLogic.getShip();
			
			if(shiftIsPressed  && (ship != null)){
				ship.setSpeed(ship.getDefaultSpeed() * 2);
			} else {
//				o();
			}

			if(upIsPressed && (ship != null)){
				moveShipUp(ship);
			}

			if(downIsPressed  && (ship != null)){
				moveShipDown(ship, gameScreen.getHeight());
			}

			if(leftIsPressed  && (ship != null)){
				moveShipLeft(ship);
			}

			if(rightIsPressed  && (ship != null)){
				moveShipRight(ship, gameScreen.getWidth());
			}
		}
	}

	/**
	 * Move the ship up
	 * @param ship the ship
	 */
	private void moveShipUp(Ship ship){
		if(ship.getY() - ship.getSpeed() >= 0){
			ship.translate(0, -ship.getSpeed());
		}
	}

	/**
	 * Move the ship down
	 * @param ship the ship
	 */
	private void moveShipDown(Ship ship, int screenHeight){
		if(ship.getY() + ship.getSpeed() + ship.height < screenHeight){
			ship.translate(0, ship.getSpeed());
		}
	}
	
	/**
	 * Move the ship left
	 * @param ship the ship
	 */
	private void moveShipLeft(Ship ship){
		if(ship.getX() - ship.getSpeed() >= 0){
			ship.translate(-ship.getSpeed(), 0);
		}
	}
	
	/**
	 * Move the ship right
	 * @param ship the ship
	 */
	private void moveShipRight(Ship ship, int screenWidth){
		if(ship.getX() + ship.getSpeed() + ship.width < screenWidth){
			ship.translate(ship.getSpeed(), 0);
		}
	}
	
//	JOptionPane.showMessageDialog(this.mainFrame, HOWTOPLAYTEXT
//			, "How To Play", JOptionPane.PLAIN_MESSAGE);
	
	public void o() {

		JFrame f = new JFrame("INFO");
		f.setBounds(50, 50, 200, 200);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	/**
	 * Handle a key input event.
	 */
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			this.upIsPressed = true;
			break;
		case KeyEvent.VK_DOWN:
			this.downIsPressed = true;
			break;
		case KeyEvent.VK_LEFT:
			this.leftIsPressed = true;
			break;
		case KeyEvent.VK_RIGHT:
			this.rightIsPressed = true;
			break;
		case KeyEvent.VK_SPACE:
			GameStatus status = gameLogic.getStatus();
			if(!status.isGameStarted() && !status.isGameOver() && !status.isGameStarting()){
				// new game
				lastBulletTime = System.currentTimeMillis();
				leftIsPressed = false;
				rightIsPressed = false;
				downIsPressed = false;
				upIsPressed = false;
				spaceIsPressed = false;
				
				gameLogic.newGame();
			}
			else{
				this.spaceIsPressed = true;
			}
			break;
		case KeyEvent.VK_SHIFT:
			GameStatus status2 = gameLogic.getStatus();
			if (!status2.isGameStarted() && !status2.isGameOver() && !status2.isGameStarting()) {
				o();
			}
			this.shiftIsPressed = true;
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(1);
			break;
		}
		e.consume();
	}

	/**
	 * Handle a key release event.
	 */
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			this.upIsPressed = false;
			break;
		case KeyEvent.VK_DOWN:
			this.downIsPressed = false;
			break;
		case KeyEvent.VK_LEFT:
			this.leftIsPressed = false;
			break;
		case KeyEvent.VK_RIGHT:
			this.rightIsPressed = false;
			break;
		case KeyEvent.VK_SPACE:
			this.spaceIsPressed = false;
			break;
		case KeyEvent.VK_SHIFT:
			this.shiftIsPressed = false;

			Ship ship = gameLogic.getShip(); 
			if (ship != null) {
			ship.setSpeed(ship.getDefaultSpeed());
			break;
			} else
				o();
		}
		e.consume();
	}

	public void keyTyped(KeyEvent e) {
		// not used
	}
}
