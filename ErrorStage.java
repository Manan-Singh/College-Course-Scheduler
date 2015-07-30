import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ErrorStage extends Stage {

	public ErrorStage(String message){
		this.setTitle("Error!");
		StackPane root = new StackPane();
		root.setPadding(new Insets(10, 10, 10, 10));
		Text userMessage = new Text(message);
		root.getChildren().add(userMessage);
		Scene scene = new Scene(root);
		this.setScene(scene);
		this.show();
	}
}
