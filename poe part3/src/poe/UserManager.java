package poe;
import java.util.Scanner;

public class UserManager {
    private String storedUsername;
    private String storedPassword;
    private String storedCell;
    private String firstName;
    private String lastName;
    Scanner input = new Scanner(System.in)

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    public boolean checkCellPhoneNumber(String number) {
        return number.matches("^\\+27\\d{9}$");
    }

    public boolean registerUser() {
        System.out.println("Enter First Name:");
        firstName = input.nextLine();
        System.out.println("Enter Last Name:");
        lastName = input.nextLine();
        System.out.println("Enter Username:");
        String username = input.nextLine();
        if (!checkUserName(username)) {
            System.out.println("Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.");
            return false;
        } else {
            System.out.println("Username successfully captured.");
        }
        System.out.println("Enter Password:");
        String password = input.nextLine();
        if (!checkPasswordComplexity(password)) {
            System.out.println("Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.");
            return false;
        } else {
            System.out.println("Password successfully captured.");
        }
        System.out.println("Enter Cell Phone Number (+27):");
        String cell = input.nextLine();
        if (!checkCellPhoneNumber(cell)) {
            System.out.println("Cell phone number incorrectly formatted or does not contain international code.");
            return false;
        } else {
            System.out.println("Cell phone number successfully added.");
        }

        storedUsername = username;
        storedPassword = password;
        storedCell = cell;
        return true;
    }

    public boolean loginUser() {
        System.out.println("Login Username:");
        String username = input.nextLine();
        System.out.println("Login Password:");
        String password = input.nextLine();
        return username.equals(storedUsername) && password.equals(storedPassword);
    }

    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
