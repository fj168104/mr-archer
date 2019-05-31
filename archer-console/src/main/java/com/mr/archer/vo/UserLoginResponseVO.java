package com.mr.archer.vo;

import lombok.Data;

@Data
public class UserLoginResponseVO {
    private String username;
    private String fullName;
    private boolean isAdmin;
}

