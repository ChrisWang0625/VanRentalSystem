import java.util.*;

/**
 * Created by Jiuding on 2017/3/28.
 */
public class Vehicle {
    private String name;
    private String type;
    private ArrayList<Booking> bookings;
    private Location location;

    /**
     * constructor
     * @param name name of the vehicle
     * @param type type of the vehicle
     */
    public Vehicle(String name, String type, Location location) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.bookings = new ArrayList<>();
    }

    /**
     * @return name of the vehicle
     */
    public String getName() {
        return name;
    }

    /**
     * @return type of the vehicle (auto/manual)
     */
    public String getType() {
        return type;
    }

    /**
     * @return list of rental record
     */
    public ArrayList<Booking> getBookings() {
        return this.bookings;
    }

    /**
     * function to get location of the vehicle
     * @return location of the vehicle
     */
    public Location getLocation() {return this.location;}


    /**
     * function to check the availability of vehicle in a given period of time, the 1 hour cool down time of the booking is
     * also considered when implementing this function
     * @param startDate start date of the period
     * @param endDate end date of the period
     * @return true if the vehicle is available in given period and false if unavailable
     */
    public boolean isAvailable(Calendar startDate, Calendar endDate) {
        for (Booking booking : bookings){

            if (endDate.getTime().before(booking.getStartDate().getTime())||startDate.getTime().after(booking.getEndDate().getTime())) continue;
            else {
                Calendar tempDate = (Calendar) startDate.clone();
                while (tempDate.compareTo(endDate) != 0) {
                    if (tempDate.compareTo(booking.getStartDate()) >= 0 && tempDate.compareTo(booking.getEndDate())<=0) return false;
                    tempDate.add(Calendar.HOUR_OF_DAY, 1);
                }
                if (tempDate.compareTo(booking.getStartDate())>=0 && tempDate.compareTo(booking.getEndDate()) <= 0) return false;
            }
        }
        return true;
    }

    /**
     * @param startDate start date of the rental record
     * @param endDate end date of the rental record
     */
    public void insertRentalRecord(int id, Vehicle vehicle, Calendar startDate, Calendar endDate){
        bookings.add(new Booking(id, vehicle, startDate, endDate));
        System.out.print(this.name);
    }
}
