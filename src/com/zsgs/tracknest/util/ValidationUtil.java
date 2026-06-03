package com.zsgs.tracknest.util;

public class ValidationUtil {

    private static final String NAME_REGEX = "^[A-Za-z][A-Za-z0-9 .'-]{1,49}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PHONE_REGEX = "^[6-9][0-9]{9}$";
    private static final String ADDRESS_REGEX = "^[A-Za-z0-9][A-Za-z0-9 .,#'/-]{2,99}$";
    private static final String PRODUCT_NAME_REGEX = "^[A-Za-z0-9][A-Za-z0-9 .'-]{1,49}$";

    private ValidationUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidName(String value) {
        return value != null && value.trim().matches(NAME_REGEX);
    }

    public static boolean isValidEmail(String value) {
        return value != null && value.trim().matches(EMAIL_REGEX);
    }

    public static boolean isValidPhoneNumber(Long value) {
        return value != null && String.valueOf(value).matches(PHONE_REGEX);
    }

    public static boolean isValidAddress(String value) {
        return value != null && value.trim().matches(ADDRESS_REGEX);
    }

    public static boolean isValidProductName(String value) {
        return value != null && value.trim().matches(PRODUCT_NAME_REGEX);
    }

    public static boolean isPositive(Long value) {
        return value != null && value > 0L;
    }

    public static boolean isZeroOrPositive(Long value) {
        return value != null && value >= 0L;
    }
}
