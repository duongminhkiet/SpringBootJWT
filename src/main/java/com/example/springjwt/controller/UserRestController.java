package com.example.springjwt.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjwt.entities.User;
import com.example.springjwt.exceptionjwt.InvalidUserException;
import com.example.springjwt.exceptionjwt.InvalidUserException.ErrorCode;
import com.example.springjwt.service.JwtService;
import com.example.springjwt.service.UserService;

@RestController
@RequestMapping("/rest")
public class UserRestController {
  @Autowired
  private JwtService jwtService;
  @Autowired
  private UserService userService;
  @Autowired 
  private PasswordEncoder passwordEncoder;
  /* ---------------- GET ALL USER ------------------------ */
  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public ResponseEntity<List<User>> getAllUser() {
    return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
  }
  /* ---------------- GET USER BY ID ------------------------ */
  @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
  public ResponseEntity<Object> getUserById(@PathVariable int id) {
    User user = userService.findById(id);
    if (user != null) {
      return new ResponseEntity<Object>(user, HttpStatus.OK);
    }
    return new ResponseEntity<Object>("Not Found User", HttpStatus.NO_CONTENT);
  }
  /* ---------------- CREATE NEW USER ------------------------ */
//  @RequestMapping(value = "/users", method = RequestMethod.POST)
//  public ResponseEntity<String> createUser(@RequestBody User user) {
//    if (null != userService.add(user)) {
//      return new ResponseEntity<String>("Created!", HttpStatus.CREATED);
//    } else {
//      return new ResponseEntity<String>("User Existed!", HttpStatus.BAD_REQUEST);
//    }
//  }
  @RequestMapping(value = "/users", method = RequestMethod.POST)
  public User createUser(@RequestBody User user) throws InvalidUserException{
	  User userFound = userService.findByUsername(user.getUsername());
	  if(null != userFound) {
		  throw new InvalidUserException(userFound.getUsername(), ErrorCode.EXIST);
	  }else {
		  String pass = user.getPassword();
		  user.setPassword(passwordEncoder.encode(pass));
	  }
	  if (null != userService.add(user)) {
		  return user;
	  }
	  return null;
  }
  /* ---------------- DELETE USER ------------------------ */
  @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteUserById(@PathVariable int id) {
    userService.delete(id);
    return new ResponseEntity<String>("Deleted!", HttpStatus.OK);
  }
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<String> login(HttpServletRequest request, @RequestBody User user) {
    String result = "";
    HttpStatus httpStatus = null;
    try {
      if (userService.checkUserExistingInDB(user)) {
        result = jwtService.generateTokenLogin(user.getUsername());
        httpStatus = HttpStatus.OK;
      } else {
        result = "Wrong userId and password";
        httpStatus = HttpStatus.BAD_REQUEST;
      }
    } catch (Exception ex) {
      result = "Server Error";
      httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<String>(result, httpStatus);
  }
}
