package com.angrysurfer.social;

import com.angrysurfer.social.media.model.*;
import com.angrysurfer.social.media.model.Reaction.ReactionType;
import com.angrysurfer.social.media.repository.*;
import com.angrysurfer.social.media.service.ForumService;
import com.angrysurfer.social.media.service.PostService;
import com.angrysurfer.social.media.service.QuoteService;
import com.angrysurfer.social.media.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@SpringBootApplication
@ComponentScan(basePackages = "com.angrysurfer")
public class SocialApplication {

    static String sample1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque rutrum vitae tellus sit amet efficitur.";
    static String sample2 = "Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.";
    static String sample3 = "Nulla porttitor nisl in suscipit semper. Phasellus viverra dignissim mauris, efficitur commodo dui suscipit quis.";
    static String sample4 = "Integer quis leo aliquam risus semper laoreet. Fusce tincidunt malesuada nisi, a accumsan ipsum bibendum a.";
    static String sample5 = "Praesent ex nisl, fermentum vel feugiat id, placerat sit amet arcu.";
    static String sample6 = "Sed luctus sagittis tellus. Quisque et convallis lectus.";
    static String sample7 = "Sed malesuada et lectus quis egestas. Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
    @Autowired
    ReactionRepository reactionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostService postService;
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    ReactionService reactionService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    EditRepository editRepository;
    @Autowired
    ForumService forumService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    QuoteService quoteService;

    public static void main(String[] args) {
        SpringApplication.run(SocialApplication.class, args);
    }

    @Bean
    CommandLineRunner socialAppConfig() {

        return args -> {
            setupData();
        };
    }

