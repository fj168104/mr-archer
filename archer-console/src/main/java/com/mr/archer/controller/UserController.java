package com.mr.archer.controller;

import com.mr.archer.vo.ResponseEnvelope;
import com.mr.archer.vo.UserLoginRequestVO;
import com.mr.archer.vo.UserLoginResponseVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping("/login")
    public ResponseEntity<ResponseEnvelope<UserLoginResponseVO>> login(UserLoginRequestVO requestVO) {
        if (StringUtils.isEmpty(requestVO.getUsername())) {
            throw new IllegalArgumentException("Invalid Username");
        }
        if (StringUtils.isEmpty(requestVO.getPassword())) {
            throw new IllegalArgumentException("Invalid Password");
        }
        UserLoginResponseVO responseVO = new UserLoginResponseVO();
        responseVO.setUsername(responseVO.getUsername());
        ResponseEnvelope<UserLoginResponseVO> userLoginResponseVOResponseEnvelope = new ResponseEnvelope<>();
        return new ResponseEntity<>(userLoginResponseVOResponseEnvelope, HttpStatus.OK);
    }

}
