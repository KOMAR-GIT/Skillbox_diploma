package main.controller;

import main.api.response.CalendarResponse;
import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import main.api.response.TagResponse;
import main.dto.TagDto;
import main.dto.interfaces.TagInterface;
import main.service.CalendarService;
import main.service.SettingsService;
import main.service.TagsService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiGeneralController {

    private final SettingsService settingsService;

    private final InitResponse initResponse;

    private final ModelMapper modelMapper;

    private final TagsService tagsService;

    private final CalendarService calendarService;

    public ApiGeneralController(SettingsService settingsService,
                                InitResponse initResponse,
                                ModelMapper modelMapper,
                                TagsService tagsService,
                                CalendarService calendarService)
    {
        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.modelMapper = modelMapper;
        this.tagsService = tagsService;
        this.calendarService = calendarService;
    }


    @GetMapping("/api/settings")
    public SettingsResponse settings() {
        return settingsService.getAllGlobalSettings();
    }

    @GetMapping("/api/init")
    public InitResponse init() {
        return initResponse;
    }

    @GetMapping("/api/tag")
    public ResponseEntity<TagResponse> tags(@RequestParam(value = "query", defaultValue = "") String query) {
        List<TagInterface> tagInterfaces = tagsService.getTagsByQuery(query);
        TagResponse tagResponse = new TagResponse(
                tagInterfaces.stream().map(this::convertTagToTagDTO).collect(Collectors.toList()));

        return new ResponseEntity<>(tagResponse, HttpStatus.OK);
    }

    @GetMapping("/api/calendar")
    public ResponseEntity<CalendarResponse> calendar(@RequestParam(value = "year", defaultValue = "") String year){
        CalendarResponse calendarResponse = new CalendarResponse(calendarService.getYears() , calendarService.getPostsCountByYear(year));
        return new ResponseEntity<>(calendarResponse, HttpStatus.OK);
    }


    private TagDto convertTagToTagDTO(TagInterface tagInterface) {
        return modelMapper.map(tagInterface, TagDto.class);
    }

}
