package com.example.demo.service;

public class LoginResponse {
    String message;
    Boolean status;
    private String userRole; // Add this line

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUserRole() {
        return userRole;
    }
	public LoginResponse(String message, Boolean status, String userRole) {
		super();
		this.message = message;
		this.status = status;
		this.userRole = userRole;
	}
    

}
