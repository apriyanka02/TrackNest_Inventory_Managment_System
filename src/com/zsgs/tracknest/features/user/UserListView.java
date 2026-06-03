package com.zsgs.tracknest.features.user;

import com.zsgs.tracknest.data.dto.User;
import com.zsgs.tracknest.util.ConsoleInput;
import com.zsgs.tracknest.util.ConsoleView;
import com.zsgs.tracknest.util.IdFormatter;

import java.util.List;
import java.util.Scanner;

public class UserListView {

    private final UserListModel userListModel;
    private final Scanner scanner;

    public UserListView() {
        this.userListModel = new UserListModel();
        this.scanner = ConsoleInput.getScanner();
    }

    public void init() {
        ConsoleView.title("User Management");
        ConsoleView.option("1", "View all users");
        ConsoleView.option("2", "View user");
        ConsoleView.prompt("Choose an option");
        if ("2".equals(scanner.nextLine().trim())) {
            showUser();
        } else {
            showAllUsers();
        }
    }

    private void showAllUsers() {
        List<User> users = userListModel.getUsers();
        printLine("All Users");
        if (users.isEmpty()) {
            System.out.println("No users found.");
        } else {
            for (User user : users) {
                printUser(user);
            }
        }
        printLine("End of Users");
    }

    private void showUser() {
        ConsoleView.prompt("Enter user id number");
        User user = userListModel.getUser(readLong());
        printLine("User Details");
        if (user == null) {
            System.out.println("User not found.");
        } else {
            printUser(user);
        }
        printLine("End of User Details");
    }

    private Long readLong() {
        return IdFormatter.parseIdNumber(scanner.nextLine());
    }

    private void printUser(User user) {
        System.out.println(IdFormatter.userId(user.getUserId()) + " | " + user.getUserName()
                + " | " + user.getEmail()
                + " | " + user.getRole());
    }

    private void printLine(String title) {
        ConsoleView.title(title);
    }
}
