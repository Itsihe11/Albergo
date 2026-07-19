package com.example.progettoalbergo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.progettoalbergo.DTO.AdminRequest;
import com.example.progettoalbergo.Services.AdminHib;




@RestController
@RequestMapping("/api/admin")
public class AdminController {



    @Autowired
    private AdminHib adminDependancy;




    @PostMapping("/login")
    public String login(
            @RequestBody AdminRequest request){


        return adminDependancy.login(
                request.getUsername(),
                request.getPassword()
        );

    }


}