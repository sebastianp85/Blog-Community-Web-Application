package me.code.blogapp.services;

import lombok.AllArgsConstructor;
import me.code.blogapp.models.Account;
import me.code.blogapp.models.Post;
import me.code.blogapp.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AccountService accountService;
    private final ValidationService validationService;

    public Optional<Post> getById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public void save(Post post) {
        validationService.validatePostContent(post.getBody());
        validationService.validatePostContent(post.getTitle());

        if (post.getId() == null) {
            post.setCreatedAt(LocalDateTime.now());
        }
        postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public List<Post> searchPosts(String userName) {
        validationService.validatePostContent(userName);
        return postRepository.findByAccountUserNameContainingIgnoreCase(userName);
    }

    public void createOrUpdatePost(Post post, Principal principal) {
        String authUsername = "anonymousUser";
        if (principal != null) {
            authUsername = principal.getName();
        }

        Optional<Account> optionalAccount = accountService.findByEmail(authUsername);

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            post.setAccount(account);
        } else {
            Account temporaryAccount = new Account(authUsername, authUsername + "@temp.com", "tempPassword");
            accountService.save(temporaryAccount);
            post.setAccount(temporaryAccount);
        }

        save(post);
    }

    public void updatePost(Long id, Post updatedPost) {
        Optional<Post> optionalPost = getById(id);
        optionalPost.ifPresent(existingPost -> {
            existingPost.setTitle(updatedPost.getTitle());
            existingPost.setBody(updatedPost.getBody());
            save(existingPost);
        });
    }

    public void deletePost(Long id) {
        getById(id).ifPresent(this::delete);
    }
}