package com.ccjiahao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Review {
    private int id;
    private int paperId;
    private String reviewer;
    private String isAssociated;
    private int logic;
    private int sci;
    private int innovation;
    private String passOrReject;
    private String suggestion;
    private Date reviewTime;
}
