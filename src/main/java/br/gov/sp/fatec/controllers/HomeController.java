package br.gov.sp.fatec.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    @RequestMapping(value = "/", method=RequestMethod.GET)
    public String index() {
        return "Welcome to Travelling Reviews REST.";
    }
}
