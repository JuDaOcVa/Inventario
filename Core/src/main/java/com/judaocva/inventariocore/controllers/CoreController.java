package com.judaocva.inventariocore.controllers;

import com.judaocva.inventariocore.dto.GenericResponseDto;
import com.judaocva.inventariocore.dto.LoginRequestDto;
import com.judaocva.inventariocore.dto.UserRequestDto;
import com.judaocva.inventariocore.services.CoreServices;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CoreController {

    public final CoreServices coreServices;

    public CoreController(CoreServices coreServices) {
        this.coreServices = coreServices;
    }

    @PostMapping("/users/save")
    @ResponseBody
    public GenericResponseDto saveUser(@RequestBody UserRequestDto userRequestDto) {
        return coreServices.saveUser(userRequestDto);
    }

    @PostMapping("/login")
    @ResponseBody
    public GenericResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return coreServices.login(loginRequestDto);
    }

}
