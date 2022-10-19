package com.example.demo.controllers;

//import com.example.demo.models.Comments;
import com.example.demo.models.Commentari;
import com.example.demo.models.Profile;
//import com.example.demo.repo.CommentsRepository;
import com.example.demo.repo.CommentariRepository;
import com.example.demo.repo.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repo.PostRepository;
import com.example.demo.models.Post;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class BlogController  {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CommentariRepository commentariRepository;
    @GetMapping("/")
    public String blogMain(Model model)
    {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

   @GetMapping("/blog/add")
    public String blogAdd(Post post, Model model)
    {
        return "blog-add";
    }



//    @PostMapping("/blog/add")
//    public String blogPostAdd(@RequestParam String title,
//                              @RequestParam String anons,
//                              @RequestParam String full_text, Model model)
//    {
//        Post post = new Post(title, anons, full_text);
//        postRepository.save(post);
//        return "redirect:/";
//    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@ModelAttribute ("post") @Valid Post post, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "blog-add";
        }
        postRepository.save(post);
        return "redirect:/";
    }

    @GetMapping("/blog/filter")
    public String blogFilter(Model model)
    {
        return "blog-filter";
    }

    @PostMapping("/blog/filter/result")
    public String blogResult(@RequestParam String title, Model model)
    {
        List<Post> results = postRepository.findByTitleContains(title);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-filter";
    }

    @PostMapping("/blog/filter/results")
    public String blogResults(@RequestParam String title, Model model)
    {
        List<Post> results = postRepository.findByTitle(title);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-filter";
    }



    //////////////////////


    @GetMapping("/blog/profile")
    public String blogProfile(Model model)
    {

        Iterable<Profile> profiles = profileRepository.findAll();
        model.addAttribute("profiles", profiles);
        return "blog-profile";
    }

    @GetMapping("/blog/ProfileAdd")
    public String blogProfileAdd(Profile profile, Model model)
    {

        return "blog-profileAdd";
    }

