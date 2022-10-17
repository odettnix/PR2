package com.example.demo.repo;

import com.example.demo.models.Commentari;
import com.example.demo.models.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentariRepository extends CrudRepository<Commentari, Long> {

    List<Commentari> findByAuthor(String author);
    List<Commentari> findByAuthorContains(String author);
}
