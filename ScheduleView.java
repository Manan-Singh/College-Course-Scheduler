import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class ScheduleView extends StackPane implements Serializable {

	private Canvas canvas;
	private GraphicsContext gc;
	private HashMap<String, Integer> timeCoordinates;
	private HashMap<String, Integer> dayCoordinates;
	private StackPane forground;
	private Pane upperLayer;
	private ArrayList<Group> coursesAdded;
	public static ArrayList<Course> courses;
	
	
	public ScheduleView() {
		dayCoordinates = new HashMap<String, Integer>();
		//courses = new ArrayList<Course>();
		coursesAdded = new ArrayList<Group>();
		timeCoordinates = new HashMap<String, Integer>();
		canvas = new Canvas(1000, 1000);
		gc = canvas.getGraphicsContext2D();
		initializeCanvas();
		forground = new StackPane();
		upperLayer = new Pane();
		forground.getChildren().add(upperLayer);		
		this.getChildren().addAll(canvas, forground);
		File f = new File("courses.obj");
		if(f.exists()){
			try {
				FileInputStream fin = new FileInputStream(f);
				ObjectInputStream oin = new ObjectInputStream(fin);
				courses = (ArrayList<Course>) oin.readObject();
				for(int i = 0; i < courses.size(); i++){
					addCourse(courses.get(i));
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e){
				e.printStackTrace();
			}
		}else{
			courses = new ArrayList<Course>();
		}
	}
	
	private void initializeCanvas() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setStroke(Color.SALMON);
		gc.strokeText("Mon", 140, 20);
		gc.strokeText("Tue", 340, 20);
		gc.strokeText("Wed", 540, 20);
		gc.strokeText("Thu", 740, 20);
		gc.strokeText("Fri", 940, 20);
		dayCoordinates.put("mon", 140);
		dayCoordinates.put("tue", 340);
		dayCoordinates.put("wed", 540);
		dayCoordinates.put("thu", 740);
		dayCoordinates.put("fri", 940);
		gc.setStroke(Color.web("#3366FF"));
		gc.setLineWidth(0.75);
		int currentNum = 6;
		int y = 60;
		for(int i = 1; i < 24; i++){
			if(currentNum > 23){
				timeCoordinates.put("12:00 AM", y);
				gc.strokeText(("12:00 AM"), 
						10, y);
				currentNum = 1;
				y += 40;
			}
			String time = ((currentNum % 12 == 0) ? 12 : currentNum % 12) + ":00 " + ((currentNum < 12) ? "AM" : "PM");
			timeCoordinates.put(time, y);
			gc.strokeText(time, 
					(((currentNum % 12) >= 10 || currentNum % 12 == 0) ? 10 : 18), y);
			y += 40;
			currentNum++;
		}
		gc.setStroke(Color.ORANGE);
		for(int i = 55; i < 980; i += 40){
			gc.strokeLine(80, i, 1000, i);
		}
	}
	
	public void addCourse(Course c) {
		Group g = new Group();
		for(int i = 0; i < c.getDays().size(); i++){
			Button b = c.generateCourseImage();
			int hour = c.getStartTime().getHour();
			if(hour == 0){
				hour = 12;
			}
			b.setLayoutX(getDayPosition(c.getDays().get(i)));
			b.setLayoutY(timeCoordinates.get(hour + ":00 " + (c.getStartTime().isAM() ? "AM" : "PM")) - 5 + (Math.floor((2.0 / 3.0) * c.getStartTime().getMinutes())));
			//upperLayer.getChildren().add(b);
			g.getChildren().add(b);
		}
		upperLayer.getChildren().add(g);
		coursesAdded.add(g);
	}
	
	public int getDayPosition(String day) {
		return dayCoordinates.get(day.toLowerCase()) - 40;
	}
	
	public void remove(String name){
		for(int i = 0; i < coursesAdded.size(); i++){
			Button b = (Button)coursesAdded.get(i).getChildren().get(0);
			if(b.getText().equals(name)){
				System.out.println("Course: " + b.getText());
				upperLayer.getChildren().remove(coursesAdded.get(i));
				coursesAdded.remove(i);
				courses.remove(i);
			}
		}
	}
}
