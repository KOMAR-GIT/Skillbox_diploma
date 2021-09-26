package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class DefaultController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }




}
