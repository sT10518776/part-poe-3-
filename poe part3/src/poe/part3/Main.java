package poe.part3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        UserManager user = new UserManager();
        boolean registered = user.registerUser();

        if (registered) {
            boolean loginSuccess = user.loginUser();
            System.out.println(user.returnLoginStatus(loginSuccess));

            if (loginSuccess) {
                MessageManager messages = new MessageManager(scanner);
                messages.startMessageSession();
            }
        } else {
            System.out.println("Registration failed.");
        }

        scanner.close();
    }
}