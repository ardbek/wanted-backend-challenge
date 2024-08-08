package org.project.portfolio.domain.post.repository;

import org.project.portfolio.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
