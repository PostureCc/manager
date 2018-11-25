package com.chan.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CommonController {

    @ModelAttribute
    public void setValue(Model model){
        model.addAttribute("name","Model_Chan");

    }

}
