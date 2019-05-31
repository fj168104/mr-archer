package com.mr.archer.vo;

import lombok.Data;

@Data
public class ResponseEnvelope<T> {
    private String resultCode;
    private String message;
    private T data;
}
