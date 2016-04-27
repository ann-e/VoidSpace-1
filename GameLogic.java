package rbadia.voidspace.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
//import java.util.Timer;



import javax.swing.Timer;

import rbadia.voidspace.model.Asteroid;
import rbadia.voidspace.model.Bullet;
import rbadia.voidspace.model.EnemyShip;
import rbadia.voidspace.model.Ship;
import rbadia.voidspace.sounds.SoundManager;




/**
 * Handles general game logic and status.
 */
public class GameLogic extends TimerTask {
	protected GameScreen gameScreen;
	protected GameStatus status;
	private SoundManager soundMan;
	private Level level;
	
	private Ship ship;
	private Asteroid asteroid;
	private List<Asteroid> asteroids;
	private List<Bullet> bullets;
	private EnemyShip enemyShip;
	
	private int ASTEROIDS_TIMING = 2000;
	java.util.Timer asteroidTimer = new java.util.Timer(true);
	

	
	/**
	 * Create a new game logic handler
	 * @param gameScreen the game screen
	 */
	public GameLogic(GameScreen gameScreen){
		this.gameScreen = gameScreen;
		java.util.Timer asteroidTimer = new java.util.Timer(true);
		// initialize game status information
		status = new GameStatus();
		// initialize the sound manager
		soundMan = new SoundManager();
		
		// init some variables
		bullets = new ArrayList<Bullet>();
	}

	/**
	 * Returns the game status
	 * @return the game status 
	 */
	public GameStatus getStatus() {
		return status;
	}

	public SoundManager getSoundMan() {
		return soundMan;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	/**
	 * Prepare for a new game.
	 */
	public void newGame(){  						// we can accept these as parameters in a levelUp method
		status.setGameStarting(true);
		
		// init game variables
		bullets = new ArrayList<Bullet>();
		asteroids = new ArrayList<Asteroid>();

		status.setShipsLeft(3);
		status.setGameOver(false);
		status.setAsteroidsDestroyed(0);
		status.setNewAsteroid(false);
				
		// init the ship, EnemyShip and the Asteroid Timer Task
        newShip(gameScreen);
        //newAsteroid(gameScreen);  
        newEnemyShip(gameScreen);
       
        
        
        // prepare game screen
        gameScreen.doNewGame();
        
        // delay to display "Get Ready" message for 1.5 seconds
		Timer timer = new Timer(1500, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameStarting(false);
				status.setGameStarted(true);
			}
		});
		timer.setRepeats(false);
		timer.start();
		
		// spawn an asteroid every 2 seconds
		asteroidsBegin(ASTEROIDS_TIMING);
	}
	
	
	public void levelUp() {
		
		int asteroidsDestroyed = (int) status.getAsteroidsDestroyed();
		
		switch (asteroidsDestroyed) {
		
		case 5: 
			
		case 25:
			System.out.println("lvl 3");
		case 50: 
			System.out.println("lvl 4");
		case 75:
			System.out.println("lvl 5");
		
		
		
		}

	}
	
	/**
	 * Check game or level ending conditions.
	 */
	public void checkConditions(){
		// check game over conditions
		if(!status.isGameOver() && status.isGameStarted()){
			if(status.getShipsLeft() == 0){
				gameOver();
			}
		}
	}
	
	/**
	 * Actions to take when the game is over.
	 */
	public void gameOver(){
		status.setGameStarted(false);
		status.setGameOver(true);
		gameScreen.doGameOver();
		
        // delay to display "Game Over" message for 3 seconds
		Timer timer = new Timer(3000, new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				status.setGameOver(false);
			}
		});
		timer.setRepeats(false);
		timer.start();java.util.Timer asteroidTimer = new java.util.Timer(true);
	}
	
	/**
	 * Fire a bullet from ship.
	 */
	public void fireBullet(){
		Bullet bullet = new Bullet(ship);
		bullets.add(bullet);
		soundMan.playBulletSound();
	}
	
	/**
	 * Move a bullet once fired.
	 * @param bullet the bullet to move
	 * @return if the bullet should be removed from screen
	 */
	public boolean moveBullet(Bullet bullet){
		if(bullet.getY() - bullet.getSpeed() >= 0){
			bullet.translate(0, -bullet.getSpeed());
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * Create a new ship (and replace current one).
	 */
	public Ship newShip(GameScreen screen){
		this.ship = new Ship(screen);
		return ship;
	}
	
	/**
	 * Creates a new enemy ship.
	 */
	public EnemyShip newEnemyShip(GameScreen screen) {
		this.enemyShip = new EnemyShip(screen);
		return enemyShip;
	}
	
	/**
	 * Create a new asteroid.
	 */
	public Asteroid newAsteroid(GameScreen screen){
		this.asteroid = new Asteroid(screen);
		return asteroid;
	}
	
	/**
	 * Returns the ship.
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}
	
	/**
	 * Returns the enemy ship.
	 * @return enemy ship
	 */
	public EnemyShip getEnemyShip() {
		return enemyShip;
	}

	/**
	 * Returns the list of asteroids.
	 * @return the list of asteroids
	 */
	public List<Asteroid> getAsteroids() {
		return asteroids;
	}

	/**
	 * Returns the list of bullets.
	 * @return the list of bullets
	 */
	public List<Bullet> getBullets() {
		return bullets;
	}
	
	/**
	 * Creates new a new asteroid and adds it to the 
	 * list of asteroids.
	 */
	
	public void run() {
		asteroids.add(newAsteroid(gameScreen));
	}
	
	public void asteroidsBegin(int asteroidTiming) {
		asteroidTimer.schedule(this, 0, asteroidTiming);
	}
	
	
	
	
	

	
	
}
