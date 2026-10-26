package com.pji2918.springdeveloper.repository;

import com.pji2918.springdeveloper.dao.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
