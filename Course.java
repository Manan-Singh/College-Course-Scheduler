import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.control.Button;

public class Course implements Serializable {

	private Time startTime;
	private int duration;
	private String courseName;
	private String color;
	private ArrayList<String> days;
	
	public Course(String courseName, Time startTime){
		this.courseName = courseName;
		this.setStartTime(startTime);
	}
	
	public Course(String courseName, Time startTime, int duration){
		this(courseName, startTime);
		this.duration = duration;
	}
	
	public Course(String courseName, Time startTime, int duration, String color){
		this(courseName, startTime, duration);
		this.color = color;
	}
	
	public Course(String courseName, Time startTime, int duration, String color, ArrayList<String> days){
		this(courseName, startTime, duration, color);
		this.setDays(days);
	}
	
	/**
	 * Creates a button graphic that will be displayed on the schedule
	 * @return A stylized button which will show information regarding the course
	 */
	public Button generateCourseImage(){
		Button thisCourse = new Button(courseName);
		thisCourse.setStyle("-fx-text-fill: white; -fx-background-color: " + color);
		thisCourse.setPrefWidth(100);
		thisCourse.setPrefHeight((2.0/3.0) * duration);
		return thisCourse;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public ArrayList<String> getDays() {
		return days;
	}

	public void setDays(ArrayList<String> days) {
		this.days = days;
	}
	
	public String toString(){
		return this.courseName;
	}
}
