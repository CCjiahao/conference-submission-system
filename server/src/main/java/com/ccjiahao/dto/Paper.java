package com.ccjiahao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Paper {
    private String token;
    private String title;
    private String abstracts;
    private String expertise;
    private String collaborators;
    private String paper;
}
