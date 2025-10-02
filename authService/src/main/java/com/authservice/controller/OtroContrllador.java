package com.authservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otro")
public class OtroContrllador  {

    @GetMapping("")
    public String saludo(){
        return "Hellow world";  
    }
}
