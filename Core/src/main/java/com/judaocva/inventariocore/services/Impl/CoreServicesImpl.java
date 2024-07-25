package com.judaocva.inventariocore.services.Impl;

import com.judaocva.inventariocore.dto.GenericResponseDto;
import com.judaocva.inventariocore.dto.LoginRequestDto;
import com.judaocva.inventariocore.dto.LoginResponseDto;
import com.judaocva.inventariocore.dto.UserRequestDto;
import com.judaocva.inventariocore.miscellaneous.GenericMethods;
import com.judaocva.inventariocore.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import com.judaocva.inventariocore.services.CoreServices;

@Repository
public class CoreServicesImpl implements CoreServices {

    private final UsersRepository usersRepository;

    public CoreServicesImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public GenericResponseDto login(LoginRequestDto loginRequestDto) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        try {
            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setToken(GenericMethods.generateToken());
        } catch (Exception e) {
            genericResponseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            genericResponseDto.setMessage("Error in login service, contact the administrator.");
        }
        return genericResponseDto;
    }

    public GenericResponseDto saveUser(UserRequestDto userRequestDto) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        try {
            if (userRequestDto.getId() == 0) {
                usersRepository.createUser(userRequestDto);
            } else {
                usersRepository.updateUser(userRequestDto);
            }
            genericResponseDto.setStatus(HttpStatus.OK.value());
            genericResponseDto.setMessage("User saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            genericResponseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            genericResponseDto.setMessage("Error saving user, contact the administrator.");
        }
        return genericResponseDto;
    }

}
