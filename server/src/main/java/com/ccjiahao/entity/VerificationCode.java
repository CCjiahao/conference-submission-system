package com.ccjiahao.entity;

import lombok.Data;

import java.util.Date;


@Data
public class VerificationCode {
    private String email;
    private String code;
    private Date expirationTime;
}
