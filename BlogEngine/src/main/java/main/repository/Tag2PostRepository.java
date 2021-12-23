package main.repository;

import main.dto.TagInterface;
import main.model.Tag2Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Tag2PostRepository extends CrudRepository<Tag2Post, Integer> {



}
