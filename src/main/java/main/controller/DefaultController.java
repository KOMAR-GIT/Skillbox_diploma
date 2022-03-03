package main.controller;

import main.api.response.ResponseWithErrors;
import main.api.response.StatisticResponse;
import main.dto.GlobalSettingsCodes;
import main.dto.interfaces.StatisticsInterface;
import main.security.SecurityUser;
import main.service.SettingsService;
import main.service.StatisticsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/api/statistics/my")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<StatisticResponse> userStatistics() {
        StatisticsInterface statisticsInterface = statisticsService.getUserStatistics();
        StatisticResponse statisticResponse = modelMapper.map(statisticsInterface, StatisticResponse.class);
        statisticResponse.setFirstPublication(statisticResponse.getFirstPublication() / 1000);
        return ResponseEntity.ok(statisticResponse);
    }

    @GetMapping("/api/statistics/all")
    public ResponseEntity<StatisticResponse> globalStatistics() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        SecurityUser securityUser = auth.getName().equals("anonymousUser")
                ? null : (SecurityUser) auth.getPrincipal();
        if (settingsService.getOneSetting(GlobalSettingsCodes.STATISTICS_IS_PUBLIC) ||
                securityUser.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("user:moderate"))) {
            StatisticsInterface statisticsInterface = statisticsService.getGlobalStatistics();
            StatisticResponse statisticResponse = modelMapper.map(statisticsInterface, StatisticResponse.class);
            statisticResponse.setFirstPublication(statisticResponse.getFirstPublication() / 1000);
            return ResponseEntity.ok(statisticResponse);
        }
        return ResponseEntity.status(401).body(null);
    }
}
