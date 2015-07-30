import java.io.Serializable;


public class Time implements Serializable {

	private String time;
	
	public static void main(String[] args){
		//Time t = new Time("12:05 AM");
		System.out.println(calculateDuration(new Time("11:00 AM"), new Time("1:00 PM")));
	}
	
	/**
	 * Times are given to the constructor in the format of- XX:XX AM/PM, where X is an integer digit.
	 * Valid times for this class may not have hours that go above 12.
	 * @param time
	 */
	public Time(String time){
		if(time.matches("[1]?[0-9]:[0-5][0-9] [aA|pP][mM]")){
			this.time = time;
		}else{
			throw new IllegalArgumentException("The time does not match the pattern: XX:XX AM/PM !");
		}
	}
	
	public int getHour(){
		int hourToReturn =  Integer.parseInt(time.substring(0, time.indexOf(":")));
		if(hourToReturn == 12 && this.isAM()){
			return 0;
		}
		return hourToReturn;
	}
	
	public int getMinutes(){
		String shortTime = time.substring(0, time.indexOf(" "));
		return Integer.parseInt(shortTime.substring(shortTime.indexOf(":") + 1));
	}
	
	public boolean isAM(){
		if(time.toLowerCase().charAt(time.length() - 2) == 'a'){
			return true;
		}else if(time.toLowerCase().charAt(time.length() - 2) == 'p'){
			return false;
		}else{
			throw new IllegalArgumentException("The format for time should be: XX:XX AM/PM!");
		}
	}
	
	public String toString(){
		return time;
	}
	
	/**
	 * Calculates the amount of time, in minutes, that has passed since the start to the end
	 * @param startTime The starting time
	 * @param endTime The ending time
	 * @return How long, in minutes, since the start to the ending time
	 */
	public static int calculateDuration(Time startTime, Time endTime){
		int startHour = startTime.getHour();
		if(!startTime.isAM() && startHour != 12){
			startHour += 12;
		}
		int endHour = endTime.getHour();
		if(!endTime.isAM() && endHour != 12){
			endHour += 12;
		}
		if(startHour > endHour){
			throw new IllegalArgumentException("The start time must be before the end time!");
		}
		return ((endHour - startHour)) * 60 + (endTime.getMinutes() - startTime.getMinutes());
	}
	
	public static int calculateDuration(String startTime, String endTime) {
		Time t1 = new Time(startTime);
		Time t2 = new Time(endTime);
		return calculateDuration(t1, t2);
	}
	
	public static boolean isValidTimeFrame(Time t1, Time t2){
		try{
			calculateDuration(t1, t2);
			return true;
		}catch(IllegalArgumentException e){
			return false;
		}
	}
}
