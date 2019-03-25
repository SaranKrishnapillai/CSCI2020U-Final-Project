package sample;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import java.util.Random;
public class Game_Content{
	public static final double W = 800, H = 600;
	private static final String MISSILE_IMAGE = "sample/missile.png";
	private static final String PLAYER_IMAGE = "sample/ship.png";
	private static final String ASTEROID_IMAGE = "sample/Asteroid.png";
	private static final String ENEMY_IMAGE = "sample/Tie fighter.png";
	private static final String COIN_IMAGE = "sample/JSR.jpg";
	private static final String VERTICAL_MISSILE_IMAGE = "sample/VerticalMissile.png";
	
	private Image missileImage, asteroidImage;
	private ImageView bg1, bg2; 
	private Node hero; 
	private Node enemy1, enemy2, enemy3, enemy4, enemy5;
	private Node asteroid1, asteroid2;
	private ArrayList<Node> missiles = new ArrayList<Node>();
	private ArrayList<Node> villains = new ArrayList<Node>();
	private ArrayList<Node> asteroids = new ArrayList<Node>();
	private ArrayList<Node> enemies = new ArrayList<Node>();
	private ArrayList<Node> coins = new ArrayList<Node>();
	private ArrayList<Node> bMissiles = new ArrayList<Node>();
	private ArrayList<Node> enemyMissiles = new ArrayList<Node>();
	private ArrayList<Node> enemyMissiles2 = new ArrayList<Node>();
	private ArrayList<Node> enemyMissiles3 = new ArrayList<Node>();
	private ArrayList<Node> enemyMissiles4 = new ArrayList<Node>();
	private ArrayList<Node> enemyMissiles5 = new ArrayList<Node>();
	private Node coin1, coin2;

	public static Group board; 
	Label scoreText;
	public int score ;
	boolean goUp, goDown, goRight, goLeft; 
	boolean shooting; 
	AnimationTimer timer; 
	private int numEnemies = 1;

	public Game_Content(){
		board = new Group();
		setUp();
		animate();
	}

	private void setUp() {
		score = 0; 
		shooting = false; 
		//villainImage = new Image()
		missileImage = new Image(MISSILE_IMAGE);
		asteroidImage = new Image(ASTEROID_IMAGE);
		bg1 = new ImageView(new Image("sample/SPACE.png"));
		bg2 = new ImageView(new Image("sample/SPACE.png"));

		bg1.relocate(0, 0);
		bg2.relocate(800, 0);

		coin1 = new ImageView(new Image(COIN_IMAGE));
		coin2 = new ImageView(new Image(COIN_IMAGE));


		hero = new ImageView(new Image("sample/ship.png"));
		hero.relocate(0, 0);

		enemy1 = new ImageView(new Image(ENEMY_IMAGE));
		enemy2 = new ImageView(new Image(ENEMY_IMAGE));
		enemy3 = new ImageView(new Image(ENEMY_IMAGE));
		enemy4 = new ImageView(new Image(ENEMY_IMAGE));
		enemy5 = new ImageView(new Image(ENEMY_IMAGE));

		scoreText = new Label("Scores: 0");
		scoreText.relocate(170, 10);
		scoreText.setTextFill(Color.WHITE);

		Button help = new Button("Help");
		help.relocate(500, 10);
		help.setOnAction(e -> helpScreen());
		help.setFocusTraversable(false);

		board.getChildren().addAll(bg1,bg2,hero,scoreText,help);

	}

	public void animate() {
		timer = new AnimationTimer() {
			int enemyCounter = 0; 
			Random random = new Random(); 

			@Override
			public void handle(long arg0) {

				int dx = 0;
				int dy = 0;

				if(goUp) dy-=4;
				if(goDown) dy+=4;
				if(goRight) dx +=4; 
				if(goLeft) dx-=4;
				//System.out.println(asteroidCounter % modifier==0);
				if(random.nextInt(100) == 1) {
					createAsteroids();
					//bombardment();
				}

				if(random.nextInt(100) == 1) {
					createCoin();
				}


				if(enemyCounter < numEnemies) {
					createEnemy();
					enemyCounter++;
				}

				
				//System.out.println(asteroidCounter % modifier == 0);


				moveHeroTo(hero.getLayoutX() + dx, hero.getLayoutY() + dy);
				moveAsteroids();
				moveEnemies((int)hero.getLayoutX(), (int)hero.getLayoutY()); 
				moveCoins();
				fireBombardment();
				fireMissiles();
				checkHit();
				scrollBackground();
				firingPattern();
				enemyFire();
			}

		};
	}
	
