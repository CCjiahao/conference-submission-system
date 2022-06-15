package com.ccjiahao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Paper {
    private int id;
    private String token;
    private String title;
    private String abstracts;
    private String expertise;
    private String collaborators;
    private String paper;
}
