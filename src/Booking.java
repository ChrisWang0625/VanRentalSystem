import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jiuding on 2017/3/28.
 */
public class Booking {
    private int id;
    private ArrayList<Vehicle> vehicles;
    private Date startDate;
    private Date endDate;

    /**
     * @param id id of the booking
     * @param vehicles list of vehicle booked
     * @param startDate start date of the booking
     * @param endDate end date of the booking
     */
    public Booking(int id, ArrayList<Vehicle> vehicles, Date startDate, Date endDate){
        this.id = id;
        this.vehicles = vehicles;
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
    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * @return start date of the booking
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @return end date of the booking
     */
    public Date getEndDate() {
        return endDate;
    }
}
