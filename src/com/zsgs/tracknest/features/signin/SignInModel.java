package com.zsgs.tracknest.features.signin;

import com.zsgs.tracknest.data.dto.LoginRequest;
import com.zsgs.tracknest.data.dto.User;
import com.zsgs.tracknest.data.repository.TrackNestDB;
import com.zsgs.tracknest.util.ValidationUtil;

class SignInModel {

    private static final int MIN_PASSWORD_LENGTH = 4;

    private final SignInView signInView;

    SignInModel(SignInView signInView) {
        this.signInView = signInView;
    }

    void authenticate(LoginRequest request, User.Role expectedRole) {
        if (request == null || ValidationUtil.isBlank(request.getEmail()) || ValidationUtil.isBlank(request.getPassword())) {
            signInView.onSignInFailed("Email and password are required.");
            return;
        }
        if (!ValidationUtil.isValidEmail(request.getEmail())) {
            signInView.onSignInFailed("Enter a valid email address.");
            return;
        }

        User user = TrackNestDB.getInstance().authenticateUser(request.getEmail(), request.getPassword());
        if (user == null) {
            signInView.onSignInFailed("Invalid email or password.");
            return;
        }
        if (expectedRole != null && user.getRole() != expectedRole) {
            signInView.onSignInFailed("Please choose the correct sign in option for this account.");
            return;
        }
        signInView.onSignInSuccessful(user);
    }

    void register(User user) {
        if (user == null
                || ValidationUtil.isBlank(user.getUserName())
                || ValidationUtil.isBlank(user.getEmail())
                || ValidationUtil.isBlank(user.getPassword())) {
            signInView.onSignInFailed("Name, email and password are required.");
            return;
        }
        if (!ValidationUtil.isValidName(user.getUserName())) {
            signInView.onSignInFailed("Name must be 2-50 characters and start with a letter.");
            return;
        }
        if (!ValidationUtil.isValidEmail(user.getEmail())) {
            signInView.onSignInFailed("Enter a valid email address.");
            return;
        }
        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            signInView.onSignInFailed("Password must have at least " + MIN_PASSWORD_LENGTH + " characters.");
            return;
        }
        if (!ValidationUtil.isValidPhoneNumber(user.getPhoneNumber())) {
            signInView.onSignInFailed("Phone number must have 10 digits and start with 6, 7, 8, or 9.");
            return;
        }
        if (!ValidationUtil.isValidAddress(user.getPlace())) {
            signInView.onSignInFailed("Place must be 3-100 valid address characters.");
            return;
        }
        if (TrackNestDB.getInstance().getUserByEmail(user.getEmail()) != null) {
            signInView.onSignInFailed("This email is already registered. Please sign in.");
            return;
        }

        user.setEmail(user.getEmail().trim());
        user.setUserName(user.getUserName().trim());
        user.setPlace(user.getPlace().trim());
        user.setRole(User.Role.USER);
        User savedUser = TrackNestDB.getInstance().addUser(user);
        if (savedUser == null) {
            signInView.onSignInFailed("Could not create user.");
            return;
        }
        signInView.onSignInSuccessful(savedUser);
    }
}
