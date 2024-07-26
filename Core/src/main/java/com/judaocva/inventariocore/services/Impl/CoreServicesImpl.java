package com.judaocva.inventariocore.services.Impl;

import com.judaocva.inventariocore.dto.*;
import com.judaocva.inventariocore.miscellaneous.GenericMethods;
import com.judaocva.inventariocore.repository.ProductsRepository;
import com.judaocva.inventariocore.repository.UsersRepository;
import com.judaocva.inventariocore.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import com.judaocva.inventariocore.services.CoreServices;

@Repository
public class CoreServicesImpl implements CoreServices {

    private final UsersRepository usersRepository;
    private final TokenRepository tokenRepository;
    private final ProductsRepository productsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CoreServicesImpl(UsersRepository usersRepository, TokenRepository tokenRepository, ProductsRepository productsRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.tokenRepository = tokenRepository;
        this.productsRepository = productsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public GenericResponseDto login(LoginRequestDto loginRequestDto) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        try {
            String storedPassword = usersRepository.getPasswordByEmail(loginRequestDto.getEmail());
            if (passwordEncoder.matches(loginRequestDto.getPassword(), storedPassword)) {
                UserDto userDto = usersRepository.getUserByEmail(loginRequestDto.getEmail());
                String token = GenericMethods.generateToken();
                tokenRepository.saveOrUpdateToken(userDto.getId(), token);
                LoginResponseDto loginResponseDto = new LoginResponseDto();
                loginResponseDto.setName(userDto.getName());
                loginResponseDto.setEmail(userDto.getEmail());
                loginResponseDto.setPhone(userDto.getPhone());
                loginResponseDto.setToken(token);
                genericResponseDto.setStatus(HttpStatus.OK.value());
                genericResponseDto.setMessage("Login successful");
                genericResponseDto.setData(loginResponseDto);
            } else {
                genericResponseDto.setStatus(HttpStatus.UNAUTHORIZED.value());
                genericResponseDto.setMessage("Invalid credentials");
            }
        } catch (Exception e) {
            e.printStackTrace();
            genericResponseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            genericResponseDto.setMessage("Error in login service, contact the administrator. " + e.getMessage());
        }
        return genericResponseDto;
    }

    @Override
    public GenericResponseDto saveUser(UserDto userRequestDto) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        try {
            if (userRequestDto.getPassword() != null && !userRequestDto.getPassword().isEmpty() && !userRequestDto.getPassword().isBlank())
                userRequestDto.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

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
            genericResponseDto.setMessage("Error saving user, contact the administrator. " + e.getMessage());
        }
        return genericResponseDto;
    }

    @Override
    public GenericResponseDto saveProduct(ProductSaveRequestDto productSaveRequestDto) {
        GenericResponseDto genericResponseDto = new GenericResponseDto();
        try {
            if (productSaveRequestDto.getId() == 0) {
                productSaveRequestDto.setIdUser(tokenRepository.getIdUserByToken(productSaveRequestDto.getToken()));
                productsRepository.createProduct(productSaveRequestDto);
            } else {
                productSaveRequestDto.setQuantity(productSaveRequestDto.getQuantity() + productsRepository.getQuantityById(productSaveRequestDto.getId()));
                productsRepository.updateProduct(productSaveRequestDto);
            }
            genericResponseDto.setStatus(HttpStatus.OK.value());
            genericResponseDto.setMessage("Product saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            genericResponseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            genericResponseDto.setMessage("Error saving product, contact the administrator. " + e.getMessage());
        }
        return genericResponseDto;
    }
}