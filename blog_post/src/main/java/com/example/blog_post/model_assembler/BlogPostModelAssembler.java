package com.example.blog_post.model_assembler;

import com.example.blog_post.controller.BlogPostController;
import com.example.blog_post.entity.BlogPost;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
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
