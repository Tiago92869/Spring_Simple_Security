package com.demo.security.dto;

import com.demo.security.domain.UserRole;

public record RegisterDto(String login, String password, UserRole role) {
}
