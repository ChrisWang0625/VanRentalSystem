import java.io.*;
import java.util.*;

/**
 * Created by Jiuding on 2017/3/21.
 */
public class VanRentalSystem {

    private ArrayList<Booking> bookings;
    private ArrayList<Vehicle> vehicles;

    public static void main(String args[]){
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(args[0]));
        } catch (FileNotFoundException e) {}
        finally {
            if (sc!=null) sc.close();
        }
    }

}
