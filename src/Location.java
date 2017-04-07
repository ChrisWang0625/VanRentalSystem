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

    public int[] book(String requestType, int id, Calendar startDate, Calendar endDate, int autoRequested,
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

        if (count == 0) return returnArray;
        else {
            System.out.print(" " + this.name);
            for (Vehicle candidate: candidates) {
                //System.out.println(candidate.getName() + " " + candidate.getType());
                candidate.insertRentalRecord(id, candidate, startDate, endDate);

            }
        }
        System.out.print(";");
        return returnArray;
    }
}
