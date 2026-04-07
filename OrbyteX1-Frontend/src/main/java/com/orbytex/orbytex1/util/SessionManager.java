package com.orbytex.orbytex1.util;

import com.orbytex.orbytex1.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionManager {
    private static SessionManager instance;

    @Getter @Setter
    private String token;

    @Getter @Setter
    private User currentUser;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
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

