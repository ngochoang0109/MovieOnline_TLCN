package com.tlcn.movieonline.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public String adminHome(){
        return "/admin/admin-index";
    }

}
