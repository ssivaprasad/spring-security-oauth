package com.ssp.apps.oauth.resource.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ssp.apps.oauth.resource.client.dto.AuthorizeModel;

@Controller
public class ClientController {

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/authrizationcode")
    public String authrizationcode(ModelMap model,
            @RequestParam(name = "code", required = false) String authorizationCode) {
        AuthorizeModel authorizationModel = new AuthorizeModel("code", "client_1", "READ",
                "http://localhost:8080/authrizationcode");
        model.addAttribute("model", authorizationModel);
        return "authrizationcode";
    }


    @GetMapping("/authrizationcode_success")
    public String authrizationcode_success(ModelMap model,
            @RequestParam(name = "code") String code) {
        model.addAttribute("code", code);
        model.addAttribute("isAuthenticated", true);
        return "authrizationcode_success";
    }

}
