import java.util.Scanner;

public class VehicleDealershipApp {

    private Action action;
    private VehicleInventory inventory;

    private enum Action {
        START_MENU,
        MAIN_MENU,
        MOD_MENU,
        QUIT_APP,
    }

    public VehicleDealershipApp() {
        this.action = Action.START_MENU;
        this.inventory = null;
    }

    private String getInput(Scanner scanner, String... messages) {
        for (String message : messages) {
            System.out.println(message);
        }

        String input = scanner.nextLine().trim();

        if (!input.isBlank())
            return input;

        System.out.println("Empty input. Try again!");
        return getInput(scanner, messages);
    }

    private int getIntInput(Scanner scanner, String... messages) {
        for (String message : messages) {
            System.out.println(message);
        }

        // nextInt returns empty when switching to nextLine
        // thus parsing the raw input is the better
        // alternative for consistent result.
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid input type. Please provide a integer as input. Try again!");
            return getIntInput(scanner, messages);
        }
    }

    private Action startMenu(Scanner scanner) {
        int selectedOption = getIntInput(scanner,
                "Start Menu (Enter a number below to select the option)",
                "0. Quit program",
                "1. Create new inventory",
                "2. Load inventory from file");

        switch (selectedOption) {
            case 0:
                return Action.QUIT_APP;
            case 1:
                System.out.println("Creating new inventory...");
                this.inventory = new VehicleInventory();
                System.out.println("Successfully created inventory...");
                return Action.MAIN_MENU;
            case 2:
                while (true) {
                    String filename = getInput(scanner,
                            "Enter the filename (eg. example_inventory.txt) or 'q' to return to Start Menu.");

                    if (filename.equalsIgnoreCase("q")) {
                        return Action.START_MENU;
                    }

                    try {
                        System.out.println(String.format("Creating inventory from %s...", filename));
                        this.inventory = VehicleInventory.deserialize(filename);
                        System.out.println(String.format("Successfully created inventory from %s...", filename));
                    } catch (Exception e) {
                        System.out
                                .println(
                                        String.format("Failed to create inventory from %s. Try again!", filename));
                        continue;
                    }

                    return Action.MAIN_MENU;
                }
            default:
                System.out.println("Invalid option. Try again!");
                return startMenu(scanner);
        }
    }

    private Action mainMenu(Scanner scanner) {
        int selectedOption = getIntInput(scanner,
                "Main Menu (Enter a number below to select the option)",
                "0. Return to Start Menu",
                "1. View inventory",
                "2. Add vehicle",
                "3. Remove vehicle",
                "4. Modify vehicle");

        switch (selectedOption) {
            case 0:
                String filename = getInput(scanner,
                        "Any changes will be discarded. Would you like you save it? If so provide a filename otherwise 'q' to discard and return to Start Menu.");

                if (filename.equalsIgnoreCase("q")) {
                    System.out.println("Returning to Start Menu...");
                    return Action.START_MENU;
                }

                try {
                    System.out.println(String.format("Saving inventory to %s...", filename));
                    inventory.serialize(filename);
                    System.out.println(String.format("Successfully saved inventory to %s...", filename));
                } catch (Exception e) {
                    System.out.println("Failed to save. Returning to Start Menu...");
                }
                return Action.START_MENU;
            case 1:
                this.inventory.display();
                return Action.MAIN_MENU;
            case 2:
                String vin = getInput(scanner, "Enter the VIN of the new vehicle or 'q' to return to Main Menu.");
                if (vin.equalsIgnoreCase("q")) {
                    System.out.println("Returning to Main Menu...");
                    return Action.MAIN_MENU;
                }

                String make = getInput(scanner, "Enter the make of the new vehicle or 'q' to return to Main Menu.");
                if (make.equalsIgnoreCase("q")) {
                    System.out.println("Returning to Main Menu...");
                    return Action.MAIN_MENU;
                }

                String model = getInput(scanner, "Enter the model of the new vehicle or 'q' to return to Main Menu.");
                if (model.equalsIgnoreCase("q")) {
                    System.out.println("Returning to Main Menu...");
                    return Action.MAIN_MENU;
                }

                String color = getInput(scanner, "Enter the color of the new vehicle or 'q' to return to Main Menu.");
                if (color.equalsIgnoreCase("q")) {
                    System.out.println("Returning to Main Menu...");
                    return Action.MAIN_MENU;
                }

                int year = 0;
                while (true) {
                    year = getIntInput(scanner, "Enter the year of the new vehicle or '-1' to return to Main Menu.");

                    if (year == -1) {
                        System.out.println("Returning to Main Menu...");
                        return Action.MAIN_MENU;
                    }

                    if (year < -1) {
                        System.out.println("Invalid input value. Year cannot be less than 0. Try again!");
                        continue;
                    }

                    break;
                }

                int mileage = 0;
                while (true) {
                    mileage = getIntInput(scanner,
                            "Enter the mileage of the new vehicle or '-1' to return to Main Menu.");

                    if (mileage == -1) {
                        System.out.println("Returning to Main Menu...");
                        return Action.MAIN_MENU;
                    }

                    if (mileage < -1) {
                        System.out.println("Invalid input value. Mileage cannot be less than 0. Try again!");
                        continue;
                    }

                    break;
                }

                Vehicle addedVehicle = this.inventory.add(new Vehicle(vin, make, model, color, year, mileage));
                System.out
                        .println(
                                "The vehicle has been added to the inventory. Here's an overview of it's information.");
                System.out.println(addedVehicle.summary());
                return Action.MAIN_MENU;
            case 3:
                while (true) {
                    int vehicleIndex = getIntInput(scanner,
                            "Enter the Vehicle Index of the vehicle you'd like to remove or '-1' to return to Main Menu.");

                    if (vehicleIndex == -1) {
                        System.out.println("Returning to Main Menu...");
                        return Action.MAIN_MENU;
                    }

                    if (!this.inventory.exists(vehicleIndex)) {
                        System.out.println("Vehicle Index provided does not exists. Try again!");
                        continue;
                    }

                    Vehicle removedVehicle = this.inventory.remove(vehicleIndex);
                    System.out.println("The following vehicle has been removed:");
                    System.out.println(removedVehicle.summary());
                    return Action.MAIN_MENU;
                }
            case 4:
                return Action.MOD_MENU;
            default:
                System.out.println("Invalid option. Try again!");
                return mainMenu(scanner);
        }
    }

    private Action modMenu() {
        return Action.MOD_MENU;
    }

    public void run() {
        System.out.println("Welcome to the Vehicle Dealership app!");

        try (Scanner scanner = new Scanner(System.in)) {
            while (this.action != Action.QUIT_APP) {
                switch (this.action) {
                    case START_MENU:
                        this.action = startMenu(scanner);
                        break;
                    case MAIN_MENU:
                        this.action = mainMenu(scanner);
                        break;
                    case MOD_MENU:
                        this.action = modMenu();
                        break;
                    default:
                        break;
                }
            }
        }

        System.out.println("Thank you for using the Vehicle Dealership app!");
    }

}
