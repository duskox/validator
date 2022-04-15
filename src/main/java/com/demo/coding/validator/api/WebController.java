package com.demo.coding.validator.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pageTitle", "Work order entry!");
        return "index";
    }
}
