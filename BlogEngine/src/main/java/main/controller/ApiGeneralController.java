package main.controller;

import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import main.api.response.TagResponse;
import main.dto.TagDTO;
import main.dto.TagInterface;
import main.repository.PostRepository;
import main.service.PostsService;
import main.service.SettingsService;
import main.service.TagsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PostsService postsService;

    @Autowired
    TagsService tagsService;

    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse) {
        this.settingsService = settingsService;
        this.initResponse = initResponse;
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
        int maxTagsCount = tagInterfaces.stream().map(TagInterface::getTagCount).max(Comparator.comparing(Integer::intValue)).get();
        TagResponse tagResponse = new TagResponse(
                tagInterfaces.stream().map(t -> convertTagToTagDTO(t,maxTagsCount)).collect(Collectors.toList()));

        return new ResponseEntity<>(tagResponse, HttpStatus.OK);
    }

    private TagDTO convertTagToTagDTO(TagInterface tagInterface, int maxCount) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setTag(tagInterface.getTag());
        double dWeightTag = (double) tagInterface.getTagCount() / postsService.getAllPostsCount();
        double tagWeight = dWeightTag * (1.0/ (double) maxCount / postsService.getAllPostsCount());
        tagDTO.setWeight(tagWeight);
        return tagDTO;
    }

}
