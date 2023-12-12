package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import javafx.application.Platform;
import org.example.model.Chat;
import org.example.model.Error;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class NotificationAPIClient extends RootAPIClient {

    public void observeNewMessages(Integer userId, APICallback callback) {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(20000);
                    doObserveNewMessages(userId, callback);
                } catch (InterruptedException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void doObserveNewMessages(Integer userId, org.example.api.APICallback callback)  throws IOException, InterruptedException{
        String url = BASE_URL + "/users/" + userId + "/notifications";
        HttpResponse<String> response = doGETRequest(url);

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            List<Chat> list = Arrays.asList(gson.fromJson(response.body(), Chat[].class));

            onSuccess(callback, list);
        } else {
            Gson gson = new Gson();
            Error error = gson.fromJson(response.body(), Error.class);
            onError(callback, error);
        }
    }

}
