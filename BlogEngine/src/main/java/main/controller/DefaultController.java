package main.controller;

import main.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    @Autowired
    private TagRepository tagRepository;

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }




}