    private void setupData() {

        try {
            User mark = new User();
            mark.setEmail("mpippins@gmail.com");
            mark.setAlias("Codex");
            // mark.setAvatarUrl(
            //                 "https://scontent-lga3-2.xx.fbcdn.net/v/t1.0-9/122096068_10157579198200060_6435561549815175270_n.jpg?_nc_cat=102&ccb=1-3&_nc_sid=09cbfe&_nc_ohc=i9wHzz0J-D0AX8jvurk&_nc_ht=scontent-lga3-2.xx&oh=2e301551c30008b4ee00cd968b670b44&oe=60781BF9");
            mark.setAvatarUrl(
                    "https://www.kindpng.com/picc/m/78-786207_user-avatar-png-user-avatar-icon-png-transparent.png");
            userRepository.save(mark);

            Profile markProfile = new Profile();
            markProfile.setUser(mark);
            markProfile.setFirstName("Mark");
            markProfile.setLastName("Pippins");
            markProfile.setProfileImageUrl("https://www.pinclipart.com/picdir/big/559-5594866_necktie-drawing-vector-round-avatar-user-icon-png.png");
            profileRepository.save(markProfile);

            User jeff = new User();
            jeff.setEmail("jjenkins@hotmail.com");
            jeff.setAlias("JJenk");
            jeff.setAvatarUrl(
                    "https://www.kindpng.com/picc/m/78-786207_user-avatar-png-user-avatar-icon-png-transparent.png");
            userRepository.save(jeff);

            Profile jeffProfile = new Profile();
            jeffProfile.setUser(jeff);
            jeffProfile.setFirstName("Jeff");
            jeffProfile.setLastName("Jenkins");
            jeffProfile.setProfileImageUrl(
                    "https://external-iad3-1.xx.fbcdn.net/safe_image.php?d=AQFV19STtu26m8ls&w=500&h=261&url=fbstaging%3A%2F%2Fgraph.facebook.com%2Fstaging_resources%2FMDEyMDExODc3NzY1NjI5MzMxOjE5NzI3NTg3OTk%3D&cfs=1&ext=jpg&_nc_cb=1&_nc_hash=AQGJcNye-QKxnJ2y");
            profileRepository.save(jeffProfile);

            User tammy = new User();
            tammy.setEmail("tbrown@hotmail.com");
            tammy.setAlias("Tammy23423");
            tammy.setAvatarUrl(
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSeIUUwf1GuV6YhA08a9haUQBOBRqJinQCJxA&usqp=CAU");
            userRepository.save(tammy);

            Profile tammyProfile = new Profile();
            tammyProfile.setUser(tammy);
            tammyProfile.setFirstName("Tammy");
            tammyProfile.setLastName("Brown");
            tammyProfile.setProfileImageUrl(
                    "https://scontent-iad3-1.xx.fbcdn.net/v/t1.0-9/124835072_10158716341183363_1735050267315457839_o.jpg?_nc_cat=102&ccb=1-3&_nc_sid=e3f864&_nc_ohc=U4zwIBXPmQEAX9x-5tZ&_nc_ht=scontent-iad3-1.xx&oh=0b4a2388c319e851140a3e6cf06aa0e9&oe=60782F77");
            profileRepository.save(tammyProfile);

            User john = new User();
            john.setEmail("johnsmith@hotmail.com");
            john.setAlias("SurfsUp");
            john.setAvatarUrl(
                    "data:image/png;base64,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");
            userRepository.save(john);

            Profile johnProfile = new Profile();
            johnProfile.setUser(john);
            johnProfile.setFirstName("John");
            johnProfile.setLastName("Smith");
            profileRepository.save(johnProfile);

            User henry = new User();
            henry.setEmail("henrywilson@hotmail.com");
            henry.setAlias("henrytravels");
            henry.setAvatarUrl(
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTWhzBr1cgYhOtxQTo6GvXzX6JIq39qYilUyQ&usqp=CAU");
            userRepository.save(henry);

            Profile henryProfile = new Profile();
            henryProfile.setUser(henry);
            henryProfile.setFirstName("Henry");
            henryProfile.setLastName("Wilson");
            profileRepository.save(henryProfile);

            Forum forum1 = new Forum();
            forum1.setName("Welcome");
            userRepository.findAll().forEach(forum1::addMember);
            forumService.save(forum1);

            Forum forum2 = new Forum("Skinny Puppy");
            forum2.getMembers().add(mark);
            forum2.getMembers().add(jeff);
            forumService.save(forum2);

            Post post1 = new Post();
            post1.setPostedBy(mark);
            post1.setText(sample1);
            // post1
            postRepository.save(post1);

            Comment comment1 = new Comment();
            comment1.setText(sample2);
            comment1.setPostedBy(henry);
            comment1.setPost(post1);
            comment1 = commentRepository.save(comment1);

            post1.getReplies().add(comment1);
            postRepository.save(post1);

            Reaction reaction1 = new Reaction();
            reaction1.setUser(john);
            reaction1.setReactionType(ReactionType.LIKE);
            reactionRepository.save(reaction1);

            Comment comment2 = new Comment();
            comment2.setText(sample3);
            comment2.setPostedBy(tammy);
            comment2.getReactions().add(reaction1);
            comment2.setParent(comment1);
            commentRepository.save(comment2);

            comment1.getReplies().add(comment2);
            commentRepository.save(comment1);

            Reaction reaction2 = new Reaction();
            reaction2.setUser(tammy);
            reaction2.setReactionType(ReactionType.LIKE);
            reactionRepository.save(reaction2);

            Reaction reaction3 = new Reaction();
            reaction3.setUser(jeff);
            reaction3.setReactionType(ReactionType.LIKE);
            reactionRepository.save(reaction3);

            Post post2 = new Post();
            post2.setPostedBy(henry);
            post2.setText(sample4);
            post2.getReactions().add(reaction2);
            post2.getReactions().add(reaction3);
            postRepository.save(post2);

            Post post3 = new Post();
            post3.setPostedBy(jeff);
            post3.setText(sample5);
            postRepository.save(post3);

            Comment comment3 = new Comment();
            comment3.setText(sample6);
            comment3.setPostedBy(tammy);
            commentRepository.save(comment3);

            post3.getReplies().add(comment3);
            postRepository.save(post3);

            Post post4 = new Post();
            post4.setPostedBy(mark);
            post4.setPostedTo(mark);
            post4.setText(sample7);
            postRepository.save(post4);


            Post post5 = new Post();
            post5.setPostedBy(tammy);
            post5.setPostedTo(tammy);
            post5.setText("Hi. I'm Tammy.");
            postRepository.save(post5);

            Comment comment4 = new Comment();
            comment4.setText("Hi, Tammy. I'm Henry.");
            comment4.setPostedBy(henry);
            comment4.setPost(post1);
            comment4 = commentRepository.save(comment4);

            post5.getReplies().add(comment4);
            postRepository.save(post5);

            Reaction reaction4 = new Reaction();
            reaction4.setUser(tammy);
            reaction4.setReactionType(ReactionType.LIKE);
            reactionRepository.save(reaction4);

            comment4.getReactions().add(reaction4);
            commentRepository.save(comment4);

            List<String> quotes = quoteService.getQuotes();
            quotes.forEach(quote -> {
                Post quoted = new Post();
                quoted.setText(quote);
                quoted.setPostedBy(mark);
                quoted.setPostedTo(mark);
                quoted = postRepository.save(quoted);
            });

            // Reaction reaction5 = new Reaction();
            // reaction5.setUser(john);
            // reaction5.setReactionType(ReactionType.LIKE);
            // reactionRepository.save(reaction5);

            // Comment comment5 = new Comment();
            // comment5.setText("How come you guys aren't talking in latin?");
            // comment5.setPostedBy(jeff);s
            // comment5.getReactions().add(reaction5);
            // comment5.setParent(comment4);
            // commentRepository.save(comment5);

            // comment4.getReplies().add(comment5);
            // commentRepository.save(comment4);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
