package com.wangzi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.wangzi.service.IUserService;


@RestController
public class UserController {
	private Logger logger = LogManager.getLogger(UserController.class);
	@Autowired
	private IUserService userService;
}
