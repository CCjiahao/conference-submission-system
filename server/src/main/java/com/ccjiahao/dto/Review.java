package com.ccjiahao.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Review {
    private String token;
    private int paperId;
    private String isAssociated;
    private int logic;
    private int sci;
    private int innovation;
    private String passOrReject;
    private String suggestion;
}
