import java.util.*;
/**
 * Created by Jiuding on 2017/4/1.
 */
public class Location {
    private String name;
    private ArrayList<Vehicle> vehicles;

    public Location(String name){
        this.name = name;
        this.vehicles = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public boolean book(String requestType, int id, Calendar startDate, Calendar endDate, int autoRequested,
                                int manualRequested){
        ArrayList<Vehicle> candidates = new ArrayList<>();
        int i = autoRequested;
        int j = manualRequested;
        for (Vehicle vehicle: vehicles){
            if (i == 0 && j == 0) break;
            if (vehicle.getType().equals("Automatic")){
                if (i == 0) continue;
                if (vehicle.isAvailable(startDate, endDate)){
                    candidates.add(vehicle);
                    i--;
                }
            } else if (vehicle.getType().equals("Manual")){
                if (j == 0) continue;
                if (vehicle.isAvailable(startDate, endDate)){
                    candidates.add(vehicle);
                    j--;
                }
            }
        }

        if (i != 0 || j != 0) return false;

        if (requestType.equals("Request")) {
            System.out.print("Booking " + id + " " + this.name);
        } else if (requestType.equals("Change")) {
            System.out.print("Change " + id + " " + this.name);
        }
        for (Vehicle candidate: candidates) {
            candidate.insertRentalRecord(id, candidate, startDate, endDate);
        }
        System.out.print("; ");
        System.out.println();
        return true;
    }
}
