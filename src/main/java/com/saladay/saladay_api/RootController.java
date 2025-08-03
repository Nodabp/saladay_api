package com.saladay.saladay_api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RootController {

    @GetMapping("/")
    public RedirectView redirectToDocs() {
        return new RedirectView("/swagger-ui/index.html"); // 또는 /docs, /api 등
    }
}