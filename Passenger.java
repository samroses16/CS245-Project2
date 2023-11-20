import java.util.Random;


public class Passenger implements Comparable<Passenger> {

	//private int currentFloor;
	public int destination;
	public int startTime;
	
	
	//initialize properties
	Passenger(int currentFloor) {
		Random rand = new Random();
		destination = rand.nextInt(0,Main.floors);
		startTime = Main.tick;
		
		while (currentFloor == destination) {
			destination = rand.nextInt(0,Main.floors);
		}
		
	}
	
	public int compareTo(Passenger person) {
		return Integer.compare(this.destination, person.destination);
	}
}
