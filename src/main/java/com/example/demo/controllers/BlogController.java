package com.example.demo.controllers;

//import com.example.demo.models.Comments;
import com.example.demo.models.*;
//import com.example.demo.repo.CommentsRepository;
import com.example.demo.repo.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
@Controller
public class BlogController  {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CommentariRepository commentariRepository;

    @Autowired
    private UserRepository userRepository;
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
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("profile", users);

        return "blog-add";
    }

    public String crntUsNm()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }

        return null;
    }


    @PostMapping("/blog/add")
    public String blogPostAdd(@ModelAttribute ("post") @Valid Post postValid, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors()){
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("profile", users);
            return "blog-add";
        }
        Post post = new Post(postValid.getTitle(), postValid.getAnons(), postValid.getFull_text(), postValid.getUser());
        postRepository.save(post);
        return "redirect:/";
    }

    ///
    @Autowired
    private MoreInfoRepository moreInfoRepository;
    @GetMapping("/blog/MoreInfo")
    public String blogMoreInfo(Model model)
    {
        Iterable<MoreInfo> moreInfos = moreInfoRepository.findAll();
        model.addAttribute("moreinfo", moreInfos);
        return "blog-moreinfo";
    }

    @GetMapping("/blog/MoreInfoadd")
    public String blogMoreInfoAdd(MoreInfo moreInfo, Model model)
    {
        Iterable<User> user = userRepository.findAll();
        model.addAttribute("user", user);

        return "blog-moreinfo-add";
    }


    @PostMapping("/blog/MoreInfoadd")
    public String blogMoreInfoAdd(@ModelAttribute ("moreInfo") @Valid MoreInfo moreInfoValid, BindingResult bindingResult, Model model)
    {
        Boolean haveErrors = false;

        if(moreInfoRepository.findByEmail(moreInfoValid.getEmail()) != null && !moreInfoRepository.findByEmail(moreInfoValid.getEmail()).getId().equals(moreInfoValid.getId())){
            model.addAttribute("email_error", "Данный email уже существует");
            haveErrors = true;
        }

        if(moreInfoRepository.findByPhone(moreInfoValid.getPhone()) != null && !moreInfoRepository.findByPhone(moreInfoValid.getPhone()).getId().equals(moreInfoValid.getId())){
            model.addAttribute("phone_error", "Данный телефон уже существует");
            haveErrors = true;
        }


        if(moreInfoRepository.findByUser(moreInfoValid.getUser()) != null && !moreInfoRepository.findByUser(moreInfoValid.getUser()).getId().equals(moreInfoValid.getId())){
            model.addAttribute("user_error", "У данного пользователя уже есть контакты");
            haveErrors = true;
        }


        if(bindingResult.hasErrors() || haveErrors){
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("user", users);
            return "blog-moreinfo-add";
        }
        MoreInfo moreInfos = new MoreInfo(moreInfoValid.getEmail(), moreInfoValid.getPhone(), moreInfoValid.getUser());
        moreInfoRepository.save(moreInfos);
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
        User user = userRepository.findByUsername(crntUsNm());

//        Iterable<Profile> profiles = profileRepository.findAll();
        model.addAttribute("profiles", user);
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
//        profileRepository.save(profile);
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

    ////////////////////////////
    @GetMapping("/blog/Cardadd")
    public String blogCardAdd(Model model)
    {
        Iterable<User> students = userRepository.findAll();
        model.addAttribute("profile", students);
        Iterable<Post> universities = postRepository.findAll();
        model.addAttribute("post", universities);
        return "blog-editCard";
    }

    @GetMapping("/blog/Card")
    public String CardShow(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("profile", users);
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        model.addAttribute("profile", users);
        return "blog-card";
    }

    @PostMapping("/blog/Carddd")
    public String blogCardAdd(@RequestParam long user_id, @RequestParam long post_id, Model model)
    {
        User user1 = userRepository.findById(user_id).get();
        Post post1 = postRepository.findById(post_id).get();

        post1.getUsers().add(user1);

        postRepository.save(post1);

        return "redirect:/";
    }



    //////////////////////////
    @GetMapping("/blog/filerProfile")
    public String blogFilterProfile(Model model)
    {
        return "blog-profilesFilter";
    }

    @PostMapping("/blog/filter/profile")
    public String blogResultProfile(@RequestParam String username, Model model)
    {
        List<Profile> results = profileRepository.findByUsernameContains(username);
        model.addAttribute("results", results);
//        List<Post> result = postRepository.findByTitleContains(title);
//        model.addAttribute("result", result);
        return "blog-profilesFilter";
    }

    @PostMapping("/blog/filter/profiles")
    public String blogResultsProfiles(@RequestParam String username, Model model)
    {
        List<Profile> results = profileRepository.findByUsername(username);
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

    //    @GetMapping("/blog/MoreInfoadd")
//    public String blogMoreInfoAdd(MoreInfo moreInfo, Model model)
//    {
//        Iterable<Profile> profiles = profileRepository.findAll();
//        model.addAttribute("profile", profiles);
//
//        return "blog-moreinfo-add";
//    }

//    @PostMapping("/blog/MoreInfoadd")
//    public String blogMoreInfoAdd(@ModelAttribute ("moreInfo") @Valid MoreInfo moreInfoValid, BindingResult bindingResult, Model model)
//    {
//        if(bindingResult.hasErrors()){
//            Iterable<Profile> profiles = profileRepository.findAll();
//            model.addAttribute("profile", profiles);
//            return "blog-moreinfo-add";
//        }
//        MoreInfo moreInfos = new MoreInfo(moreInfoValid.getEmail(), moreInfoValid.getPhone(), moreInfoValid.getHomephone(), moreInfoValid.getProfile());
//        moreInfoRepository.save(moreInfos);
//        return "redirect:/";
//    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Post posts, Model model)
    {
        if(!postRepository.existsById(id)){
            return "redirect:/blog";
        }
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("profile", users);
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
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("profile", users);
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

    ///////////
    @GetMapping("/blog/Card/{profile_id}/{post_id}")
    public String blogBlogCardDelete(@PathVariable Long post_id, @PathVariable Long profile_id,
                                 Model model)
    {
        User user = userRepository.findById(profile_id).orElseThrow();
        Post post = postRepository.findById(post_id).orElseThrow();
        user.getPosts().remove(post);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/blog/Card/{user_id}/{post_id}/edit")
    public String blogEditCard(@PathVariable long user_id, Model model)
    {
        if(!userRepository.existsById(user_id)){
            return "redirect:/blog";
        }


        Iterable<User> users = userRepository.findAll();
        model.addAttribute("user", users);
        Iterable<Post> post = postRepository.findAll();
        model.addAttribute("post", post);
        Optional<User> profile1 = userRepository.findById(user_id);
/*        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);*/
        model.addAttribute("user", profile1.get());

        return "card-edit";
    }

    @PostMapping("/blog/Card/{user_id}/{post_id}/edit")
    public String blogPostUpdateCard(@RequestParam long user_id, @RequestParam long post_id, Model model)
    {
        User user1 = userRepository.findById(user_id).orElseThrow();
        Post posts1 = postRepository.findById(post_id).orElseThrow();
        user1.getPosts().remove(posts1);
        userRepository.save(user1);

        User user2 = userRepository.findById(user_id).get();
        Post post1 = postRepository.findById(post_id).get();

        post1.getUsers().add(user1);

        postRepository.save(post1);

//        user1.getPosts().add(posts1);
//        postRepository.save(posts1);
        return "redirect:/";
    }


    ////////////////////////////////////

    @GetMapping("/blog/MoreInfo/{id}")
    public String blogMoreInfoDetails(@PathVariable(value = "id") long id, Model model)
    {
        Optional<MoreInfo> moreInfo = moreInfoRepository.findById(id);
        ArrayList<MoreInfo> res = new ArrayList<>();
        moreInfo.ifPresent(res::add);
        model.addAttribute("moreinfo", res);
        if(!moreInfoRepository.existsById(id)){
            return "redirect:/blog";
        }
        return "blog-detailsMoreInfo";
    }

//    @GetMapping("/blog/MoreInfoadd")
//    public String blogMoreInfoAdd(MoreInfo moreInfo, Model model)
//    {
//        Iterable<Profile> profiles = profileRepository.findAll();
//        model.addAttribute("profile", profiles);
//
//        return "blog-moreinfo-add";
//    }

//    @PostMapping("/blog/MoreInfoadd")
//    public String blogMoreInfoAdd(@ModelAttribute ("moreInfo") @Valid MoreInfo moreInfoValid, BindingResult bindingResult, Model model)
//    {
//        if(bindingResult.hasErrors()){
//            Iterable<Profile> profiles = profileRepository.findAll();
//            model.addAttribute("profile", profiles);
//            return "blog-moreinfo-add";
//        }
//        MoreInfo moreInfos = new MoreInfo(moreInfoValid.getEmail(), moreInfoValid.getPhone(), moreInfoValid.getHomephone(), moreInfoValid.getProfile());
//        moreInfoRepository.save(moreInfos);
//        return "redirect:/";
//    }

    @GetMapping("/blog/MoreInfo/{id}/edit")
    public String blogMoreInfoEdit(@PathVariable(value = "id") long id, MoreInfo moreInfo, Model model)
    {
        if(!moreInfoRepository.existsById(id)){
            return "redirect:/blog";
        }
        Iterable<Profile> profiles = profileRepository.findAll();
        model.addAttribute("profile", profiles);
        Optional<MoreInfo> moreInfos = moreInfoRepository.findById(id);
/*        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);*/
        model.addAttribute("moreInfo", moreInfos.get());

        return "blog-editMoreInfo";
    }

    @PostMapping("/blog/MoreInfo/{id}/edit")
    public String blogMoreInfoUpdate(@ModelAttribute ("moreinfo") @Valid MoreInfo moreInfoValid, BindingResult bindingResult,
                                 Model model)
    {
        if(bindingResult.hasErrors()){
            Iterable<Profile> profiles = profileRepository.findAll();
            model.addAttribute("profile", profiles);
            return "blog-editMoreInfo";
        }
        moreInfoRepository.save(moreInfoValid);
        return "redirect:/";
    }



    @PostMapping("/blog/MoreInfo/{id}/remove")
    public String blogBlogDeleteMoreInfo(@PathVariable(value = "id") long id,
                                        Model model)
    {
        MoreInfo moreInfo = moreInfoRepository.findById(id).orElseThrow();
        moreInfoRepository.delete(moreInfo);
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
    public String blogEditProfile(@PathVariable(value = "id") long id, User users, Model model)
    {
        if(!userRepository.existsById(id)){
            return "redirect:/";
        }
        Optional<User> user = userRepository.findById(id);
        model.addAttribute("user", user.get());

        return "blog-editProfile";
    }


    @PostMapping("/blog/Profile/{id}/edit")
    public String blogPostUpdateProfile(@ModelAttribute ("user") @Valid User user, BindingResult bindingResult)
    {

        if(bindingResult.hasErrors()){
            return "blog-editProfile";
        }
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
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
