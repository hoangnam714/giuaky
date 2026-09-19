package com.huynhvohoangnam.giuaky.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.huynhvohoangnam.giuaky.model.User;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    public UserService() {
        // Tài khoản mẫu mặc định để test nhanh: admin / 123456
        users.add(new User("admin", "123456", "Nguyễn Văn Admin"));
    }

    public User login(String username, String password) {
        if (username == null || password == null) return null;
        return users.stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username.trim()) && u.getPassword().equals(password.trim()))
                .findFirst()
                .orElse(null);
    }

    public boolean register(User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty() || exists(user.getUsername())) {
            return false;
        }
        users.add(user);
        return true;
    }

    public boolean exists(String username) {
        return users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username.trim()));
    }

    public int count() {
        return users.size();
    }
}
