package com.mr.archer.controller;

import com.alibaba.fastjson.JSON;
import com.mr.archer.ArcherApiApplication;
import com.mr.archer.entity.SysUser;
import com.mr.archer.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by feng on 2019/6/20 0020
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={ArcherApiApplication.class, SysUserControllerTest.class})
public class SysUserControllerTest {

	@Autowired
	private SysUserService userService;

	@Test
	public void updateUser() throws Exception {
		SysUser user = userService.selectById(2);


		//username can not be modified
		user.setAvatar(null);

		userService.updateAllColumnById(user);
	}

}