	public void createCoin() {
		ImageView aCoin = new ImageView(COIN_IMAGE);
		Node newCoin = aCoin; 

		newCoin.relocate(W,  (int)(Math.random()*(H-50)+10));
		coins.add(newCoin);
		board.getChildren().addAll(newCoin);

	}

	public void moveCoins() {
		int dx = -5; 

		for(int i = 0; i < coins.size(); i++) {
			if(coins.get(i).getLayoutX() > -50) {
				coins.get(i).setLayoutX(coins.get(i).getLayoutX() + dx);
			}else {
				board.getChildren().remove(coins.get(i));
				coins.remove(i);
			}

		}
	}

	public void createEnemy() {
		Enemy enemy = new Enemy();
		Node newEnemy = enemy.enemy; 

		newEnemy.relocate(W, (int)(Math.random()*(H-50)+10));
		enemies.add(newEnemy);
		board.getChildren().add(newEnemy);
		
		
	}

	public void moveEnemies(int px, int py) {
		int dx = 0;
		int dy = 0; 

		for(int i = 0; i < enemies.size(); i++) {
			if(px > enemies.get(i).getLayoutX()){
				dx = +2;
			}
			if(px < enemies.get(i).getLayoutX()){ 
				dx = -2;
			}	
			if(py < enemies.get(i).getLayoutY()){
				dy = -2;
			}
			if(py > enemies.get(i).getLayoutY()){
				dy = 2;
			}

			enemies.get(i).setLayoutX(enemies.get(i).getLayoutX() + dx);
			enemies.get(i).setLayoutY(enemies.get(i).getLayoutY() + dy);


		}

	}

	public void createEnemyMissile() {
		ImageView aMissile = new ImageView(MISSILE_IMAGE);
		Node newMissile = aMissile;

		// There is only a maximum of 5 enemies. 
		// Each enemy will have separate missile array list.
		// add distinct missiles to avoid earlier error. 
		
		for(int i = 0; i < enemies.size(); i++) {
			newMissile.relocate(enemies.get(i).getLayoutX() + enemies.get(i).getBoundsInLocal().getWidth(),enemies.get(i).getLayoutY());
			enemyMissiles.add(newMissile);
			board.getChildren().add(newMissile);
			break;
		}
	}

	public void enemyFire() {
		for(int i = 0; i < enemyMissiles.size(); i++) {
			enemyMissiles.get(i).setLayoutX(enemyMissiles.get(i).getLayoutX() -10);
			if(enemyMissiles.get(i).getLayoutX() > W) {
				enemyMissiles.remove(i);
			}
		}
	}

	public void createAsteroids() {

		ImageView anAsteroid = new ImageView(asteroidImage);
		Node newAsteroid = anAsteroid; 

		newAsteroid.relocate(W,  (int)(Math.random()*(H-50)+10));
		asteroids.add(newAsteroid);
		board.getChildren().addAll(newAsteroid);

	}

	public void moveAsteroids() {
		int dx = -5; 

		for(int i = 0; i < asteroids.size(); i++) {
			if(asteroids.get(i).getLayoutX() > -50) {
				asteroids.get(i).setLayoutX(asteroids.get(i).getLayoutX() + dx);
			}else {
				board.getChildren().remove(asteroids.get(i));
				asteroids.remove(i);
			}

		}
	}

	public void scrollBackground() {
		bg1.setLayoutX(bg1.getLayoutX()-1);
		bg2.setLayoutX(bg2.getLayoutX()-1);

		if(bg1.getLayoutX() < -800) {
			bg1.setLayoutX(795);
		}
		if(bg2.getLayoutX() < -800) {
			bg2.setLayoutX(795);
		}
	}

