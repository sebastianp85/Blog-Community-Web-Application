package me.code.blogapp.repositories;

import me.code.blogapp.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAccountUserNameContainingIgnoreCase(String userName);
}
