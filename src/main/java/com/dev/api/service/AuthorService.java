package com.dev.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;

import com.dev.api.entity.Author;

public interface AuthorService {
	
	Author findByIdOrElseThrow(@NonNull Long id);

    Author save(@NonNull Author author);

    Page<Author> findAll(@NonNull PageRequest pageable);

    void deleteByIdOrElseThrow(@NonNull Long id);

}
