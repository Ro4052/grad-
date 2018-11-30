package com.scottlogic.librarygradproject.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexRedirectController {

    @RequestMapping(path = {"/dashboard", "/profile"})
    public Object serveIndex() {
        return "forward:/index.html";
    }
}
