package com.zsgs.tracknest.util;

public class IdFormatter {

    private IdFormatter() {
    }

    public static String userId(Long id) {
        return format("USR", id);
    }

    public static String supplierId(Long id) {
        return format("SUP", id);
    }

    public static String productId(Long id) {
        return format("PRO", id);
    }

    public static String orderId(Long id) {
        return format("ORD", id);
    }

    public static Long parseIdNumber(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        int separatorIndex = trimmed.indexOf('-');
        if (separatorIndex >= 0 && separatorIndex < trimmed.length() - 1) {
            trimmed = trimmed.substring(separatorIndex + 1);
        }
        try {
            return Long.parseLong(trimmed);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static String format(String prefix, Long id) {
        if (id == null) return prefix + "-NA";
        return prefix + "-" + id;
    }
}
