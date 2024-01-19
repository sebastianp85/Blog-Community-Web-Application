package me.code.blogapp.config;

import lombok.AllArgsConstructor;
import me.code.blogapp.models.Account;
import me.code.blogapp.models.Authority;
import me.code.blogapp.models.Post;
import me.code.blogapp.repositories.AuthorityRepository;
import me.code.blogapp.services.AccountService;
import me.code.blogapp.services.PostService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class Data implements CommandLineRunner {

    private final PostService postService;
    private final AccountService accountService;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) {
        List<Post> posts = postService.getAll();

        if (posts.isEmpty()) {
            Authority user = authorityRepository.save(new Authority("ROLE_USER"));
            Authority admin = authorityRepository.save(new Authority("ROLE_ADMIN"));

            String userPassword = System.getenv("userPassword");
            String adminPassword = System.getenv("adminPassword");
            String adminEmail = System.getenv("adminEmail");
            String userEmail = System.getenv("userEmail");

            Account account1 = new Account("user", userEmail, userPassword);
            account1.setAuthorities(Set.of(user));
            accountService.save(account1);

            Account account2 = new Account("admin", adminEmail, adminPassword);
            account2.setAuthorities(Set.of(admin));
            accountService.save(account2);

            Post post1 = new Post("The first title here", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", account1);
            Post post2 = new Post("Dignissimos", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.", account2);

            postService.save(post1);
            postService.save(post2);
        }
    }
}
