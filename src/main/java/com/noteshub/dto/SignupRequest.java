package com.noteshub.dto;
import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
    private String role; // ✅ Add this line
}
