package com.ayamultipedia.services.auth;


import com.ayamultipedia.dto.SignupRequest;
import com.ayamultipedia.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);
}
