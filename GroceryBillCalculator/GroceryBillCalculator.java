import java.text.DecimalFormat;
import java.util.Scanner;

public class GroceryBillCalculator {

  public static void main(String[] args) {
    DecimalFormat decimalFormatter = new DecimalFormat("#.00");
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to the Grocery Bill Calculator!");

    System.out.print("Lets begin! Enter a coupon amount amount as a decimal (example, .10): ");
    double discount = scanner.nextDouble();
    if (discount > 1.0 || discount <= 0) {
      discount = 0.1;
      System.out.println(
          "The discount you entered exceeds 100% or is less than or equal to zero. Thus, the discount is set to 10%.");
    }

    double[] bills = new double[4];
    System.out.println("\nNow let's enter your grocery bill from weeks 1-4.");
    for (int i = 0; i < 4; i++) {
      System.out.print("Enter your grocery bill for week " + (i + 1) + ": ");
      bills[i] = scanner.nextDouble();
    }

    System.out.println("\nHere is a view of your grocery summary.");

    double total = 0;
    for (double bill : bills) {
      total += bill;
    }
    double avgWeeklyWithoutCoupon = total / 4;
    double avgMonthlyWithoutCoupon = avgWeeklyWithoutCoupon * 4;
    System.out.println(
        "Weekly total without coupon: $" + decimalFormatter.format(avgWeeklyWithoutCoupon));
    System.out.println(
        "Monthly total without coupon: $" + decimalFormatter.format(avgMonthlyWithoutCoupon));

    double totalWithCoupon = total * (1 - discount);
    double avgWeeklyWithCoupon = totalWithCoupon / 4;
    double avgMonthlyWithCoupon = avgWeeklyWithCoupon * 4;
    System.out.println(
        "Weekly total with coupon("
            + decimalFormatter.format(discount * 100)
            + "% discount): $"
            + decimalFormatter.format(avgWeeklyWithCoupon));
    System.out.println(
        "Monthly total with coupon("
            + decimalFormatter.format(discount * 100)
            + "% discount): $"
            + decimalFormatter.format(avgMonthlyWithCoupon));

    scanner.close();
  }
}
