package main.service;

import main.dto.TagDTO;
import main.dto.TagInterface;
import main.repository.Tag2PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagsService {

    @Autowired
    Tag2PostRepository tag2PostRepository;

    public TagsService() {
    }

    public List<TagInterface> getTags(String query){
        List<TagInterface> tagInterfaces;
        if(query.isEmpty()){
            tagInterfaces = tag2PostRepository.getAllTags();
        }else
            tagInterfaces = tag2PostRepository.getTagsByQuery(query);
        return tagInterfaces;
    }


}
