import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AddCoursePane extends Stage {
	
	private TextField courseNameField;
	private TextField startTimeField, endTimeField;
	private CheckBox[] checkBoxes;
	private String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	
	
	public AddCoursePane(ScheduleView sv){
		this.setResizable(false);
		this.setTitle("Add a course!");
		checkBoxes = new CheckBox[5];
		for(int i = 0; i < checkBoxes.length; i++){
			checkBoxes[i] = new CheckBox();
		}
		BorderPane root = new BorderPane();
		VBox content = new VBox();
		content.setPadding(new Insets(10, 10, 10, 10));
		content.setSpacing(40);
		root.setCenter(content);
		//First row
		HBox nameContainer = new HBox();
		nameContainer.setAlignment(Pos.CENTER);
		courseNameField = new TextField();
		Text nameLabel = new Text("Course name: ");
		nameContainer.getChildren().addAll(nameLabel, courseNameField);
		content.getChildren().add(nameContainer);
		//Second row
		HBox timeContainer = new HBox();
		timeContainer.setAlignment(Pos.CENTER);
		timeContainer.setSpacing(10);
		Text startText = new Text("Start time: ");
		startTimeField = new TextField();
		startTimeField.setPrefColumnCount(5);
		timeContainer.getChildren().addAll(startText, startTimeField);
		Text endText = new Text("End time: ");
		endTimeField = new TextField();
		endTimeField.setPrefColumnCount(5);
		timeContainer.getChildren().addAll(endText, endTimeField);
		content.getChildren().add(timeContainer);
		//Third row
		BorderPane dayContainer = new BorderPane();
		VBox daysChecks = new VBox();
		Text selectDays = new Text("Select days: ");
		daysChecks.getChildren().add(selectDays);
		for(int i = 0; i < checkBoxes.length; i++){
			HBox h = new HBox();
			h.setSpacing(5);
			h.setPadding(new Insets(5, 5, 5, 5));
			h.getChildren().add(checkBoxes[i]);
			h.getChildren().add(new Text(daysOfWeek[i]));
			daysChecks.getChildren().add(h);
		}
		VBox colorBox = new VBox();
		Text selectColor = new Text("Select a color: ");
		ColorPicker cp = new ColorPicker();
		colorBox.getChildren().addAll(selectColor, cp);
		dayContainer.setRight(colorBox);
		dayContainer.setLeft(daysChecks);
		content.getChildren().add(dayContainer);
		Button ok = new Button("OK");
		HBox okField = new HBox();
		okField.setAlignment(Pos.CENTER);
		okField.getChildren().add(ok);
		content.getChildren().add(okField);
		ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				ArrayList<String> checkedDays = new ArrayList<String>();
				for(int i = 0; i < checkBoxes.length; i++){
					if(checkBoxes[i].isSelected()){
						checkedDays.add(daysOfWeek[i].substring(0, 3).toLowerCase());
					}
				}
				String realColor = cp.getValue().toString().substring(cp.getValue().toString().indexOf("x") + 1);
				String courseName = courseNameField.getText();
				Time t1 = null;
				Time t2 = null;
				if(!courseName.equals("")){
					try{
						t1 = new Time(startTimeField.getText());
						t2 = new Time(endTimeField.getText());
						if(Time.isValidTimeFrame(t1, t2)){
							if(!checkedDays.isEmpty()){
								Course c = new Course(courseNameField.getText(), new Time(startTimeField.getText()), 
										Time.calculateDuration(startTimeField.getText(), endTimeField.getText()),
										"#" + realColor.substring(0, realColor.length() - 2), checkedDays);
								sv.addCourse(c);
								ScheduleView.courses.add(c);
								//for(Course x : ScheduleView.courses){System.out.println(x);}
							}else{
								new ErrorStage("You need to check which days the course is held!");
							}
						}else{
							new ErrorStage("That is not a valid time frame!");
							
						}
					}catch(IllegalArgumentException e2){
						new ErrorStage("Please input the time in the format: XX:XX AM/PM !");
					}
				}else{
					new ErrorStage("Please select a name for your course!");
				}
				
//				Course c = new Course(courseNameField.getText(), new Time(startTimeField.getText()), 
//						Time.calculateDuration(startTimeField.getText(), endTimeField.getText()),
//						"#" + realColor.substring(0, realColor.length() - 2), checkedDays);
//				sv.addCourse(c);
			}});
		Scene scene = new Scene(root, 500, 400);
		this.setScene(scene);
		this.show();
	}

}
