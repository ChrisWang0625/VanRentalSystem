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

    public Booking(int id, ArrayList<Vehicle> vehicles, Date startDate, Date endDate){
        this.id = id;
        this.vehicles = vehicles;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
