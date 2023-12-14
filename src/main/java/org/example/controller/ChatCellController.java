package org.example.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static org.example.App.userMain;

public class ChatCellController {
    @FXML
    private Label nombreChatId;


    public void setChat(int user1_id, String user1_username, int user2_id, String user2_username) {

        if (user1_id == userMain.getId()) {
            nombreChatId.setText(user2_username);
        } else {
            nombreChatId.setText(user1_username);
        }
    }
}

