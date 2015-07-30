import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class DisplayDriver extends Application implements Serializable {
	
	private ScheduleView sv;
	

	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("College Course Schedule View");
		VBox leftContainer = new VBox();
		leftContainer.setPadding(new Insets(10, 10, 10, 10));
		leftContainer.setSpacing(15);
		Button addNewCourse = new Button("Add New Course");
		sv = new ScheduleView();
		addNewCourse.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				new AddCoursePane(sv);
				
			}});
		Button removeCourse = new Button("Remove Course");
		removeCourse.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				if(!ScheduleView.courses.isEmpty()){					
					new RemoveCoursePane(sv);
					//sv.remove("CSE 219");
				}else{
					new ErrorStage("You have no courses to remove!");
				}
			}});
		Button save = new Button("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				File f = new File("courses.obj");
				try {
					FileOutputStream fout = new FileOutputStream(f);
					ObjectOutputStream oout = new ObjectOutputStream(fout);
					oout.writeObject(ScheduleView.courses);
					fout.close();
					oout.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}});
		save.setMaxWidth(Double.MAX_VALUE);
		addNewCourse.setMaxWidth(Double.MAX_VALUE);
		removeCourse.setMaxWidth(Double.MAX_VALUE);
		leftContainer.getChildren().addAll(addNewCourse, removeCourse, save);
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		ScrollPane sr = new ScrollPane();
		sr.setPrefSize(800, 800);
		sr.setContent(sv);
		root.setCenter(sr);
		root.setLeft(leftContainer);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	

}
