import java.io.*;
import java.util.*;

/**
 * Created by Jiuding on 2017/3/21.
 */
public class VanRentalSystem {

    private static ArrayList<Booking> bookings;
    private static ArrayList<Vehicle> vehicles;

    public static void main(String args[]){
        bookings = new ArrayList<>();
        vehicles = new ArrayList<>();
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found!");
        } while (sc.hasNextLine()){
            String line = sc.nextLine().split("#", 2)[0];
            String[] cmd = line.trim().split("(\\s)+");
            if (cmd.length==0) continue;
            if (cmd[0].equals("Location")){
                addVehicle(cmd[1], cmd[2], cmd[3]);
            }
        }

        if (sc!=null) sc.close();

        printVehicle();


    }

    private static void addVehicle(String location, String name, String type){
        Vehicle vehicle = new Vehicle(name, type, location);
        vehicles.add(vehicle);
    }

    private static void printVehicle(){
        for (Vehicle v : vehicles){
            System.out.println(" " + v.getName() + " " + v.getType() + " " + v.getLocation());
        }
    }

}
