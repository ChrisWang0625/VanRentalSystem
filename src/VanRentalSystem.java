import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jiuding on 2017/3/21.
 */
public class VanRentalSystem {

    private static ArrayList<Location> locations = new ArrayList<>();
    private static ArrayList<Vehicle> allVehicles = new ArrayList<>();
    private static final int CURRENT_YEAR = 2017;

    /**
     * main function
     * @param args command line arguments
     * @throws ParseException
     */
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
                allVehicles = getAllVehicles();
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
                    if (checkRequest(startDate, endDate, numAuto, numManual, allVehicles)) {
                        System.out.print("Booking " + id);
                        processRequest(id, startDate, endDate, numAuto, numManual);

                        if(sc.hasNextLine()) {
                            System.out.println();
                        }

                    } else {
                        System.out.println("Booking rejected");
                    }
                } else if (cmd[0].equals("Change")) {
                    ArrayList<Booking> bookingArrayList = getBookingList(id);
                    if (bookingArrayList.size()==0) System.out.println("Change rejected");
                    else {
                        cancelBooking(bookingArrayList);
                        if (!checkRequest(startDate, endDate, numAuto, numManual, allVehicles)) {
                            recoverBooking(bookingArrayList);
                            System.out.println("Change Rejected");
                        } else {
                            System.out.print("Change " + id);
                            processRequest(id, startDate, endDate, numAuto, numManual);
                            if(sc.hasNextLine()) {
                                System.out.println();
                            }
                        }
                    }
                }
            } else if (cmd[0].equals("Cancel")) {
                int id = Integer.parseInt(cmd[1]);
                ArrayList<Booking> backupBooking = getBookingList(id);
                if (backupBooking.size()==0) {
                    System.out.println("Cancel Rejected");
                } else {
                    cancelBooking(backupBooking);
                    System.out.println("Cancel " + id);
                }
            } else if (cmd[0].equals("Print")) {
                Location location = getLocation(cmd[1]);

                ArrayList<Vehicle> vehicles = location.getVehicles();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm MMM dd");

                for (Vehicle vehicle : vehicles) {
                    ArrayList<Booking> bookings = vehicle.getBookings();
                    Collections.sort(bookings, (o1, o2) -> {
                        Calendar a = o1.getStartDate();
                        Calendar b = o2.getEndDate();
                        if (a.before(b)) {
                            return -1;
                        } else if (a.equals(b)){
                            return 0;
                        } else {
                            return 1;
                        }

                    });
                    for (Booking booking : bookings) {
                        Calendar tempStartDate = (Calendar) booking.getStartDate().clone();
                        Calendar tempEndDate = (Calendar) booking.getEndDate().clone();
                        System.out.println(booking.getVehicle().getLocation().getName() + " "
                        + booking.getVehicle().getName() + " " + simpleDateFormat.format(tempStartDate.getTime())
                                + " " + simpleDateFormat.format(tempEndDate.getTime()));
                    }
                }
            }
        }

        if (sc!=null) sc.close();


    }

    /**
     * function to depot vehicle to the location, if location doesn't exist, the function will create a new location and depot vehicle to location
     * @param locationName the name of the location
     * @param vehicleName the name of the vehicle
     * @param vehicleType the type of gear of the vehicle
     */
    private static void depotVehicle(String locationName, String vehicleName, String vehicleType) {
        Location location = getLocation(locationName);
        if (location == null) {
            location = new Location(locationName);
            locations.add(location);
        }
        location.addVehicle(new Vehicle(vehicleName, vehicleType, location));
    }

    /**
     * @return ArrayList of all vehicles in the system.
     */
    private static ArrayList<Vehicle> getAllVehicles() {
        ArrayList<Vehicle> allVehicles = new ArrayList<>();
        for (Location l : locations) {
            ArrayList<Vehicle> vs = l.getVehicles();
            for (Vehicle v: vs) {
                allVehicles.add(v);
            }
        }
        return allVehicles;
    }

    /**
     * function to get the location given location name
     * @param locationName the name of the location
     * @return Location
     */
    private static Location getLocation (String locationName) {
        for (Location location: locations){
            if(location.getName().equals(locationName)){
                return location;
            }
        }
        return null;
    }


    /**
     * function to search and return a list of booking using a booking id
     * @param requestID the id of request
     * @return a list of booking of the required booking id
     */
    private static ArrayList<Booking> getBookingList(int requestID) {
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

    /**
     * function to cancel a list of booking given
     * @param bookingArrayList ArrayList of bookings to be canceled
     */
    private static void cancelBooking(ArrayList<Booking> bookingArrayList) {
        for (Booking booking : bookingArrayList) {
            Vehicle vehicle = booking.getVehicle();
            ArrayList<Booking> bookings = vehicle.getBookings();
            bookings.remove(booking);
        }
    }

    /**
     * function to recover booking when change or cancel rejected
     * @param bookingArrayList the ArrayList of booking to be recovered
     */
    private static void recoverBooking(ArrayList<Booking> bookingArrayList) {
        for (Booking booking : bookingArrayList) {
            Vehicle vehicle = booking.getVehicle();
            ArrayList<Booking> bookings = vehicle.getBookings();
            bookings.add(booking);
        }
    }


    /**
     * this function is used to check if the requirement can be fulfilled by check if there is enough automatic and manual
     * vans available. If not, the system will not process the booking or change, if there is enough vehicles available
     * in the given period of time, then proceed to booking.
     * @param startDate start date of the request
     * @param endDate end date of the request
     * @param autoNum number of automatic vans demanded
     * @param manualNum number of manual vans demanded
     * @param allVehicles ArrayList of all vehicles
     * @return true if the request can be done, and false if there is not enough available vans for rent
     */
    private static boolean checkRequest(Calendar startDate, Calendar endDate,
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

        if (autoAvailable < autoNum || manualAvailable < manualNum) return false;
        else return true;
    }


    /**
     * this function is to process the booking or change of booking by looping through all
     * locations until all the requests are satisfied.
     * @param id id of the request
     * @param startDate start date of the request
     * @param endDate end date of the request
     * @param autoNum number of automatic vans required
     * @param manualNum number of manual vans required
     */
    private static void processRequest (int id, Calendar startDate, Calendar endDate,
                                           int autoNum, int manualNum){
        for (Location location : locations){
            int[] arr = location.book(id, startDate, endDate, autoNum, manualNum);
            int prevAuto = autoNum;
            int prevManual = manualNum;
            autoNum = arr[0];
            manualNum = arr[1];
            if (autoNum == 0 && manualNum == 0) break;
            if (autoNum != prevAuto || manualNum != prevManual) System.out.print(";");
        }
    }

    /**
     * convert 3 strings to a calendar object
     * @param hour string of hour
     * @param month string of month
     * @param day string of day
     * @return the Calendar object of the date
     */
    private static Calendar convertStringToCalendar(String hour, String month, String day) {
        Calendar calendar = Calendar.getInstance();
        int monthInt = convertStringToMonth(month);
        calendar.set(Calendar.MONTH, monthInt);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        calendar.set(Calendar.YEAR, CURRENT_YEAR);
        return calendar;
    }

    /**
     * convert a string of month to a integer
     * @param month a string of short month e.g. "Jan"
     * @return the string of month
     */
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
