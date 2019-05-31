package com.mr.archer.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateRolePermVo implements Serializable{

    private String rid;
    private Integer ptype;
    private List<String> pvals = new ArrayList<>();
}
