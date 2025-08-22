package com.saladay.saladay_api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class RootController {
    /* 호스트에 접근하면 바로 이동 되도록 하는 컨트롤러. */
    @GetMapping("/")
    public RedirectView redirectToDocs() {
        return new RedirectView("/swagger-ui/index.html");
    }
}