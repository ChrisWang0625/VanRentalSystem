import java.util.Date;
import java.util.HashMap;

/**
 * Created by Jiuding on 2017/3/28.
 */
public class Vehicle {
    private String name;
    private String type;
    private HashMap rentalRecord = new HashMap();
    private String location;

    /**
     * @param name name of the vehicle
     * @param type type of the vehicle
     * @param location location of the vehicle
     */
    public Vehicle(String name, String type, String location) {
        this.name = name;
        this.type = type;
        this.location = location;
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
    public HashMap getRentalRecord() {
        return rentalRecord;
    }

    /**
     * @return location of the vehicle
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param startDate start date of the rental record
     * @param endDate end date of the rental record
     */
    public void insertRentalRecord(Date startDate, Date endDate){
        rentalRecord.put(startDate, endDate);
    }

    /**
     * @param startDate start date of the rental record
     */
    public void deleteRentalRecord(Date startDate){
        rentalRecord.remove(startDate);
    }
}
