package com.zsgs.tracknest.util;

public class ConsoleView {

    private static final String LINE = "----------------------------------------";

    private ConsoleView() {
    }

    public static void title(String title) {
        System.out.println();
        System.out.println(LINE);
        System.out.println(title);
        System.out.println(LINE);
    }

    public static void option(String number, String label) {
        System.out.println(number + ". " + label);
    }

    public static void prompt(String label) {
        System.out.print(label + ": ");
    }

    public static void success(String message) {
        System.out.println("[OK] " + message);
    }

    public static void error(String message) {
        System.out.println("[Error] " + message);
    }
}
