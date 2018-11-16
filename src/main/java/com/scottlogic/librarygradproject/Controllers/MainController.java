package com.scottlogic.librarygradproject.Controllers;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class MainController {

    @RequestMapping("/")
    public RedirectView redirect(ModelMap model) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://127.0.0.1:3000");
        return redirectView;
    }
}
