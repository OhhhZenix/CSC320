import java.util.Scanner;

public class VehicleDealership {

  private static String getInput(Scanner scanner, String... messages) {
    for (String message : messages) {
      System.out.println(message);
    }

    String input = scanner.nextLine().trim();

    if (!input.isBlank())
      return input;

    System.out.println("Empty input. Try again!");
    return getInput(scanner, messages);
  }

  private static int getIntInput(Scanner scanner, String... messages) {
    for (String message : messages) {
      System.out.println(message);
    }

    // nextInt returns empty when switching to nextLine
    // thus parsing the raw input is the better
    // alternative for consistent result.
    try {
      return Integer.parseInt(scanner.nextLine());
    } catch (Exception e) {
      System.out.println("Invalid input type. Please provide a integer input. Try again!");
      return getIntInput(scanner, messages);
    }
  }

  private static void startMenu() {

  }

  public static void main(String[] args) {
    VehicleInventory inventory = null;
    int menu = 0;
    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        switch (menu) {
          // Start Menu
          case 0:
            if (true) {
              System.out.println();
              continue;
            }
            break;
          // Main Menu
          case 1:
            break;
          // Modification Menu
          case 2:
            break;
          default:
            break;
        }
      }
    }
  }
}
