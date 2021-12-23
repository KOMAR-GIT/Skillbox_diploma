package main.service;

import main.api.response.TagResponse;
import main.dto.TagInterface;
import main.repository.Tag2PostRepository;
import main.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {

    @Autowired
    TagRepository tagRepository;

    public TagsService() {
    }

    public List<TagInterface> getTags(String query){
        List<TagInterface> tagInterfaces;
        if(query.isEmpty()){
            tagInterfaces = tagRepository.getAllTags();
        }else
            tagInterfaces = tagRepository.getTagsByQuery(query);
        return tagInterfaces;
    }


}
