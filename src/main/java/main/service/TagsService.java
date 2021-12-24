package main.service;

import main.dto.TagInterface;
import main.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {

    private final TagRepository tagRepository;

    public TagsService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagInterface> getTags(String query) {
        List<TagInterface> tagInterfaces;
        if (query.isEmpty()) {
            tagInterfaces = tagRepository.getAllTags();
        } else {
            tagInterfaces = tagRepository.getTagsByQuery(query);
        }
        return tagInterfaces;
    }


}
