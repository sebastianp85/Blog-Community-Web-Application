package me.code.blogapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntegrationPolicyController {

    @GetMapping("/integration_policy")
    public String showIntegrationPolicy() {
        return "integration_policy";
    }
}
