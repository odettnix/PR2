package com.example.demo.controllers;

import com.example.demo.models.MoreInfo;
import com.example.demo.models.Profile;
import com.example.demo.models.User;
import com.example.demo.repo.MoreInfoRepository;
import com.example.demo.repo.ProfileRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MoreInfoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MoreInfoRepository moreInfoRepository;


    @GetMapping("/contacts/add/{id}")
    public String goAddPage(@PathVariable(value = "id") long id, Model model)
    {
        User user = userRepository.findById(id).get();

        MoreInfo moreInfo = new MoreInfo();
        moreInfo.setUser(user);
        model.addAttribute("moreInfo", moreInfo);

        return "blog-moreinfo-add";
    }

    @PostMapping("/contacts/create")
    public String createMoreInfo(@ModelAttribute("moreInfo") @Valid MoreInfo moreinfo,
                                 BindingResult bindingResult,
                                 Model model)
    {
        Boolean haveErrors = false;

        if(moreInfoRepository.findByEmail(moreinfo.getEmail()) != null && !moreInfoRepository.findByEmail(moreinfo.getEmail()).getId().equals(moreinfo.getId())){
            model.addAttribute("email_errors", "Данный email уже существует");
            haveErrors = true;
        }

        if(moreInfoRepository.findByPhone(moreinfo.getPhone()) != null && !moreInfoRepository.findByPhone(moreinfo.getPhone()).getId().equals(moreinfo.getId())){
            model.addAttribute("phoneNumber_errors", "Данный телефон уже существует");
            haveErrors = true;
        }



        if(bindingResult.hasErrors() || haveErrors) {
            return "blog-moreinfo-add";
        }

        moreInfoRepository.save(moreinfo);

        moreinfo.getUser().setMoreInfo(moreinfo);


        userRepository.save(moreinfo.getUser());

        return "redirect:/blog/profile";
    }

}
