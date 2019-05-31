package com.mr.archer.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用的选项对象
 */

@Data
public class Option implements Serializable{

    private Serializable id;
    private String val;
    private String val2;

    public Option(Serializable id, String val, String val2) {
        this.id = id;
        this.val = val;
        this.val2 = val2;
    }
}
