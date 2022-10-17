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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String blogAdd(Model model)
    {
        return "blog-add";
    }



    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model)
    {
        Post post = new Post(title, anons, full_text);
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
    public String blogResults(@RequestParam String title, @RequestParam String anons, Model model)
    {
        List<Post> results = postRepository.findByTitleAndAnonsContains(title, anons);
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

    @PostMapping("/blog/profile")
    public String blogProfileAdd(@RequestParam String nick,
                                 @RequestParam String surname,
                                 @RequestParam String name,
                                 @RequestParam String patron,
                                 @RequestParam String about_me,
                                 @RequestParam int age,
                                 Model model)
    {
        Profile profile = new Profile(nick, surname, name, patron, about_me, age);
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

    @PostMapping("/blog/comments")
    public String blogCommentsAdd(@RequestParam String heading,
                                 @RequestParam String text_otz,
                                 @RequestParam String author,
                                 @RequestParam String date_otz,
                                 @RequestParam String mark,
                                 Model model)
    {
        Commentari commentari = new Commentari(heading, text_otz, author, date_otz, mark);
        commentariRepository.save(commentari);
//        model.addAttribute("heading", heading);
//        model.addAttribute("text_otz", text_otz);
//        model.addAttribute("author", author);
//        model.addAttribute("date_otz", date_otz);
//        model.addAttribute("mark", mark);


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
    public String blogResultsProfiles(@RequestParam String nick, @RequestParam String surname, Model model)
    {
        List<Profile> results = profileRepository.findByNickAndSurnameContains(nick, surname);
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
    public String blogResultsComments(@RequestParam String author, @RequestParam String heading, Model model)
    {
        List<Commentari> results = commentariRepository.findByAuthorAndHeadingContains(author, heading);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-commentsFilter";
    }








}
