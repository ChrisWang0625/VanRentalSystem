import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Jiuding on 2017/3/21.
 */
public class VanRentalSystem {

    private static ArrayList<Location> locations;
    private static final int CURRENT_YEAR = 2017;

    public static void main(String args[]) throws ParseException {
        locations = new ArrayList<>();
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
                depotVehicle(cmd[1], cmd[2], cmd[3]);
            } else if (cmd[0].equals("Request") || cmd[0].equals("Change")) {
                int id = Integer.parseInt(cmd[1]);
                Calendar startDate = convertStringToCalendar(cmd[2], cmd[3], cmd[4]);
                Calendar endDate = convertStringToCalendar(cmd[5], cmd[6], cmd[7]);
                int numAuto = 0;
                int numManual = 0;
                for (int i = 9; i < cmd.length; i += 2) {
                    if (cmd[i].equals("Automatic")) numAuto = Integer.parseInt(cmd[i-1]);
                    else if (cmd[i].equals("Manual")) numManual = Integer.parseInt(cmd[i-1]);
                }
                if (cmd[0].equals("Request")){
                    if (!processRequest(cmd[0], id, startDate, endDate, numAuto, numManual)) {
                        System.out.println("Booking rejected");
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
        location.addVehicle(new Vehicle(vehicleName, vehicleType));
    }

    private static Location getLocation (String locationName){
        for (Location location: locations){
            if(location.getName().equals(locationName)){
                return location;
            }
        }
        return null;
    }




    private static boolean processRequest (String requestType, int id, Calendar startDate, Calendar endDate,
                                           int autoNum, int manualNum){
        for (Location location: locations) {
            if(location.book(requestType, id, startDate, endDate, autoNum, manualNum)) return true;
        }
        return false;
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
