import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;



public class Elevator {
	/*change back*/public static List<Floors> Floors;// static because all the elevators are going to use the same floors
	static boolean createFloors = true;
	private int currentFloor;
	private int maxPeople; // is the elevator capacity

	PriorityQueue<Passenger> heapUp = new PriorityQueue<>(); // min heap for the elevator when it is going up
	PriorityQueue<Passenger> heapDown = new PriorityQueue<>(Collections.reverseOrder()); // max heap for the elevator when it is going down
	
	static ArrayList<Integer> ArrayOfTimes; //doesn't fall in the category of the the properties because it is not
	private boolean goingUp;

	public Elevator() {
		if(createFloors) {// only creating floors for the first iteration and then done. Try making this work whenever you do!!!!!!
			if (Main.structures.equals("linked")) {
				Floors = new LinkedList<>();
			}else {
				Floors = new ArrayList<>();
			}
			for(int i = 0;i<Main.floors;i++) {
				Floors.add(new Floors(i));
			}
			createFloors = false;
			ArrayOfTimes=new ArrayList<>();
		}
		currentFloor = 0;
		goingUp = true;
		
	}
	
	//load passenger to elevator
	public void loadPassenger() {
		if(goingUp) {
			while (!Floors.get(currentFloor).waitingPeopleUp.isEmpty() && maxPeople<Main.elevator_capacity){ // while there is space in the elevator and there are passengers going up, then add them to the elevator
				heapUp.add(Floors.get(currentFloor).waitingPeopleUp.poll());//add passenger to the elevator
				maxPeople++;// add the passenger to the capacity
			}
		}else {
			while (!Floors.get(currentFloor).waitingPeopleDown.isEmpty() && maxPeople<Main.elevator_capacity){ // while there is space in the elevator and there are passengers going up, then add them to the elevator
				heapDown.add(Floors.get(currentFloor).waitingPeopleDown.poll()); //add passenger to the elevator
				maxPeople++; //add the passenger to the capacity
			}
		}	
	}
	
	//unload passenger off elevator
	public void unloadPassenger() {
		if (goingUp) {
			while (!heapUp.isEmpty() && heapUp.peek().destination == currentFloor) {// while there are passengers that are going down at that floor,keep getting them down
				ArrayOfTimes.add(Main.tick - heapUp.poll().startTime);//  adds the time taken to the list of all times
				maxPeople--;
			}
		}
		else {
			while (!heapDown.isEmpty() && heapDown.peek().destination == currentFloor) {
				ArrayOfTimes.add(Main.tick - heapDown.poll().startTime);
				maxPeople--;
			}
		}
	}
	

	public int getCurrentFloor() {
		return currentFloor;
	}
	 
	public void changeFloor() {
		if (goingUp) {
			int floorCandidate = Main.floors-1;
			
			//check in the elevator
			if (!heapUp.isEmpty()) {// takes the passengers in the elevator nextfloor
				floorCandidate=heapUp.peek().destination;
			}
			for (int i = currentFloor+1;i<Main.floors;i++) {
				if (!Floors.get(i).waitingPeopleUp.isEmpty()){
					floorCandidate=Math.min(floorCandidate, Floors.get(i).getFloorNum());
				}
			}
			if(floorCandidate-currentFloor>5) {// if the next floor is greater than 5 then you either increase by 5 or just set it to the last floor if that would be greater than the maximum floors
				if (currentFloor+5<Main.floors) {
					currentFloor +=5;
				}else {
					currentFloor=Main.floors-1;
				}
			}else {// if the next floor is not more than 5 floors up
				currentFloor = floorCandidate;
			}
			
		}else {// use the code above to make a similar implementation for when the elevator is going down. 
			int floorCandidate = 0;
			
			if (!heapDown.isEmpty()) {// takes the passengers in the elevator nextfloor
				floorCandidate=heapDown.peek().destination;
			}
			for (int i = currentFloor-1;i>=0;i--) {
				if (!Floors.get(i).waitingPeopleDown.isEmpty()){
					floorCandidate=Math.min(floorCandidate, Floors.get(i).getFloorNum());
				}
			}
			if(floorCandidate-currentFloor>5) {// if the next floor is greater than 5 then you either increase by 5 or just set it to the last floor if that would be greater than the maximum floors
				if (currentFloor-5>=0) {
					currentFloor -=5;
				}else {
					currentFloor=0;
				}
			}else {// if the next floor is not more than 5 floors up
				currentFloor = floorCandidate;
			}
		}
		
	}
	public void goingUpOrDown(){
		//changing the direction of the elevator
		if (goingUp) {
			if (heapUp.isEmpty() && currentFloor==Main.floors-1) {
				goingUp=false;
			}
		}else {
			if (heapDown.isEmpty() && currentFloor==0){
				goingUp = true;
			}
		}
		
	}
}
