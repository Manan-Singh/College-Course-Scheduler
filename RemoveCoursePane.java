import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class RemoveCoursePane extends Stage {

	public RemoveCoursePane(ScheduleView sv){
		this.setTitle("Remove a course!");
		BorderPane root = new BorderPane();
		ListView<Course> lv = new ListView<Course>();
		lv.setItems(FXCollections.observableArrayList(ScheduleView.courses));
		root.setLeft(lv);
		Text directions = new Text("Choose a course \nto remove from the list!");
		VBox body = new VBox();
		body.setPadding(new Insets(10, 10, 10, 10));
		body.setSpacing(15);
		Button remove = new Button("Remove");
		remove.setMaxWidth(Double.MAX_VALUE);
		remove.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				String toRemove = ScheduleView.courses.get(lv.getSelectionModel().getSelectedIndex()).toString();
				sv.remove(toRemove);
			}});
		root.setCenter(body);
		body.getChildren().add(directions);
		body.getChildren().add(remove);
		Scene scene = new Scene(root, 500, 200);
		this.setScene(scene);
		this.show();
	}
}
