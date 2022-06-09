package com.ccjiahao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Paper {
    private String id;
    private String username;
    private String title;
    private String abstracts;
    private String expertise;
    private String collaborators;
    private Date commitTime;
    private String state;
}
