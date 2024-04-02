import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class VehicleDealership {

  public static String getHandledStringInput(Scanner scanner, String... messages) {
    for (String message : messages) {
      System.out.println(message);
    }

    String input = scanner.nextLine().trim();

    if (!input.isBlank())
      return input;

    System.out.println("Empty input. Try again!");
    return getHandledStringInput(scanner, messages);
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

    if (index == -1 || inventory.exists(index)) {
      return index;
    }

    System.out.println("The index provided does not exists. Try again!");
    return getVehicleIndex(scanner, inventory, messages);
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

            Vehicle selectedVehicle = inventory.get(index);
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
                System.out.println(selectedVehicle.summary());
              }

              if (modifyMenuInput == 2) {
                String oldVIN = selectedVehicle.getVIN();
                String newVIN = getHandledStringInput(scanner, String.format(
                    "Enter a new VIN for the Vehicle Index %s or 'q' to return to Modification Menu.", index));

                if (newVIN.equalsIgnoreCase("q")) {
                  System.out.println("Returning to Modification Menu...");
                  continue;
                }

                selectedVehicle.setVIN(newVIN);
                System.out.println(
                    String.format("The VIN for the Vehicle Index %s has been changed from %s to %s.", index, oldVIN,
                        newVIN));
              }

              if (modifyMenuInput == 3) {
                String oldMake = selectedVehicle.getMake();
                String newMake = getHandledStringInput(scanner, String.format(
                    "Enter a new make for the Vehicle Index %s or 'q' to return to Modification Menu.", index));

                if (newMake.equalsIgnoreCase("q")) {
                  System.out.println("Returning to Modification Menu...");
                  continue;
                }

                selectedVehicle.setMake(newMake);
                System.out.println(
                    String.format("The make for the Vehicle Index %s has been changed from %s to %s.", index, oldMake,
                        newMake));
              }

              if (modifyMenuInput == 4) {
                String oldModel = selectedVehicle.getModel();
                String model = getHandledStringInput(scanner,
                    String.format(
                        "Enter a new model for the Vehicle Index %s or 'q' to return to Modification Menu.", index));

                if (model.equalsIgnoreCase("q")) {
                  System.out.println("Returning to Modification Menu...");
                  continue;
                }

                selectedVehicle.setModel(model);
                System.out.println(
                    String.format("The model for the Vehicle Index %s has been changed from %s to %s.", index, oldModel,
                        model));
              }

              if (modifyMenuInput == 5) {
                String oldColor = selectedVehicle.getColor();
                String newColor = getHandledStringInput(scanner,
                    String.format(
                        "Enter a new color for the Vehicle Index %s or 'q' to return to Modification Menu.", index));

                if (newColor.equalsIgnoreCase("q")) {
                  System.out.println("Returning to Modification Menu...");
                  continue;
                }

                selectedVehicle.setColor(newColor);
                System.out.println(
                    String.format(
                        "The color for the Vehicle Index %s has been changed from %s to %s.", index, oldColor,
                        newColor));
              }

              if (modifyMenuInput == 6) {
                int oldYear = selectedVehicle.getYear();
                int newYear;

                while (true) {
                  newYear = getHandledIntInput(scanner, String.format(
                      "Enter a new year for the Vehicle Index %s or '-1' to return to Modification Menu.", index));

                  if (newYear > -1)
                    break;

                  System.out.println("Invalid input value. Year cannot be less than 0. Try again!");
                }

                if (newYear == -1) {
                  System.out.println("Returning to Modification Menu...");
                  continue;
                }

                selectedVehicle.setYear(newYear);
                System.out.println(
                    String.format(
                        "The year for the Vehicle Index %s has been changed from %s to %s.", index, oldYear,
                        newYear));
              }

              if (modifyMenuInput == 7) {
                int oldMileage = selectedVehicle.getMileage();
                int newMileage;

                while (true) {
                  newMileage = getHandledIntInput(scanner, String.format(
                      "Enter a new mileage for the Vehicle Index %s or '-1' to return to Modification Menu.", index));

                  if (newMileage > -1)
                    break;

                  System.out.println("Invalid input value. Mileage cannot be less than 0. Try again!");
                }

                if (newMileage == -1) {
                  System.out.println("Returning to Modification Menu...");
                  continue;
                }

                selectedVehicle.setMileage(newMileage);
                System.out.println(
                    String.format(
                        "The mileage for the Vehicle Index %s has been changed from %s to %s.", index, oldMileage,
                        newMileage));
              }
            }
          }
        }
      }
    }
  }
}
