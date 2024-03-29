import java.util.Scanner;

public class CharacterPrinter {

  public static void main(String[] args) {
    System.out.println("Let's make a fictional character.");

    Scanner userInput = new Scanner(System.in);

    System.out.println("What is their first name?");
    String firstName = userInput.nextLine();

    System.out.println("What is their last name?");
    String lastName = userInput.nextLine();

    System.out.println("What is their street address?");
    String streetAddress = userInput.nextLine();

    System.out.println("What is their city?");
    String city = userInput.nextLine();

    System.out.println("What is their zip code?");
    int zipCode = userInput.nextInt();

    userInput.close();

    System.out.println("Here are the info about your fictional character.");
    System.out.println("First Name: " + firstName);
    System.out.println("Last Name: " + lastName);
    System.out.println("Street Address: " + streetAddress);
    System.out.println("City: " + city);
    System.out.println("Zipcode: " + zipCode);
  }
}
