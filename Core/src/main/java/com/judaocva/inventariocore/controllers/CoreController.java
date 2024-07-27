package com.judaocva.inventariocore.controllers;

import com.judaocva.inventariocore.dto.GenericResponseDto;
import com.judaocva.inventariocore.dto.LoginRequestDto;
import com.judaocva.inventariocore.dto.ProductSaveRequestDto;
import com.judaocva.inventariocore.dto.UserDto;
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
    public GenericResponseDto saveUser(@RequestBody UserDto userRequestDto) {
        return coreServices.saveUser(userRequestDto);
    }

    @PostMapping("/login")
    @ResponseBody
    public GenericResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return coreServices.login(loginRequestDto);
    }

    @PostMapping("/products/save")
    @ResponseBody
    public GenericResponseDto saveProduct(@RequestBody ProductSaveRequestDto productSaveRequestDto) {
        return coreServices.saveProduct(productSaveRequestDto);
    }

    @GetMapping("/products/getProducts")
    @ResponseBody
    public GenericResponseDto getProductsByToken(@RequestHeader String token) {
        return coreServices.getProductsByToken(token);
    }

}
