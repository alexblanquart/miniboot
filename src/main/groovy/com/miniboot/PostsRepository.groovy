package com.miniboot

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository;;

interface PostsRepository extends JpaRepository<Post, Long> {

	List<Post> findAllByCategory(String category, Pageable pageable)
}
