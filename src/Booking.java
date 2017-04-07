import java.util.Calendar;

/**
 * Created by Jiuding on 2017/3/28.
 */
public class Booking {
    private int id;
    private Vehicle vehicle;
    private Calendar startDate;
    private Calendar endDate;

    /**
     * constructor
     * @param id id of the booking
     * @param vehicle vehicle booked
     * @param startDate start date of the booking
     * @param endDate end date of the booking
     */
    public Booking(int id, Vehicle vehicle, Calendar startDate, Calendar endDate){
        this.id = id;
        this.vehicle = vehicle;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return id booking id
     */
    public int getId() {
        return id;
    }

    /**
     * @return list of vehicles booked in this booking
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * @return start date of the booking
     */
    public Calendar getStartDate() {
        return startDate;
    }

    /**
     * @return end date of the booking
     */
    public Calendar getEndDate() {
        return endDate;
    }
}
