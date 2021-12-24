package main.controller;

import main.repository.TagRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    private final TagRepository tagRepository;

    public DefaultController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }




}
