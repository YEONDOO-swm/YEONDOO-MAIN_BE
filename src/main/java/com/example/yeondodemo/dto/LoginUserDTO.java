package com.example.yeondodemo.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter @Setter @ToString @Slf4j
public class LoginUserDTO {
    private Long workspaceId;
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9!@#$%^&*()]{6,19}$")
    private String password;
}
