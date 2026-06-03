package com.zsgs.tracknest.features.user;

import com.zsgs.tracknest.data.dto.User;
import com.zsgs.tracknest.data.repository.TrackNestDB;

import java.util.ArrayList;
import java.util.List;

class UserListModel {

    List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for (User user : TrackNestDB.getInstance().getUsers()) {
            if (user.getRole() == User.Role.USER) {
                users.add(user);
            }
        }
        return users;
    }

    User getUser(Long userId) {
        User user = TrackNestDB.getInstance().getUserById(userId);
        if (user == null || user.getRole() != User.Role.USER) {
            return null;
        }
        return user;
    }
}
