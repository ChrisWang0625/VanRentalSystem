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

    public Vehicle(String name, String type, String location) {
        this.name = name;
        this.type = type;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public HashMap getRentalRecord() {
        return rentalRecord;
    }

    public String getLocation() {
        return location;
    }

    public void insertRentalRecord(Date startDate, Date endDate){
        rentalRecord.put(startDate, endDate);
    }

    public void deleteRentalRecord(Date startDate){
        rentalRecord.remove(startDate);
    }
}
