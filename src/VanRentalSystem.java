import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jiuding on 2017/3/21.
 */
public class VanRentalSystem {

    private static ArrayList<Location> locations = new ArrayList<>();
    private static ArrayList<Vehicle> vehicles = new ArrayList<>();
    private static final int CURRENT_YEAR = 2017;

    public static void main(String args[]) throws ParseException {
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found!");
        } while (sc.hasNextLine()){
            String line = sc.nextLine().split("#", 2)[0];
            String[] cmd = line.trim().split("(\\s)+");
            if (cmd.length==0) continue;
            if (cmd[0].equals("Location")) {
                depotVehicle(cmd[1], cmd[2], cmd[3]);
            } else if (cmd[0].equals("Request") || cmd[0].equals("Change")) {
                int id = Integer.parseInt(cmd[1]);
                Calendar startDate = convertStringToCalendar(cmd[2], cmd[3], cmd[4]);
                Calendar endDate = convertStringToCalendar(cmd[5], cmd[6], cmd[7]);
                int numAuto, numManual;
                numAuto = numManual = 0;
                for (int i = 9; i < cmd.length; i += 2) {
                    if (cmd[i].equals("Automatic")) numAuto = Integer.parseInt(cmd[i-1]);
                    else if (cmd[i].equals("Manual")) numManual = Integer.parseInt(cmd[i-1]);
                }
                if (cmd[0].equals("Request")) {
                    System.out.println(cmd[1]);
                    if (!checkRequest("Request", id, startDate, endDate, numAuto, numManual, vehicles)) {
                        System.out.println("Booking rejected");
                    } else {
                        System.out.print("Booking " + id);
                        processRequest("Request", id, startDate, endDate, numAuto, numManual);
                        if(sc.hasNextLine()) {
                            System.out.println();
                        }
                    }
                } else if (cmd[0].equals("Change")) {
                    ArrayList<Booking> bookingArrayList = getBookingList(id);
                    if (bookingArrayList.size()==0) System.out.println("Change rejected");
                    else {
                        cancelBooking(bookingArrayList);
                        if (!checkRequest("Change", id, startDate, endDate, numAuto, numManual, vehicles)) {
                            recoverBooking(bookingArrayList);
                            System.out.println("Change Rejected");
                        }
                    }
                }

            }
        }

        if (sc!=null) sc.close();


    }

    private static void depotVehicle(String locationName, String vehicleName, String vehicleType){
        Location location = getLocation(locationName);
        if (location == null){
            location = new Location(locationName);
            locations.add(location);
        }
        location.addVehicle(new Vehicle(vehicleName, vehicleType, location));
        vehicles.add(new Vehicle(vehicleName, vehicleType, location));
    }

    private static Location getLocation (String locationName){
        for (Location location: locations){
            if(location.getName().equals(locationName)){
                return location;
            }
        }
        return null;
    }


    private static ArrayList<Booking> getBookingList(int requestID){
        ArrayList<Booking> bookingArrayList = new ArrayList<>();
        for (Location location : locations) {
            ArrayList<Vehicle> vehicles = location.getVehicles();
            for (Vehicle vehicle : vehicles) {
                ArrayList<Booking> bookings = vehicle.getBookings();
                for (Booking booking : bookings) {
                    if (booking.getId() == requestID){
                        bookingArrayList.add(booking);
                    }
                }
            }
            if (bookingArrayList.size() > 0) break;
        }
        return bookingArrayList;
    }

    private static void cancelBooking(ArrayList<Booking> bookingArrayList) {
        for (Booking booking : bookingArrayList) {
            Vehicle vehicle = booking.getVehicle();
            ArrayList<Booking> bookings = vehicle.getBookings();
            bookings.remove(booking);
        }
    }

    private static void recoverBooking(ArrayList<Booking> bookingArrayList) {
        for (Booking booking : bookingArrayList) {
            Vehicle vehicle = booking.getVehicle();
            ArrayList<Booking> bookings = vehicle.getBookings();
            bookings.add(booking);
        }
    }

    public static ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> allVehicles = new ArrayList<>();
        for (Location location: locations) {
            ArrayList<Vehicle> depotVehicles = location.getVehicles();
            for (Vehicle vehicle : depotVehicles) {
                allVehicles.add(vehicle);
            }
        }
        return allVehicles;
    }

    private static boolean checkRequest(String requestType, int id, Calendar startDate, Calendar endDate,
                                         int autoNum, int manualNum, ArrayList<Vehicle> allVehicles) {

        int autoAvailable = 0;
        int manualAvailable = 0;
        for (Vehicle vehicle : allVehicles) {
            if (vehicle.getType().equals("Automatic") && vehicle.isAvailable(startDate, endDate)) {
                autoAvailable++;
            } else if (vehicle.getType().equals("Manual") && vehicle.isAvailable(startDate, endDate)) {
                manualAvailable++;
            }
        }

        System.out.println(autoAvailable + " " + manualAvailable);
        if (autoAvailable < autoNum || manualAvailable < manualNum) return false;
        else return true;
    }

    private static void processRequest (String requestType, int id, Calendar startDate, Calendar endDate,
                                           int autoNum, int manualNum){
        int i = 0;
        while (i < locations.size()) {
            Location location = locations.get(i);
            int[] arr = location.book(requestType, id, startDate, endDate, autoNum, manualNum);
            int autoLeft = arr[0];
            int manualLeft = arr[1];
            if (autoLeft == 0 && manualLeft == 0) break;
            else {
                autoNum = autoLeft;
                manualNum = manualLeft;
                i++;
            }
        }
    }

    private static Calendar convertStringToCalendar(String hour, String month, String day) {
        Calendar calendar = Calendar.getInstance();
        int monthInt = convertStringToMonth(month);
        calendar.set(Calendar.MONTH, monthInt);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        calendar.set(Calendar.YEAR, CURRENT_YEAR);
        return calendar;
    }

    public static int convertStringToMonth(String month){
        int monthInInt = 0;
        if (month.equals("Jan"))
            monthInInt = Calendar.JANUARY;
        else if (month.equals("Feb"))
            monthInInt = Calendar.FEBRUARY;
        else if (month.equals("Mar"))
            monthInInt= Calendar.MARCH;
        else if (month.equals("Apr"))
            monthInInt = Calendar.APRIL;
        else if (month.equals("May"))
            monthInInt = Calendar.MAY;
        else if (month.equals("Jun"))
            monthInInt = Calendar.JUNE;
        else if (month.equals("Jul"))
            monthInInt = Calendar.JULY;
        else if (month.equals("Aug"))
            monthInInt = Calendar.AUGUST;
        else if (month.equals("Sep"))
            monthInInt = Calendar.SEPTEMBER;
        else if (month.equals("Oct"))
            monthInInt = Calendar.OCTOBER;
        else if (month.equals("Nov"))
            monthInInt = Calendar.NOVEMBER;
        else if (month.equals("Dec"))
            monthInInt = Calendar.DECEMBER;
        return monthInInt;
    }
}
