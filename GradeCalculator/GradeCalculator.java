import java.util.Scanner;

public class GradeCalculator {

    public static void main(String[] args) {
        final int GRADE_ENTRIES = 10;
        Scanner scanner = new Scanner(System.in);
        double currentGrade = 0;
        double totalGrade = 0;
        double avgGrade = 0;
        double maxGrade = Double.MIN_VALUE;
        double minGrade = Double.MAX_VALUE;

        for (int i = 0; i < GRADE_ENTRIES; i++) {
            System.out.println("Grade Entry #" + (i + 1));
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input, please try again! use a floating-point number to make an entry.");
                System.out.println("Grade Entry #" + (i + 1));
                scanner.next();
            }

            currentGrade = scanner.nextDouble();
            totalGrade += currentGrade;

            if (currentGrade > maxGrade) {
                maxGrade = currentGrade;
            }

            if (currentGrade < minGrade) {
                minGrade = currentGrade;
            }
        }

        scanner.close();

        avgGrade = totalGrade / GRADE_ENTRIES;
        System.out.println("Statistics:");
        System.out.println("Average Grade: " + avgGrade);
        System.out.println("Maximum Grade: " + maxGrade);
        System.out.println("Minimum Grade: " + minGrade);
    }
}