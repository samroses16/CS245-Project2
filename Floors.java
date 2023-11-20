
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Floors {

	private int floorNum;//current floor 5
	
	//making a queue for people going up and down 
	public Queue<Passenger> waitingPeopleUp;
	public Queue<Passenger> waitingPeopleDown;
	
	
	//initializing the floor
	public Floors(int floorNum){
		this.floorNum = floorNum;
		if (Main.structures == "linked") {//if structure is linked then put them in a Linkedlist 
			waitingPeopleUp = new LinkedList<>();
			waitingPeopleDown = new LinkedList<>();
		}else {//if its an array then put them in an arraydeque
			waitingPeopleUp = new ArrayDeque<>();
			waitingPeopleDown = new ArrayDeque<>();
		}
	}
	//creating a person 
	public void createPerson() {
		Random rand = new Random();//randomly getting a value 
		if (rand.nextDouble() <= Main.passenger) {//checking if the value is less then the given value
			Passenger person = new Passenger(floorNum);//then create a person
			addPassenger(person);//we add the passenger to the queue
		}
	}
	
	//add passenger method 
	public void addPassenger(Passenger passenger) {
		if (passenger.destination > floorNum) {//if the passengers destination is greater than the current floor number 
			waitingPeopleUp.add(passenger);//then they are added to the people going up queue
		}else {
			waitingPeopleDown.add(passenger);//if its less then, add to the going down queue
		}
	}
	
	
	public int getFloorNum(){
		return floorNum;
	}
	
	
}
