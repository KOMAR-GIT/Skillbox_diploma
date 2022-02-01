package main.controller;

import main.repository.TagRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {


    public DefaultController(TagRepository tagRepository) {

    }

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }




}
