package com.zsgs.tracknest.features.signin;

import com.zsgs.tracknest.data.dto.LoginRequest;
import com.zsgs.tracknest.data.dto.User;
import com.zsgs.tracknest.features.home.HomeView;
import com.zsgs.tracknest.util.ConsoleInput;
import com.zsgs.tracknest.util.ConsoleView;

import java.util.Scanner;

public class SignInView {

    private final SignInModel signInModel;
    private final Scanner scanner;
    private boolean authenticated;

    public SignInView() {
        this.signInModel = new SignInModel(this);
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        while (true) {
            ConsoleView.title("TrackNest Account");
            ConsoleView.option("1", "Admin sign in");
            ConsoleView.option("2", "User sign in");
            ConsoleView.option("3", "New user");
            ConsoleView.option("4", "Back");
            ConsoleView.prompt("Choose an option");
            switch (scanner.nextLine().trim()) {
                case "1":
                    showSignIn(User.Role.ADMIN);
                    if (authenticated) return;
                    break;
                case "2":
                    showSignIn(User.Role.USER);
                    if (authenticated) return;
                    break;
                case "3":
                    registerUser();
                    if (authenticated) return;
                    break;
                case "4":
                    return;
                default:
                    ConsoleView.error("Invalid option. Please try again.");
            }
        }
    }

    private void showSignIn(User.Role role) {
        ConsoleView.title(role == User.Role.ADMIN ? "Admin Sign In" : "User Sign In");
        while (!authenticated) {
            promptAndAuthenticate(role);
            if (authenticated) return;
            if (!promptRetry()) return;
        }
    }

    private void promptAndAuthenticate(User.Role role) {
        ConsoleView.prompt("Enter your email");
        String email = scanner.nextLine();
        ConsoleView.prompt("Enter your password");
        String password = scanner.nextLine();

        LoginRequest request = new LoginRequest();
        request.setEmail(email == null ? null : email.trim());
        request.setPassword(password);
        signInModel.authenticate(request, role);
    }

    private void registerUser() {
        ConsoleView.title("Create TrackNest Account");
        User user = new User();
        ConsoleView.prompt("Enter your name");
        user.setUserName(scanner.nextLine().trim());
        ConsoleView.prompt("Enter your email");
        user.setEmail(scanner.nextLine().trim());
        ConsoleView.prompt("Enter your password");
        user.setPassword(scanner.nextLine());
        ConsoleView.prompt("Enter phone number");
        user.setPhoneNumber(readLong());
        ConsoleView.prompt("Enter place");
        user.setPlace(scanner.nextLine().trim());
        signInModel.register(user);
    }

    private boolean promptRetry() {
        System.out.println();
        ConsoleView.option("1", "Retry");
        ConsoleView.option("2", "Exit");
        ConsoleView.prompt("Choose an option");
        return "1".equals(scanner.nextLine().trim());
    }

    void onSignInSuccessful(User user) {
        authenticated = true;
        ConsoleView.success("Welcome, " + user.getUserName());
        new HomeView(user).init();
    }

    void onSignInFailed(String message) {
        ConsoleView.error(message);
    }

    private Long readLong() {
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
