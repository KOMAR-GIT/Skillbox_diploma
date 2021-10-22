package main.controller;

import main.model.*;
import main.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class DefaultController {

    @Autowired
    private TagRepository tagRepository;

    @RequestMapping("/")
    public String index()
    {
        tagRepository.save(new Tag());
        return "index";

    }




}
