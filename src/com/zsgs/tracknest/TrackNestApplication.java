package com.zsgs.tracknest;

import com.zsgs.tracknest.features.signin.SignInView;
import com.zsgs.tracknest.util.ConsoleInput;
import com.zsgs.tracknest.util.ConsoleView;

import java.util.Scanner;

class TrackNestApplication {

    public static final int VERSION_NO = 1;
    public static final String VERSION_NAME = "1.0.0";

    public static void main(String[] args) {
        ConsoleView.title("Welcome to TrackNest");
        System.out.println("Order Your Products!!");
        System.out.println("Version " + VERSION_NAME);
        showLandingMenu();
    }

    private static void showLandingMenu() {
        Scanner scanner = ConsoleInput.getScanner();
        while (true) {
            ConsoleView.title("Main Menu");
            ConsoleView.option("1", "Sign In");
            ConsoleView.option("2", "Exit");
            ConsoleView.prompt("Choose an option");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    new SignInView().init();
                    break;
                case "2":
                    ConsoleView.success("Thank you for using TrackNest");
                    return;
                default:
                    ConsoleView.error("Invalid option. Please try again.");
            }
        }
    }
}
