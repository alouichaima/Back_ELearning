package com.example.demo.service;

public class LoginResponse {
    String message;
    Boolean status;
    private String userRole; // Add this line
    private Long userId; // Add this line

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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
	public LoginResponse(String message, Boolean status, String userRole, Long userId) {
		super();
		this.message = message;
		this.status = status;
		this.userRole = userRole;
        this.userId = userId;

    }
    

}
