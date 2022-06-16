package com.ccjiahao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;

@Data
@AllArgsConstructor
public class Paper {
    private int id;
    private String username;
    private String title;
    private String abstracts;
    private String expertise;
    private String keywords;
    private String collaborators;
    private String paper;
    private Date commitTime;
    private String state;

    public boolean isAuthor(String username) {
        if (username.equals(this.username)) return true;
        return Arrays.asList(collaborators.split(",")).contains(username);
    }
}
