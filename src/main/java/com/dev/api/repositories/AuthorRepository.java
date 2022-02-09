package com.dev.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.api.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
