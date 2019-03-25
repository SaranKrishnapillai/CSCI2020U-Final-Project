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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import java.util.Random;
public class Game_Content {
	public static final double W = 800, H = 600;
	private static final String MISSILE_IMAGE = "missile.png";
	private static final String PLAYER_IMAGE = "ship.png";
	private static final String ASTEROID_IMAGE = "Asteroid.png";
	private static final String ENEMY_IMAGE = "Tie fighter.png";
	private static final String COIN_IMAGE = "JSR.jpg";
	private static final String VERTICAL_MISSILE_IMAGE = "VerticalMissile.png";

	private Image missileImage, asteroidImage;
	private ImageView bg1, bg2; 
	private Node hero; 

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

	public static Group board; 
	Label scoreText;
	public int score ;
	boolean goUp, goDown, goRight, goLeft; 
	boolean shooting; 
	AnimationTimer timer; 
	private int numEnemies = 3;

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
		bg1 = new ImageView(new Image("SPACE.png"));
		bg2 = new ImageView(new Image("SPACE.png"));

		bg1.relocate(0, 0);
		bg2.relocate(800, 0);


		hero = new ImageView(new Image("ship.png"));
		hero.relocate(250, 250);


		scoreText = new Label("Scores: 0");
		scoreText.relocate(170, 10);
		scoreText.setTextFill(Color.WHITE);

		Button help = new Button("Help");
		help.relocate(500, 10);
		help.setOnAction(e -> helpScreen());
		help.setFocusTraversable(false);

