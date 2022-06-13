package com.ccjiahao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rebuttal {
    private int id;
    private int paperId;
    private String reviewer;
    private String rebuttal;
}
