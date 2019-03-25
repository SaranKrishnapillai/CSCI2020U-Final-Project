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

public class Enemy {
	
	private String ENEMY_IMAGE = "Tie fighter.png";
	private Image image = new Image(ENEMY_IMAGE);
	public ImageView enemy = new ImageView(image);
	private ArrayList<Node> missiles = new ArrayList<Node>();
	private int x,y,dx,dy;
	private int height,width;
	private int resetX,resetY;
	
	public Enemy() {
		
		dx = -5;
		dy = 0;
		
	}
	
	public void move() {
		enemy.setLayoutX(enemy.getLayoutX() + dx); 
		enemy.setLayoutY(enemy.getLayoutY() + dy);
		
		if(enemy.getLayoutX() < 1) {
			enemy.setLayoutY(1);
			this.dy = 1;
		}
		
		if(enemy.getLayoutY() < 1) {
			enemy.setLayoutY(1);
			this.dy = 1; 
		}
		
		if(enemy.getLayoutX() > Game_Content.W + 1000) {
			enemy.setLayoutX(Game_Content.W);
			this.dx = -1; 
		}
		
		if(enemy.getLayoutY() > Game_Content.H) {
			enemy.setLayoutY(Game_Content.H); 
			this.dy = -1; 
		}
		
	}
	
	public void move(int px, int py) {
		// Updates the character's location based on its dx and dy values.
		if(px > enemy.getLayoutX()){
			this.dx = +2;
	}
		if(px < enemy.getLayoutX()){ 
			this.dx = -2;
		}	
		if(py < enemy.getLayoutY()){
			this.dy = -2;
		}
		if(py > enemy.getLayoutY()){
			this.dy = 2;
		}
		
		enemy.setLayoutX(enemy.getLayoutX() + this.dx);
		enemy.setLayoutY(enemy.getLayoutY() + this.dy);

	}
	
	public void createMissile() {
		ImageView aMissile = new ImageView("missile.png");
		Node newMissile = aMissile;

		newMissile.relocate(enemy.getLayoutX() + enemy.getBoundsInLocal().getWidth(),enemy.getLayoutY());
		missiles.add(newMissile);
		Game_Content.board.getChildren().add(newMissile);
	}
	
	public void fireMissiles() {
		for(int i = 0; i < missiles.size(); i++) {
			missiles.get(i).setLayoutX(missiles.get(i).getLayoutX() -10);
			if(missiles.get(i).getLayoutX() > Game_Content.W) {
				missiles.remove(i);
			}
		}
	}
	
}
