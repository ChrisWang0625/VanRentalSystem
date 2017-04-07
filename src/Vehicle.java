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
     * @return
     */
    public boolean isAvailable(Calendar startDate, Calendar endDate) {
        for (Booking booking : bookings){
            Calendar tempEndDate = (Calendar) endDate.clone();
            tempEndDate.add(Calendar.HOUR_OF_DAY, 1);
            Calendar coolDownEndDate = booking.getEndDate();
            coolDownEndDate.add(Calendar.HOUR_OF_DAY, 1);
            if (tempEndDate.before(booking.getStartDate())||startDate.after(coolDownEndDate)) continue;
            else {
                Calendar tempDate = (Calendar) startDate.clone();
                while (tempDate.compareTo(endDate) != 0) {
                    if (tempDate.compareTo(booking.getStartDate()) >= 0 && tempDate.compareTo(coolDownEndDate)<=0) return false;
                    tempDate.add(Calendar.HOUR_OF_DAY, 1);
                }
                if (tempDate.compareTo(booking.getStartDate())>=0 && tempDate.compareTo(coolDownEndDate) <= 0) return false;
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

    /**
     * @param id id of the booking
     */
    public void deleteRentalRecord(int id){
        for (Booking booking : bookings){
            if (booking.getId() == id){
                bookings.remove(booking);
            }
        }

    }
}
