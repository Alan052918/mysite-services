package com.example.blogpost.model_assembler;

import com.example.blogpost.controller.BlogPostController;
import com.example.blogpost.entity.BlogPost;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BlogPostModelAssembler implements RepresentationModelAssembler<BlogPost, EntityModel<BlogPost>> {

    @Override
    @NonNull
    public EntityModel<BlogPost> toModel(@NonNull BlogPost blogPost) {
        return EntityModel.of(blogPost,
                linkTo(methodOn(BlogPostController.class).getBlogPostById(blogPost.getId())).withSelfRel(),
                linkTo(methodOn(BlogPostController.class).getAllBlogPosts()).withRel("posts"));
    }

    @Override
    @NonNull
    public CollectionModel<EntityModel<BlogPost>> toCollectionModel(@NonNull Iterable<? extends BlogPost> blogPosts) {
        return RepresentationModelAssembler.super.toCollectionModel(blogPosts);
    }

}
