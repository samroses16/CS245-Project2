
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
	
	static String structures = "linked";
	static int floors = 32;//32
	static double passenger = 0.03;//0.03 
	static int elevators = 1;
	static int elevator_capacity = 10;
	static int duration = 500;
	
	static int tick = 0;
	
	Main (String properties) throws IOException{
		FileReader reader = new FileReader(properties); // create a reader object on the properties file 
		Properties p = new Properties(); // create properties object 
		p.load(reader);  // Add a wrapper around reader object

		// access properties data 
		structures = p.getProperty("structures"); 
		floors = Integer.parseInt(p.getProperty("floors"));
		passenger =  Double.parseDouble(p.getProperty("passengers"));
		elevator_capacity = Integer.parseInt(p.getProperty("elevatorCapacity"));
		elevators = Integer.parseInt(p.getProperty("elevators"));
		duration = Integer.parseInt(p.getProperty("duration"));
	}
	
	public static void results() {
		List<Integer> times = Elevator.ArrayOfTimes;
		//calculate the results 
		int min=0, max =0 , sum = 0;
		if(!times.isEmpty()) {
			min = times.get(0);
			max = times.get(0);
			for (int i = 0;i<times.size();i++) {
				sum += times.get(i);
				min = Math.min(min, times.get(i));
				max = Math.max(max, times.get(i));
			}
			System.out.println("Min = "+min);
			System.out.println("Max = "+max);
			System.out.println("Average = " + ((double)sum/times.size()));
			return;
		}
		System.out.println("There were no passengers travelling in the simulation");
		
	}
	
	
	
	public static void main(String[] args) throws IOException {
	
		if (args.length>0) {
			new Main(args[0]);
		}
		//create the elevators
		List<Elevator> Elevators;
		if (Main.structures.equals("linked")) {//initializing the elevators data structure
			Elevators = new LinkedList<>();
		}else {
			Elevators = new ArrayList<>();
		}
		
		
		for(int i = 0; i<Main.elevators;i++) {// making elevators
			Elevators.add(new Elevator());
		}
		//
		for (int k = 0; k<Main.floors;k++) {
			Elevator.Floors.get(k).createPerson();
		}
		
		for (int i = 0;i<Main.duration;i++) {
			Main.tick=i;// setting the current tick to the main class so that other classes can use 
			for (int j = 0; j< Elevators.size();j++) {
				Elevators.get(j).unloadPassenger();
				Elevators.get(j).loadPassenger();
				Elevators.get(j).goingUpOrDown();
				Elevators.get(j).changeFloor();
			}
			// generating passengers for the tick
			for (int k = 0; k<Main.floors;k++) {
				Elevator.Floors.get(k).createPerson();
			}
			
		}
		results();
	}

}

