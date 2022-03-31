package main.service;

import main.dto.interfaces.TagInterface;
import main.model.Tag;
import main.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagsService {

    private final TagRepository tagRepository;

    public TagsService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagInterface> getTagsByQuery(String query) {
        List<TagInterface> tagInterfaces;
        if (query.isEmpty()) {
            tagInterfaces = tagRepository.getAllTags();
        } else {
            tagInterfaces = tagRepository.getTagsByQuery(query);
        }

        return tagInterfaces;
    }

    public void addTags(List<String> tags) {
        List<String> existsTagList = getTagsByNames(tags).stream().map(Tag::getName).collect(Collectors.toList());
        List<Tag> newTags = new ArrayList<>();
        for (String tag : tags) {
            if (!existsTagList.contains(tag.toUpperCase())) {
                newTags.add(new Tag(tag.toUpperCase()));
            }
        }

        if (!newTags.isEmpty()) {
            tagRepository.saveAll(newTags);
        }
    }

    public List<String> getPostTags(int postId) {
        return tagRepository.getPostTags(postId);
    }

    public List<Tag> getTagsByNames(List<String> tagNames) {
        return tagRepository.findTagsByNameIn(tagNames);
    }

}