		board.getChildren().addAll(bg1,bg2,hero,scoreText,help);

	}
	int enemyCounter = 0; 
	public void animate() {
		timer = new AnimationTimer() {

			Random random = new Random(); 

			@Override
			public void handle(long arg0) {

				int dx = 0;
				int dy = 0;

				if(goUp) dy-=4;
				if(goDown) dy+=4;
				if(goRight) dx +=4; 
				if(goLeft) dx-=4;

				if(random.nextInt(100) == 1) {
					createAsteroids();
				}

				if(random.nextInt(100) == 1) {
					createCoin();
				}


				if(enemyCounter < numEnemies) {
					createEnemy();
					enemyCounter++;
				}




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
		timer.start(); 
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

		if(enemyCounter >= 1) {
			moveEnemies1(px,py);
		}

		if(enemyCounter >= 2) {
			moveEnemies2(px,py);
		}

		if(enemyCounter >= 3) {
			moveEnemies3(px,py);
		}

		if(enemyCounter >= 4) {
			moveEnemies4(px,py);
		}

		if(enemyCounter >= 5) {
			moveEnemies5(px,py);
		}
	}

	public void moveEnemies1(int px, int py) {
		int dx = 0;
		int dy = 0; 

		if(enemyCounter >= 2) {
			if(px > enemies.get(0).getLayoutX()){
				dx = +2;
			}
			if(px < enemies.get(0).getLayoutX()){ 
				dx = -2;
			}	
			if(py < enemies.get(0).getLayoutY()){
				dy = -2;
			}
			if(py > enemies.get(0).getLayoutY()){
				dy = 2;
			}

			enemies.get(0).setLayoutX(enemies.get(0).getLayoutX() + dx);
			enemies.get(0).setLayoutY(enemies.get(0).getLayoutY() + dy);
		}


	}

	public void moveEnemies2(int px, int py) {
		int dx = 0;
		int dy = 0; 

		if(px > enemies.get(1).getLayoutX()){
			dx = +2;
		}
		if(px < enemies.get(1).getLayoutX()){ 
			dx = -2;
		}	
		if(py < enemies.get(1).getLayoutY()){
			dy = -2;
		}
		if(py > enemies.get(1).getLayoutY()){
			dy = 2;
		}

		enemies.get(1).setLayoutX(enemies.get(1).getLayoutX() + dx);
		enemies.get(1).setLayoutY(enemies.get(1).getLayoutY() + dy);

		if(enemies.get(1).getLayoutX() < 2*W/3) {
			enemies.get(1).setLayoutX(2*W/3);
		}

	}



	public void moveEnemies3(int px, int py) {
		int dx = 0;
		int dy = 0; 



		if(enemyCounter >= 3) {
			if(px > enemies.get(2).getLayoutX()){
				dx = +2;
			}
			if(px < enemies.get(2).getLayoutX()){ 
				dx = -2;
			}	
			if(py < enemies.get(2).getLayoutY()){
				dy = -2;
			}
			if(py > enemies.get(2).getLayoutY()){
				dy = 2;
			}

			enemies.get(2).setLayoutX(enemies.get(2).getLayoutX() + dx);
			enemies.get(2).setLayoutY(enemies.get(2).getLayoutY() + dy);

			if(enemies.get(2).getLayoutX() < 1*W/3) {
				enemies.get(2).setLayoutX(1*W/3);
			}

		}

	}

	int fodx = -2;
	int fody = 2; 

	public void moveEnemies4(int px, int py) {


		if(enemyCounter >= 4) {

			if (enemies.get(3).getLayoutX() < 1) {
				enemies.get(3).setLayoutX(1);
				fodx = 2;
			}

			if(enemies.get(3).getLayoutY() < 1) {
				enemies.get(3).setLayoutY(1);
				fody = 2;
			}

			if(enemies.get(3).getLayoutX() > W) {
				enemies.get(3).setLayoutX(W);
				fodx = -2;
			}

			if(enemies.get(3).getLayoutY() > H) {
				enemies.get(3).setLayoutY(H);
				fody = -2;
			}			

			enemies.get(3).setLayoutX(enemies.get(3).getLayoutX() + fodx);
			enemies.get(3).setLayoutY(enemies.get(3).getLayoutY() + fody);

		}


	}


	int fidx = -2; 
	int fidy = 2;
	public void moveEnemies5(int px, int py) {

		if(enemyCounter >= 5) {

			if (enemies.get(4).getLayoutX() < 1) {
				enemies.get(4).setLayoutX(1);
				fidx = 2;
			}

			if(enemies.get(4).getLayoutY() < 1) {
				enemies.get(4).setLayoutY(1);
				fidy = 2;
			}

			if(enemies.get(4).getLayoutX() > W) {
				enemies.get(4).setLayoutX(W);
				fidx = -2;
			}

			if(enemies.get(4).getLayoutY() > H) {
				enemies.get(4).setLayoutY(H);
				fidy = -2;
			}			

			enemies.get(4).setLayoutX(enemies.get(4).getLayoutX() + fidx);
			enemies.get(4).setLayoutY(enemies.get(4).getLayoutY() + fidy);

		}

	}



	public void createEnemyMissile() {
		ImageView aMissile = new ImageView(MISSILE_IMAGE);
		ImageView bMissile = new ImageView(MISSILE_IMAGE);
		ImageView cMissile = new ImageView(MISSILE_IMAGE);
		ImageView dMissile = new ImageView(MISSILE_IMAGE);
		ImageView eMissile = new ImageView(MISSILE_IMAGE);
		Node newMissile = aMissile;
		Node newMissile2 = bMissile; 
		Node newMissile3 = cMissile;
		Node newMissile4 = dMissile;
		Node newMissile5 = eMissile; 
		// There is only a maximum of 5 enemies. 
		// Each enemy will have separate missile array list.
		// add distinct missiles to avoid earlier error. 
		newMissile.relocate(enemies.get(0).getLayoutX() + enemies.get(0).getBoundsInLocal().getWidth(),enemies.get(0).getLayoutY());
		enemyMissiles.add(newMissile);
		board.getChildren().add(newMissile);


		if(enemyCounter >= 2) {
			newMissile2.relocate(enemies.get(1).getLayoutX() + enemies.get(1).getBoundsInLocal().getWidth(), enemies.get(1).getLayoutY());
			enemyMissiles2.add(newMissile2);
			board.getChildren().add(newMissile2);
		}

		if(enemyCounter >= 3) {
			newMissile3.relocate(enemies.get(2).getLayoutX() + enemies.get(2).getBoundsInLocal().getWidth(), enemies.get(2).getLayoutY());
			enemyMissiles3.add(newMissile3);
			board.getChildren().add(newMissile3);
		}

		if(enemyCounter >= 4) {
			newMissile4.relocate(enemies.get(3).getLayoutX() + enemies.get(3).getBoundsInLocal().getWidth(), enemies.get(3).getLayoutY());
			enemyMissiles4.add(newMissile4);
			board.getChildren().add(newMissile4);
		}

		if(enemyCounter >= 5) {
			newMissile4.relocate(enemies.get(4).getLayoutX() + enemies.get(4).getBoundsInLocal().getWidth(), enemies.get(4).getLayoutY());
			enemyMissiles5.add(newMissile5);
			board.getChildren().add(newMissile5);
		}

	}





	public void enemyFire() {
		for(int i = 0; i < enemyMissiles.size(); i++) {
			enemyMissiles.get(i).setLayoutX(enemyMissiles.get(i).getLayoutX() -10);
			if(enemyMissiles.get(i).getLayoutX() > W) {
				enemyMissiles.remove(i);
			}
		}

		for(int i = 0; i < enemyMissiles2.size(); i++) {
			enemyMissiles2.get(i).setLayoutX(enemyMissiles2.get(i).getLayoutX() -10);
			if(enemyMissiles2.get(i).getLayoutX() > W) {
				enemyMissiles2.remove(i);
			}
		}

		for(int i = 0; i < enemyMissiles3.size(); i++) {
			enemyMissiles3.get(i).setLayoutX(enemyMissiles3.get(i).getLayoutX() -10);
			if(enemyMissiles3.get(i).getLayoutX() > W) {
				enemyMissiles3.remove(i);
			}
		}

		for(int i = 0; i < enemyMissiles4.size(); i++) {
			enemyMissiles4.get(i).setLayoutX(enemyMissiles4.get(i).getLayoutX() -10);
			if(enemyMissiles4.get(i).getLayoutX() > W) {
				enemyMissiles4.remove(i);
			}
		}

		for(int i = 0; i < enemyMissiles5.size(); i++) {
			enemyMissiles5.get(i).setLayoutX(enemyMissiles5.get(i).getLayoutX() -10);
			if(enemyMissiles5.get(i).getLayoutX() > W) {
				enemyMissiles5.remove(i);
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
			if(FP <= 5) {
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
		ImageView aMissile = new ImageView("missile.png");
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


		for(int i = 0; i < bMissiles.size(); i++) {
			for(int j = 0; j < asteroids.size(); j++) {
				if(bMissiles.get(i).getBoundsInParent().intersects(asteroids.get(j).getBoundsInParent())) {
					board.getChildren().removeAll(bMissiles.get(i), asteroids.get(j));
					asteroids.remove(j);
					bMissiles.remove(i);
					score++; 
					scoreText.setText("Score: " + score);
				}
			}
		}

		for(int i = 0; i < bMissiles.size(); i++) {
			for(int j = 0; j < enemies.size(); j++) {
				if(bMissiles.get(i).getBoundsInParent().intersects(enemies.get(j).getBoundsInParent())) {
					board.getChildren().removeAll(enemies.get(j), bMissiles.get(i));
					bMissiles.remove(i);
					enemies.remove(j);
					score++; 
					enemyCounter--;
					scoreText.setText("Score: " + score);
					break; 
				}
			}
		}

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


		for(int j = 0; j < enemyMissiles.size(); j++) {
			if(enemyMissiles.get(j).getBoundsInParent().intersects(hero.getBoundsInParent())) {
				gameOver();
			}
		}


		for(int j = 0; j < enemyMissiles2.size(); j++) {
			if(hero.getBoundsInParent().intersects(enemyMissiles2.get(j).getBoundsInParent())) {
				gameOver();
			}
		}

		for(int j = 0; j < enemyMissiles3.size(); j++) {
			if(hero.getBoundsInParent().intersects(enemyMissiles3.get(j).getBoundsInParent())) {
				gameOver();
			}
		}

		if(numEnemies >= 4) {
			for(int j = 0; j < enemyMissiles4.size(); j++) {
				if(hero.getBoundsInParent().intersects(enemyMissiles4.get(j).getBoundsInParent())) {
					gameOver();
				}
			}
		}
		if(numEnemies >= 5) {
			for(int j = 0; j < enemyMissiles5.size(); j++) {
				if(hero.getBoundsInParent().intersects(enemyMissiles5.get(j).getBoundsInParent())) {
					gameOver();
				}
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

		for(int i = 0; i < missiles.size(); i++) {
			for(int j = 0; j < enemies.size(); j++) {
				if(missiles.get(i).getBoundsInParent().intersects(enemies.get(j).getBoundsInParent())) {
					board.getChildren().removeAll(enemies.get(j), missiles.get(i));
					missiles.remove(i);
					enemies.remove(j);
					score++; 
					enemyCounter--;
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

	public int getNumEnemies() {
		return numEnemies;
	}

}
