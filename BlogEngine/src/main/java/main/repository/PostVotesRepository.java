package main.repository;

import main.model.Post;
import main.model.PostVotes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVotesRepository extends CrudRepository<PostVotes, Integer> {

}
