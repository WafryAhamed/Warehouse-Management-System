package com.wafry.inventory.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wafry.inventory.model.AuthResponse;
import com.wafry.inventory.model.DashboardStats;
import com.wafry.inventory.model.Product;
import com.wafry.inventory.util.SessionManager;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ApiClient {
    private static final Logger log = LoggerFactory.getLogger(ApiClient.class);
    private static final String BASE_URL = "http://localhost:8080/api/v1";
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    private static final Gson GSON = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    public static AuthResponse login(String username, String password) throws IOException {
        String json = GSON.toJson(new LoginRequest(username, password));

        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(BASE_URL + "/auth/login")
                .post(body)
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Login failed: " + response.message());
            }
            String responseBody = response.body().string();
            return GSON.fromJson(responseBody, AuthResponse.class);
        }
    }

    public static DashboardStats getDashboardStats() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/dashboard/stats")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .get()
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to fetch dashboard stats: " + response.message());
            }
            String responseBody = response.body().string();
            return GSON.fromJson(responseBody, DashboardStats.class);
        }
    }

    public static String getAllProducts() throws IOException {
        Request request = new Request.Builder()
                .url(BASE_URL + "/products")
                .header("Authorization", "Bearer " + SessionManager.getInstance().getToken())
                .get()
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to fetch products: " + response.message());
            }
            return response.body().string();
        }
    }

    private static class LoginRequest {
        String username;
        String password;

        LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}

