package com.example.springjwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjwt.entities.User;
import com.example.springjwt.service.UserService;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {
	  @Autowired
	  private UserService userService;
	  /* ---------------- GET ALL USER ------------------------ */
	  @Secured("ROLE_ADMIN")
	  @RequestMapping(value = "/users", method = RequestMethod.GET)
	  public ResponseEntity<List<User>> getAllUser() {
	    return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
	  }
}