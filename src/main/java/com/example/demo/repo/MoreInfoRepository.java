package com.example.demo.repo;

import com.example.demo.models.MoreInfo;
import com.example.demo.models.Post;
import com.example.demo.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MoreInfoRepository extends CrudRepository<MoreInfo, Long> {
    MoreInfo findByEmail(String email);
    MoreInfo findByPhone(String phone);

    MoreInfo findByUser(User user);

}