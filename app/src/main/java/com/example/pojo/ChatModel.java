package com.example.pojo;

import java.nio.charset.StandardCharsets;

public class ChatModel {

    private  String Message;
    private  String id_msg;
    private  String emailUser;
    private  String mobile;


    public ChatModel(String message, String id_msg, String emailUser,String mobile) {
        Message = message;
        this.id_msg = id_msg;
        this.emailUser = emailUser;
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ChatModel() {
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getId_msg() {
        return id_msg;
    }

    public void setId_msg(String id_msg) {
        this.id_msg = id_msg;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }
}
