import java.text.DecimalFormat;
import java.util.Scanner;

public class GroceryBillCalculator {

  public static double getDoubleInput(Scanner scanner, String... messages) {
    for (String message : messages) {
      System.out.println(message);
    }

    try {
      return Double.parseDouble(scanner.nextLine().trim());
    } catch (Exception e) {
      System.out.println("Invalid input type. Try again!");
      return getDoubleInput(scanner, messages);
    }
  }

  public static void main(String[] args) {
    DecimalFormat currencyFormatter = new DecimalFormat("#.00");
    DecimalFormat percentFormatter = new DecimalFormat("##.##%");
    System.out.println("Welcome to the Grocery Bill Calculator!");

    try (Scanner scanner = new Scanner(System.in)) {
      double discount = getDoubleInput(scanner, "Enter a coupon discount as decimal (eg. 0.10).");
      if (discount > 1.0 || discount <= 0) {
        discount = 0.10;
        System.out.println("Your input has either exceeded 100% or is less than or equal to 0%.");
        System.out.println("Thus, the system has assigned default coupon discount of 10%.");
      } else {
        System.out.println(
            String.format("The coupon discount has been set to %s.", percentFormatter.format(discount)));
      }

      final int WEEKS_IN_MONTH = 4;
      double[] weeklyBills = new double[WEEKS_IN_MONTH];
      double totalBill = 0.0;
      System.out.println("Now, let's enter grocery bills for weeks 1-4.");
      for (int i = 0; i < WEEKS_IN_MONTH; i++) {
        weeklyBills[i] = getDoubleInput(scanner, String.format("Enter your grocery bill for week %s.", (i + 1)));
        totalBill += weeklyBills[i];
      }

      System.out.println(
          String.format("The monthly total bill without coupon discount is $%s.", currencyFormatter.format(totalBill)));
      System.out.println(String.format("The weekly average bill without coupon discount is $%s.",
          currencyFormatter.format(totalBill / 4)));

      double totalBillWithDiscount = totalBill * (1 - discount);
      System.out.println(
          String.format("The monthly total bill with coupon discount is $%s.",
              currencyFormatter.format(totalBillWithDiscount)));
      System.out.println(String.format("The weekly average bill with coupon discount is $%s.",
          currencyFormatter.format(totalBillWithDiscount / 4)));
    }

    System.out.println("Thank you for using the Grocery Bill Calculator!");
  }
}
