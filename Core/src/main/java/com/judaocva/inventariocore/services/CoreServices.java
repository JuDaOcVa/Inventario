package com.judaocva.inventariocore.services;

import com.judaocva.inventariocore.dto.GenericResponseDto;
import com.judaocva.inventariocore.dto.LoginRequestDto;
import com.judaocva.inventariocore.dto.UserRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface CoreServices {
    GenericResponseDto login(LoginRequestDto loginRequestDto);

    GenericResponseDto saveUser(UserRequestDto userRequestDto);

}
