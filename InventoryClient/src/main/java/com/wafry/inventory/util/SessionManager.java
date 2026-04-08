package com.wafry.inventory.util;

import com.wafry.inventory.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionManager {
    private static final Logger log = LoggerFactory.getLogger(SessionManager.class);
    private static SessionManager instance;

    private String token;
    private User currentUser;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setSession(String token, User user) {
        this.token = token;
        this.currentUser = user;
        log.info("Session set for user: {}", user.getUsername());
    }

    public void clearSession() {
        this.token = null;
        this.currentUser = null;
        log.info("Session cleared");
    }

    public boolean isAuthenticated() {
        return token != null && currentUser != null;
    }

    public String getUserRole() {
        return currentUser != null ? currentUser.getRoleName() : null;
    }
}

