package main.controller;

import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import main.api.response.TagResponse;
import main.dto.TagDTO;
import main.dto.TagInterface;
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

    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse, ModelMapper modelMapper, TagsService tagsService) {
        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.modelMapper = modelMapper;
        this.tagsService = tagsService;
    }


    @GetMapping("/api/settings")
    private SettingsResponse settings() {
        return settingsService.getGlobalSettings();
    }

    @GetMapping("/api/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/api/tag")
    private ResponseEntity<TagResponse> tags(@RequestParam(value = "query", defaultValue = "") String query) {
        List<TagInterface> tagInterfaces = tagsService.getTags(query);
        TagResponse tagResponse = new TagResponse(
                tagInterfaces.stream().map(this::convertTagToTagDTO).collect(Collectors.toList()));

        return new ResponseEntity<>(tagResponse, HttpStatus.OK);
    }

    private TagDTO convertTagToTagDTO(TagInterface tagInterface) {
        return modelMapper.map(tagInterface, TagDTO.class);
    }

}
