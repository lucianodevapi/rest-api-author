package com.dev.api.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.api.dto.AuthorUpdateDto;
import com.dev.api.entity.Author;
import com.dev.api.entity.persist.AuthorPersist;
import com.dev.api.hatoes.AuthorModelAssembler;
import com.dev.api.service.AuthorService;
import com.dev.api.util.JsonUtil;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("authors")
public class AuthorController {

	private final AuthorService authorService;

    private final AuthorModelAssembler authorModelAssembler;

    private final ModelMapper modelMapper;

    public AuthorController(AuthorService authorService, AuthorModelAssembler authorModelAssembler, ModelMapper modelMapper) {
        this.authorService = authorService;
        this.authorModelAssembler = authorModelAssembler;
        this.modelMapper = modelMapper;
    }
    
    @GetMapping
    public CollectionModel<EntityModel<Author>> all(
            @NonNull @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @NonNull @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {
        var pageable = PageRequest.of(page, size);

        var authors = authorService.findAll(pageable).stream()
                .map(authorModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(authors, linkTo(methodOn(AuthorController.class).all(size, page))
                .withSelfRel());
    }

    @GetMapping("{id}")
    public EntityModel<Author> one(@NonNull @PathVariable("id") Long id) {
        var author = authorService.findByIdOrElseThrow(id);
        return authorModelAssembler.toModel(author);
    }

    @PostMapping
    public ResponseEntity<?> newAuthor(@NonNull @Valid @RequestBody AuthorPersist authorPersist) {
    	System.out.println(authorPersist.getEmail());
        var author = modelMapper.map(authorPersist, Author.class);
        var entityModel = authorModelAssembler.toModel(authorService.save(author));

        
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(
            @NonNull @Valid @RequestBody AuthorPersist authorPersist,
            @NonNull @PathVariable Long id) {
        var author = authorService.findByIdOrElseThrow(id);

        modelMapper.map(authorPersist, author);

        var entityModel = authorModelAssembler.toModel(authorService.save(author));

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@NonNull @PathVariable Long id) {
        authorService.deleteByIdOrElseThrow(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateAuthor(
            @NonNull @PathVariable("id") Long id,
            @Valid @RequestBody AuthorUpdateDto authorUpdateDto) {
        Author author = authorService.findByIdOrElseThrow(id);

        author.setName(authorUpdateDto.getName().get());
        author.setEmail(authorUpdateDto.getEmail().get());
        JsonUtil.changeIfPresent(authorUpdateDto.getLinkedIn(), author::setLinkedIn);
        JsonUtil.changeIfPresent(authorUpdateDto.getFaceBook(), author::setFaceBook);
        JsonUtil.changeIfPresent(authorUpdateDto.getTwitter(), author::setTwitter);
        JsonUtil.changeIfPresent(authorUpdateDto.getSummary(), author::setSummary);

        var entityModel = authorModelAssembler.toModel(authorService.save(author));

        return ResponseEntity.ok(entityModel);
    }
}
