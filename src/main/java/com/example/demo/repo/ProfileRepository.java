package com.example.demo.repo;

import com.example.demo.models.Post;
import com.example.demo.models.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long>{
    List<Profile> findByNickAndSurnameContains(String nick, String surname);
    List<Profile> findByNickContains(String nick);
}
