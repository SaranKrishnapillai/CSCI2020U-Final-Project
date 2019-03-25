import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Game extends Application{
	
	public void start(Stage stage) throws Exception{
		
		Game_Content g = new Game_Content();
		
		Scene scene = new Scene(g.getRoot(),800,600);
		scene.setOnKeyPressed(e -> g.keyPressed(e));
		scene.setOnKeyReleased(e -> g.keyReleased(e));
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String args[]) {
		launch(args);
	}
	
}