//    @PostMapping("/blog/ProfileAdd")
//    public String blogProfileAdd(@RequestParam String nick,
//                                 @RequestParam String name,
//                                 @RequestParam Date data_reg,
//                                 @RequestParam char gender,
//                                 @RequestParam int age,
//                                 Model model)
//    {
//        Profile profile = new Profile(nick, name, data_reg, gender, age);
//        profileRepository.save(profile);
//
//        return "redirect:/";
//    }

    @PostMapping("/blog/ProfileAdd")
    public String blogProfileAdd(@ModelAttribute ("profile") @Valid Profile profile, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "blog-profileAdd";
        }
        profileRepository.save(profile);
        return "redirect:/";

    }

    /////////////////////////

    @GetMapping("/blog/comments")
    public String blogComments(Model model)
    {
        Iterable<Commentari> comments = commentariRepository.findAll();
        model.addAttribute("comments", comments);
        return "blog-comments";
    }

    @GetMapping("/blog/commentsAdd")
    public String blogCommentsAdd(Commentari commentari, Model model)
    {

        return "blog-commentsAdd";
    }

    @PostMapping("/blog/commentsAdd")
    public String blogCommentsAdd(@ModelAttribute ("commentari") @Valid Commentari commentari, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()){
            return "blog-commentsAdd";
        }
        commentariRepository.save(commentari);
        return "redirect:/";
    }

    //////////////////////////
    @GetMapping("/blog/filerProfile")
    public String blogFilterProfile(Model model)
    {
        return "blog-profilesFilter";
    }

    @PostMapping("/blog/filter/profile")
    public String blogResultProfile(@RequestParam String nick, Model model)
    {
        List<Profile> results = profileRepository.findByNickContains(nick);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-profilesFilter";
    }

    @PostMapping("/blog/filter/profiles")
    public String blogResultsProfiles(@RequestParam String nick, Model model)
    {
        List<Profile> results = profileRepository.findByNick(nick);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-profilesFilter";
    }
    //////////////////
    @GetMapping("/blog/filerComments")
    public String blogFilterComments(Model model)
    {
        return "blog-commentsFilter";
    }

    @PostMapping("/blog/filter/comment")
    public String blogResultComments(@RequestParam String author, Model model)
    {
        List<Commentari> results = commentariRepository.findByAuthorContains(author);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-commentsFilter";
    }

    @PostMapping("/blog/filter/comments")
    public String blogResultsComments(@RequestParam String author, Model model)
    {
        List<Commentari> results = commentariRepository.findByAuthor(author);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-commentsFilter";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Post posts, Model model)
    {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
/*        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);*/
        model.addAttribute("post", post.get());

        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@ModelAttribute ("post") @Valid Post posts, BindingResult bindingResult,
                                 Model model)
    {
        if(bindingResult.hasErrors()){
            return "blog-edit";
        }
        postRepository.save(posts);
        return "redirect:/";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogBlogDelete(@PathVariable(value = "id") long id,
                                 Model model)
    {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/";
    }

    ///////////////////////////////////
    @GetMapping("/blog/Profile/{id}")
    public String blogDetailsProfile(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Profile> profiles = profileRepository.findById(id);
        ArrayList<Profile> res = new ArrayList<>();
        profiles.ifPresent(res::add);
        model.addAttribute("profile", res);
        if(!profileRepository.existsById(id)){
            return "redirect:/blog";
        }
        return "blog-detailsProfile";
    }

    @GetMapping("/blog/Profile/{id}/edit")
    public String blogEditProfile(@PathVariable(value = "id") long id, Profile profiles, Model model)
    {
        if(!profileRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Profile> profile = profileRepository.findById(id);
//        ArrayList<Profile> res = new ArrayList<>();
//        profile.ifPresent(res::add);
        model.addAttribute("profile", profile.get());

        return "blog-editProfile";
    }

//    @PostMapping("/blog/Profile/{id}/edit")
//    public String blogPostUpdateProfile(@PathVariable(value = "id") long id,
//                                        @RequestParam String nick,
//                                        @RequestParam String name,
//                                        @RequestParam Date data_reg,
//                                        @RequestParam char gender,
//                                        @RequestParam int age,
//                                        Model model)
//    {
//        Profile profile = profileRepository.findById(id).orElseThrow();
//        profile.setNick(nick);
//        profile.setName(name);
//        profile.setData_reg(data_reg);
//        profile.setGender(gender);
//        profile.setAge(age);
//        profileRepository.save(profile);
//        return "redirect:/";
//    }

    @PostMapping("/blog/Profile/{id}/edit")
    public String blogPostUpdateProfile(@ModelAttribute ("profile") @Valid Profile profile, BindingResult bindingResult)
    {

        if(bindingResult.hasErrors()){
            return "blog-editProfile";
        }
        profileRepository.save(profile);
        return "redirect:/";
    }

    @PostMapping("/blog/Profile/{id}/remove")
    public String blogBlogDeleteProfile(@PathVariable(value = "id") long id,
                                 Model model)
    {
        Profile profile = profileRepository.findById(id).orElseThrow();
        profileRepository.delete(profile);
        return "redirect:/";
    }
    ////////////////////////////////////////////
    @GetMapping("/blog/Comments/{id}")
    public String blogDetailsComments(@PathVariable(value = "id") long id, Model model)
    {
        Optional<Commentari> comments = commentariRepository.findById(id);
        ArrayList<Commentari> res = new ArrayList<>();
        comments.ifPresent(res::add);
        model.addAttribute("comments", res);
        if(!commentariRepository.existsById(id)){
            return "redirect:/blog";
        }
        return "blog-detailsComments";
    }

    @GetMapping("/blog/Comments/{id}/edit")
    public String blogEditProfileComments(@PathVariable(value = "id") long id, Commentari comments,  Model model)
    {
        if(!commentariRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Commentari> commentari = commentariRepository.findById(id);
//        ArrayList<Commentari> res = new ArrayList<>();
//        commentari.ifPresent(res::add);
        model.addAttribute("comments", commentari.get());

        return "blog-editComments";
    }

    @PostMapping("/blog/Comments/{id}/edit")
    public String blogPostUpdateComments(@ModelAttribute ("comments") @Valid Commentari comments, BindingResult bindingResult)
    {


        if(bindingResult.hasErrors()){
            return "blog-editComments";
        }
        commentariRepository.save(comments);
        return "redirect:/";
    }

    @PostMapping("/blog/Comments/{id}/remove")
    public String blogBlogDeleteComments(@PathVariable(value = "id") long id,
                                        Model model)
    {
        Commentari commentari = commentariRepository.findById(id).orElseThrow();
        commentariRepository.delete(commentari);
        return "redirect:/";
    }







}
