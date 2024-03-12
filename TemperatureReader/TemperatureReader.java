import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;

public class TemperatureReader {

  public static void main(String[] args) {
    final int DAYS_PER_WEEK = 7;
    List<String> daysOfWeek =
        List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
    double[] tempsOfWeek = {79.1, 60.3, 30.4, 40.5, 61.5, 72.2, 82.2};
    boolean isValidInput = false;
    DecimalFormat decimalFormatter = new DecimalFormat("#.0");

    try (Scanner scanner = new Scanner(System.in)) {
      do {
        System.out.println(
            "Enter the day of the week (Monday through Sunday) or type 'week' for weekly average.");
        String userInput = scanner.nextLine().trim();
        userInput = userInput.toLowerCase();
        userInput = userInput.substring(0, 1).toUpperCase() + userInput.substring(1);

        if (userInput.equalsIgnoreCase("Week")) {
          double totalTemp = 0.0;
          for (int i = 0; i < DAYS_PER_WEEK; i++) {
            double temp = tempsOfWeek[i];
            totalTemp += temp;
            System.out.println(
                String.format(
                    "Day: %s, Temperature: %s degrees",
                    daysOfWeek.get(i), decimalFormatter.format(temp)));
          }
          double weeklyAvgTemp = totalTemp / DAYS_PER_WEEK;
          System.out.println(
              String.format(
                  "Weekly Average Temperature: %s degrees",
                  decimalFormatter.format(weeklyAvgTemp)));
          isValidInput = true;
        } else if (daysOfWeek.contains(userInput)) {
          int indexByDay = daysOfWeek.indexOf(userInput);
          System.out.println(
              String.format(
                  "Day: %s, Temperature: %s degrees",
                  daysOfWeek.get(indexByDay), tempsOfWeek[indexByDay]));
          isValidInput = true;
        } else {
          System.out.println("Invalid input. Try again.");
        }
      } while (!isValidInput);
    }
  }
}
