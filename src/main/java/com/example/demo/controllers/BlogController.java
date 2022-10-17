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
    public String blogProfileAdd(Model model)
    {

        return "blog-profileAdd";
    }

    @PostMapping("/blog/ProfileAdd")
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

    @GetMapping("/blog/commentsAdd")
    public String blogCommentsAdd(Model model)
    {

        return "blog-commentsAdd";
    }

    @PostMapping("/blog/commentsAdd")
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
    public String blogResultsComments(@RequestParam String author, @RequestParam String heading, Model model)
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
    public String blogEdit(@PathVariable(value = "id") long id, Model model)
    {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);

        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String full_text,
                                 Model model)
    {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
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
    public String blogEditProfile(@PathVariable(value = "id") long id, Model model)
    {
        if(!profileRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Profile> post = profileRepository.findById(id);
        ArrayList<Profile> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("profile", res);

        return "blog-editProfile";
    }

    @PostMapping("/blog/Profile/{id}/edit")
    public String blogPostUpdateProfile(@PathVariable(value = "id") long id,
                                        @RequestParam String nick,
                                        @RequestParam String surname,
                                        @RequestParam String name,
                                        @RequestParam String patron,
                                        @RequestParam String about_me,
                                        @RequestParam int age,
                                        Model model)
    {
        Profile profile = profileRepository.findById(id).orElseThrow();
        profile.setNick(nick);
        profile.setSurname(surname);
        profile.setName(name);
        profile.setPatron(patron);
        profile.setAbout_me(about_me);
        profile.setAge(age);
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
    public String blogEditProfileComments(@PathVariable(value = "id") long id, Model model)
    {
        if(!commentariRepository.existsById(id)){
            return "redirect:/blog";
        }
        Optional<Commentari> commentari = commentariRepository.findById(id);
        ArrayList<Commentari> res = new ArrayList<>();
        commentari.ifPresent(res::add);
        model.addAttribute("comments", res);

        return "blog-editComments";
    }

    @PostMapping("/blog/Comments/{id}/edit")
    public String blogPostUpdateComments(@PathVariable(value = "id") long id,
                                         @RequestParam String heading,
                                         @RequestParam String text_otz,
                                         @RequestParam String author,
                                         @RequestParam String date_otz,
                                         @RequestParam String mark,
                                        Model model)
    {
        Commentari commentari = commentariRepository.findById(id).orElseThrow();
        commentari.setHeading(heading);
        commentari.setText_otz(text_otz);
        commentari.setAuthor(author);
        commentari.setDate_otz(date_otz);
        commentari.setMark(mark);
        commentariRepository.save(commentari);
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