	public void firingPattern() {
		for(int i = 0; i < enemies.size(); i++) {
			int FP = (int)(Math.random()*100)+1;
			if(FP == 1) {
				createEnemyMissile();
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		switch(e.getCode()) {
		case UP: goUp= true; break; 
		case DOWN:goDown=true; break;
		case RIGHT: goRight=true;break;
		case LEFT: goLeft=true;break; 
		case SPACE: 
			if(!shooting) {
				shooting = true; 
				createMissile();
			}
			break; 
		}
	}

	public void keyReleased(KeyEvent e) {
		switch(e.getCode()) {
		case UP: goUp= false; break; 
		case DOWN:goDown=false; break;
		case RIGHT: goRight=false;break;
		case LEFT: goLeft=false;break; 
		case SPACE: shooting = false; break; 
		}
	}

	private void moveHeroTo(double x,double y) {
		if(y >= 0 && y <= H - hero.getBoundsInLocal().getHeight()) {
			hero.setLayoutY(y);
		}
		if(x >= 0 && x <= W - hero.getBoundsInLocal().getWidth()) {
			hero.setLayoutX(x);
		}
	}

	public void createMissile() {
		ImageView aMissile = new ImageView("sample/missile.png");
		Node newMissile = aMissile;

		newMissile.relocate(hero.getLayoutX() + hero.getBoundsInLocal().getWidth(),hero.getLayoutY());
		missiles.add(newMissile);
		board.getChildren().add(newMissile);

	}

	public void fireMissiles() {
		for(int i = 0; i < missiles.size(); i++) {
			missiles.get(i).setLayoutX(missiles.get(i).getLayoutX() + 10);
			if(missiles.get(i).getLayoutX() > W) {
				missiles.remove(i);
			}
		}
	}

	public void bombardment() {
		for(int i = 30; i < W; i += 30) {
			ImageView aMissile = new ImageView(VERTICAL_MISSILE_IMAGE);
			Node newMissile = aMissile;

			newMissile.relocate(i, 0);
			bMissiles.add(newMissile);
			board.getChildren().add(newMissile);
		}
	}

	public void fireBombardment() {
		for(int i = 0; i < bMissiles.size(); i++) {
			bMissiles.get(i).setLayoutY(bMissiles.get(i).getLayoutY() + 10);
			if(bMissiles.get(i).getLayoutY() > H) {
				bMissiles.remove(i);
			}

		}
	}

	public void checkHit() {

		for(int j = 0; j < coins.size(); j++) {
			if(hero.getBoundsInParent().intersects(coins.get(j).getBoundsInParent())) {
				board.getChildren().removeAll(coins.get(j));
				coins.remove(j);
				bombardment();
			}
		}

		for(int j = 0; j < asteroids.size(); j++) {
			if(hero.getBoundsInParent().intersects(asteroids.get(j).getBoundsInParent())){

				gameOver();
			}
		}

		for(int i = 0; i < missiles.size(); i++) {
			for(int j = 0; j < villains.size(); j++) {
				if(missiles.get(i).getBoundsInParent().intersects(villains.get(j).getBoundsInParent())) {
					board.getChildren().removeAll(villains.get(j),missiles.get(i));
					villains.remove(j);
					missiles.remove(i);
					score++;
					scoreText.setText("Score: " + score);
					break; 
				}
			}


			for(int j = 0; j < asteroids.size(); j++) {
				if(missiles.get(i).getBoundsInParent().intersects(asteroids.get(j).getBoundsInParent())) {
					board.getChildren().removeAll(asteroids.get(j),missiles.get(i));
					asteroids.remove(j);
					missiles.remove(i);
					score++;
					scoreText.setText("Score: " + score);
					break; 
				}

			}



		}
	}


	private void helpScreen() {
		timer.stop();

		Stage helpStage = new Stage(); 
		helpStage.setTitle("Help");
		helpStage.setMinWidth(450);
		helpStage.initModality(Modality.APPLICATION_MODAL);


		Label helpLabel = new Label();
		helpLabel.setText("Arrow keys to move, space to shoot. \nDestroy Asteroid and Enemies for points.");

		Button okButton = new Button("Ok");
		okButton.setOnAction(e -> {
			helpStage.close();
			timer.start();
		});

		VBox pane = new VBox(20);
		pane.getChildren().addAll(helpLabel, okButton);
		pane.setAlignment(Pos.CENTER);

		Scene helpScene = new Scene(pane);
		helpStage.setScene(helpScene);
		helpStage.showAndWait();

	}

	public void gameOver() {
		Text gameOver = new Text(W/2 -50, H/2, "Game Over!");
		gameOver.setFill(Color.RED);
		gameOver.setFont(Font.font("Verdana",20));
		board.getChildren().add(gameOver);
		timer.stop();
	}

	public Parent getRoot() {
		return board; 
	}

}
