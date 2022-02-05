package main.service;

import main.model.Post;
import main.model.Tag2Post;
import main.repository.Tag2PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Tag2PostService {

    private final Tag2PostRepository tag2PostRepository;
    private final TagsService tagsService;

    public Tag2PostService(Tag2PostRepository tag2PostRepository, TagsService tagsService) {
        this.tag2PostRepository = tag2PostRepository;
        this.tagsService = tagsService;
    }

    public List<Tag2Post> getRelationsByPostId(int id) {
        return tag2PostRepository.getRelationsById(id);
    }

    public void putRelations(List<String> tagNames, Post postId) {
        List<Tag2Post> relations = new ArrayList<>();
        tagsService.getTagsByNames(tagNames).forEach(id -> relations.add(new Tag2Post(postId,id)));
        tag2PostRepository.saveAll(relations);
    }

    public void overwritePostRelations(int postId, List<Tag2Post> relations){
        tag2PostRepository.deletePostRelations(postId);
        tag2PostRepository.saveAll(relations);
    }

}
