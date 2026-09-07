package com.mariza.customer.controller.api;

import com.mariza.customer.dto.AccountResponse;
import com.mariza.customer.dto.LoginRequest;
import com.mariza.customer.service.CustomerLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerLoginController {

    private final CustomerLoginService loginService;

    @PostMapping("/login")
    public AccountResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request.getEmail(), request.getPassword());
    }

}
