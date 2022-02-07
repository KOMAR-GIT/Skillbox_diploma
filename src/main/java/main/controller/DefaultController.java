package main.controller;

import main.api.response.ResponseWithErrors;
import main.repository.TagRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class DefaultController {

    @Value("${upload.path}")
    private String uploadPath;

    public DefaultController(TagRepository tagRepository) {

    }

    @RequestMapping("/")
    public String index()
    {
        return "index";
    }


    @PostMapping("/api/image")
    public ResponseEntity<ResponseWithErrors> uploadImage(@RequestParam("image")MultipartFile file){

        if(file != null){
            file.getName();
        }

        ResponseWithErrors responseWithErrors = new ResponseWithErrors();
        return ResponseEntity.ok(responseWithErrors);
    }



}
