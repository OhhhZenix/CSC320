import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;

public class VehicleInventory {
    private ArrayList<Vehicle> vehicles;

    public VehicleInventory() {
        this.vehicles = new ArrayList<>();
    }

    public VehicleInventory(ArrayList<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public boolean exists(int index) {
        return (index >= 0) && (index < this.vehicles.size());
    }

    public Vehicle add(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        return vehicle;
    }

    public Vehicle remove(int index) {
        return this.vehicles.remove(index);
    }

    public Vehicle get(int index) {
        return this.vehicles.get(index);
    }

    public void display() {
        if (this.vehicles.isEmpty()) {
            System.out.println("The inventory is empty!");
            return;
        }

        System.out.println("The inventory consist of the following vehicles:");
        ListIterator<Vehicle> vehicleIterator = this.vehicles.listIterator();
        while (vehicleIterator.hasNext()) {
            System.out
                    .println(
                            String.format("Vehicle Index %s is %s", vehicleIterator.nextIndex(),
                                    vehicleIterator.next().summary()));
        }
    }

    public static VehicleInventory deserialize(String fileName) throws Exception {
        try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
            ArrayList<Vehicle> vehicles = new ArrayList<>();
            while (scanner.hasNextLine()) {
                vehicles.add(Vehicle.deserialize(scanner.nextLine()));
            }
            return new VehicleInventory(vehicles);
        } catch (Exception e) {
            throw new Exception("Failed to read file.");
        }
    }

    public void serialize(String fileName) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(fileName))) {
            for (Vehicle vehicle : this.vehicles) {
                writer.println(vehicle.serialize());
            }
        } catch (Exception e) {
            throw new Exception("Failed to write file.");
        }
    }

}
