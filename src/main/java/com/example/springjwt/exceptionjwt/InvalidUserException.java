package com.example.springjwt.exceptionjwt;

public class InvalidUserException extends StringBaseLocalizedException {
    /**
    *
    */
   private static final long serialVersionUID = 1L;

   public enum ErrorCode {
       NOT_FOUND("not found"),
       UNAUTHORIZED("not authorized"),
       DISABLED("disabled"),
       EXIST("exists"),
       INVALID("invalid");
       
       private ErrorCode(final String name) {
   		this.name = name;
   	}
   	private final String name;
   	public String toString() {
   		return name;
   	}
   }

   public InvalidUserException(final String username, final ErrorCode errorcode) {
       super(username, errorcode.toString());
   }


}
