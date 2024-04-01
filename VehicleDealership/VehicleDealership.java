import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class VehicleDealership {

  public static class Vehicle {
    private String vin;
    private String make;
    private String model;
    private String color;
    private int year;
    private int mileage;

    public Vehicle() {
      this.vin = "??? VIN";
      this.make = "??? Model";
      this.model = "??? Make";
      this.color = "??? Color";
      this.year = 0;
      this.mileage = 0;
    }

    public Vehicle(String vin, String make, String model, String color, int year, int mileage) {
      this.vin = vin;
      this.make = make;
      this.model = model;
      this.color = color;
      this.year = year;
      this.mileage = mileage;
    }

    public String getVIN() {
      return this.vin;
    }

    public void setVIN(String vin) {
      this.vin = vin;
    }

    public String getMake() {
      return this.make;
    }

    public void setMake(String make) {
      this.make = make;
    }

    public String getModel() {
      return this.model;
    }

    public void setModel(String model) {
      this.model = model;
    }

    public String getColor() {
      return this.color;
    }

    public void setColor(String color) {
      this.color = color;
    }

    public int getYear() {
      return this.year;
    }

    public void setYear(int year) throws Exception {
      if (year < 0)
        throw new Exception("Invalid input value. Year cannot be less than 0.");

      this.year = year;
    }

    public int getMileage() {
      return this.mileage;
    }

    public void setMileage(int mileage) throws Exception {
      if (mileage < 0)
        throw new Exception("Invalid input value. Mileage cannot be less than 0.");

      this.mileage = mileage;
    }

    public String summary() {
      return String.format(
          "%s %s %s %s with %s miles (VIN: %s).",
          this.year, this.color, this.make, this.model, this.mileage, this.vin);
    }

    public static Vehicle deserialize(String data) {
      String[] splitData = data.split(";");
      try {
        String vin = splitData[0];
        String make = splitData[1];
        String model = splitData[2];
        String color = splitData[3];
        int year = Integer.parseInt(splitData[4]);
        int mileage = Integer.parseInt(splitData[5]);
        return new Vehicle(vin, make, model, color, year, mileage);
      } catch (Exception e) {
        System.out.println("Failed to read data.");
        e.printStackTrace();
      }
      return new Vehicle();
    }

    public String serialize() {
      return String.format(
          "%s;%s;%s;%s;%s;%s",
          this.vin, this.make, this.model, this.color, this.year, this.mileage);
    }
  }

  public static class VehicleInventory {
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

    public void add(Vehicle vehicle) {
      this.vehicles.add(vehicle);
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
            .println(String.format("%s. %s", vehicleIterator.nextIndex(), vehicleIterator.next().summary()));
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

  public static String getHandledStringInput(Scanner scanner, String... messages) {
    for (String message : messages) {
      System.out.println(message);
    }

    String input = scanner.nextLine().trim();

    if (input.isBlank()) {
      System.out.println("Empty input. Try again!");
      return getHandledStringInput(scanner, messages);
    }

    return input;
  }

  public static int getHandledIntInput(Scanner scanner, String... messages) {
    for (String message : messages) {
      System.out.println(message);
    }

    // nextInt returns empty when switching to nextLine
    // thus parsing the raw input is the better
    // alternative for consistent result.
    try {
      return Integer.parseInt(scanner.nextLine());
    } catch (Exception e) {
      System.out.println("Invalid input type. Try again!");
      return getHandledIntInput(scanner, messages);
    }
  }

  public static int getMenuOption(Scanner scanner, List<Integer> options, String... messages) {
    int input = getHandledIntInput(scanner, messages);

    for (int option : options) {
      if (input == option) {
        return input;
      }
    }

    System.out.println("Invalid options. Try again!");
    return getMenuOption(scanner, options, messages);
  }

  public static int getVehicleIndex(Scanner scanner, VehicleInventory inventory, String... messages) {
    int index = getHandledIntInput(scanner, messages);

    if (!inventory.exists(index) && index != -1) {
      System.out.println("The index provided does not exists. Try again!");
      return getVehicleIndex(scanner, inventory, messages);
    }

    return index;
  }

  public static void main(String[] args) {
    VehicleInventory inventory = null;
    try (Scanner scanner = new Scanner(System.in)) {
      // Start Menu
      while (true) {
        int startMenuInput = getMenuOption(scanner, Arrays.asList(0, 1, 2, 3),
            "Start Menu Options (Enter a number below)",
            "0. Quit app",
            "1. Create a new inventory",
            "2. Load a existing inventory");

        if (startMenuInput == 0) {
          System.out.println("Thank you for using the Vehicle Dealership app!");
          break;
        }

        if (startMenuInput == 1) {
          System.out.println("Creating new inventory...");
          inventory = new VehicleInventory();
          System.out.println("Successfully created inventory...");
        }

        if (startMenuInput == 2) {
          boolean shouldReturnStartMenu = false;

          // Load Inventory Menu
          while (true) {
            String loadInventoryMenuInput = getHandledStringInput(scanner,
                "Enter the name of the inventory file (eg. example_inventory.txt) or type 'q' to return to Start Menu.");

            if (loadInventoryMenuInput.equalsIgnoreCase("q")) {
              System.out.println("Returning to Start Menu...");
              shouldReturnStartMenu = true;
              break;
            }

            try {
              System.out.println(String.format("Creating new inventory from %s...", loadInventoryMenuInput));
              inventory = VehicleInventory.deserialize(loadInventoryMenuInput);
              System.out.println(String.format("Successfully created inventory from %s...", loadInventoryMenuInput));
              break;
            } catch (Exception e) {
              System.out
                  .println(String.format("Failed to create inventory from %s. Try again.", loadInventoryMenuInput));
              continue;
            }
          }

          if (shouldReturnStartMenu) {
            continue;
          }
        }

        // Main Menu
        while (true) {
          int mainMenuInput = getMenuOption(scanner, Arrays.asList(0, 1, 2, 3, 4),
              "Main Menu Options (Enter a number below)",
              "0. Return to Start Menu",
              "1. View inventory",
              "2. Add vehicle",
              "3. Remove vehicle",
              "4. Modify vehicle");

          if (mainMenuInput == 0) {
            while (true) {
              String saveInventoryMenuInput = getHandledStringInput(scanner,
                  "Any changes will be discarded. Would you like you save it? If so provide a filename otherwise 'q' to discard and return to Start Menu.");

              if (saveInventoryMenuInput.equalsIgnoreCase("q")) {
                System.out.println("Returning to Start Menu...");
                break;
              }

              try {
                System.out.println(String.format("Saving inventory to %s...", saveInventoryMenuInput));
                inventory.serialize(saveInventoryMenuInput);
                break;
              } catch (Exception e) {
                System.out.println("Failed to save. Returning to Start Menu...");
                continue;
              }
            }

            break;
          }

          if (mainMenuInput == 1) {
            inventory.display();
          }

          if (mainMenuInput == 2) {
            while (true) {
              String vin = getHandledStringInput(scanner,
                  "Enter the vehicle's VIN number or type 'q' to return to Main Menu.");
              if (vin.equalsIgnoreCase("q")) {
                System.out.println("Returning to Main Menu...");
                break;
              }

              String make = getHandledStringInput(scanner,
                  "Enter the vehicle's make or type 'q' to return to Main Menu.");
              if (make.equalsIgnoreCase("q")) {
                System.out.println("Returning to Main Menu...");
                break;
              }

              String model = getHandledStringInput(scanner,
                  "Enter the vehicle's model or type 'q' to return to Main Menu.");
              if (model.equalsIgnoreCase("q")) {
                System.out.println("Returning to Main Menu...");
                break;
              }

              String color = getHandledStringInput(scanner,
                  "Enter the vehicle's color or type 'q' to return to Main Menu.");
              if (color.equalsIgnoreCase("q")) {
                System.out.println("Returning to Main Menu...");
                break;
              }

              int year;
              while (true) {
                year = getHandledIntInput(scanner, "Enter the vehicle's year or type '-1' to return to Main Menu.");

                if (year < -1) {
                  System.out.println("Year of the vehicle cannot be negative. Try again!");
                  continue;
                }

                break;
              }
              if (year == -1) {
                System.out.println("Returning to Main Menu...");
                break;
              }

              int mileage;
              while (true) {
                mileage = getHandledIntInput(scanner,
                    "Enter the vehicle's mileage or type '-1' to return to Main Menu.");

                if (year < -1) {
                  System.out.println("A vehicle's mileage cannot be negative. Try again!");
                  continue;
                }

                break;
              }
              if (mileage == -1) {
                System.out.println("Returning to Main Menu...");
                break;
              }

              Vehicle vehicle = new Vehicle(vin, make, model, color, year, mileage);
              inventory.add(vehicle);
              System.out
                  .println("The vehicle has been added to the inventory. Here's an overview of it's information.");
              System.out.println(vehicle.summary());
              break;
            }
          }

          if (mainMenuInput == 3) {
            int index = getVehicleIndex(scanner, inventory,
                "Enter the vehicle's index you'd like to remove or or type '-1' to return to Main Menu.");

            if (index == -1) {
              System.out.println("Returning to Main Menu...");
              break;
            }

            Vehicle vehicle = inventory.remove(index);
            System.out.println("The following vehicle has been removed:");
            System.out.println(vehicle.summary());
            continue;
          }

          if (mainMenuInput == 4) {
            int index = getVehicleIndex(scanner, inventory,
                "Enter the vehicle's index you'd like to modify or or type '-1' to return to Main Menu.");

            if (index == -1) {
              System.out.println("Returning to Main Menu...");
              break;
            }

            Vehicle vehicle = inventory.get(index);
            while (true) {
              int modifyMenuInput = getHandledIntInput(scanner,
                  String.format("Modification Menu Options (Enter a number below - Selected Index: %s)", index),
                  "0. Return to Main Menu",
                  "1. Display vehicle info",
                  "2. Edit VIN",
                  "3. Edit Make",
                  "4. Edit Model",
                  "5. Edit Color",
                  "6. Edit Year",
                  "7. Edit Milage");

              if (modifyMenuInput == 0) {
                System.out.println("Returning to Main Menu...");
                break;
              }

              if (modifyMenuInput == 1) {
                System.out.println(vehicle.summary());
              }
            }
          }
        }
      }
    }
  }
}
