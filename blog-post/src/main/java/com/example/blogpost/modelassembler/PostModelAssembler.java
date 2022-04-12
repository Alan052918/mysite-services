package com.example.blogpost.modelassembler;

import com.example.blogpost.controller.PostController;
import com.example.blogpost.entity.Post;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PostModelAssembler implements RepresentationModelAssembler<Post, EntityModel<Post>> {

    @Override
    @NonNull
    public EntityModel<Post> toModel(@NonNull Post post) {
        return EntityModel.of(post,
                linkTo(methodOn(PostController.class).getPostById(post.getId())).withSelfRel(),
                linkTo(methodOn(PostController.class).getAllPosts()).withRel("posts"));
    }

    @Override
    @NonNull
    public CollectionModel<EntityModel<Post>> toCollectionModel(@NonNull Iterable<? extends Post> blogPosts) {
        return RepresentationModelAssembler.super.toCollectionModel(blogPosts);
    }

}
