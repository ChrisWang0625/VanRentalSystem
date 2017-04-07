import java.util.*;
/**
 * Created by Jiuding on 2017/4/1.
 */
public class Location {
    private String name;
    private ArrayList<Vehicle> vehicles;

    /**
     * constructor
     * @param name of the location
     */
    public Location(String name){
        this.name = name;
        this.vehicles = new ArrayList<>();
    }

    /**
     * @return name of the location
     */
    public String getName() {
        return name;
    }

    /**
     * @return list of vehicles depot on the location
     */
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * add vehicle to the location
     * @param vehicle vehicle to add to the location
     */
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    /**
     * Function to browse through the vehicles in the depot and book those are available and fit the requirement. And
     * return the demanded
     * @param id
     * @param startDate
     * @param endDate
     * @param autoRequested
     * @param manualRequested
     * @return
     */
    public int[] book(int id, Calendar startDate, Calendar endDate, int autoRequested,
                                int manualRequested){
        ArrayList<Vehicle> candidates = new ArrayList<>();

        int i = autoRequested;
        int j = manualRequested;
        int count = 0;
        for (Vehicle vehicle: vehicles){
            if (i == 0 && j == 0) break;
            if (vehicle.getType().equals("Automatic")){
                if (i == 0) continue;
                if (vehicle.isAvailable(startDate, endDate)){
                    candidates.add(vehicle);
                    count++;
                    i--;
                }
            } else if (vehicle.getType().equals("Manual")){
                if (j == 0) continue;
                if (vehicle.isAvailable(startDate, endDate)){
                    candidates.add(vehicle);
                    count++;
                    j--;
                }
            }
        }

        int[] returnArray = {i, j};
        if (count != 0) System.out.print(" " + this.name + " ");
        StringBuilder sb = new StringBuilder();
        Iterator<Vehicle> candidateIterator = candidates.iterator();

        while(candidateIterator.hasNext()) {
            Vehicle candidate = candidateIterator.next();
            candidate.insertRentalRecord(id, candidate, startDate, endDate);
            if (candidateIterator.hasNext()) {
                System.out.print(", ");
            }
        }
        return returnArray;
    }
}
