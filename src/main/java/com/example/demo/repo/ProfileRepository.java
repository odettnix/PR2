package com.example.demo.repo;

import com.example.demo.models.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long>{
    List<Profile> findByUsername(String username);



    List<Profile> findByUsernameContains(String username);
}
