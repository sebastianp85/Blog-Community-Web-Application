package me.code.blogapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityPolicyController {

    @GetMapping("/security_policy")
    public String showSecurityPolicy() {
        return "security_policy";
    }
}
