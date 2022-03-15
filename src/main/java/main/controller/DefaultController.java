package main.controller;

import main.service.SettingsService;
import main.service.StatisticsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DefaultController {

    @Value("${upload.path}")
    private String uploadPath;
    private final StatisticsService statisticsService;
    private final ModelMapper modelMapper;
    private final SettingsService settingsService;


    public DefaultController(StatisticsService statisticsService, ModelMapper modelMapper, SettingsService settingsService) {
        this.statisticsService = statisticsService;
        this.modelMapper = modelMapper;
        this.settingsService = settingsService;
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET},
            value = "/**/{path:[^\\\\.]*}")
    public String redirectToIndex() {
        return "forward:/";
    }
}
