package com.example.blogpost.modelassembler;

import com.example.blogpost.controller.CommentController;
import com.example.blogpost.entity.Comment;
import lombok.NonNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CommentModelAssembler implements RepresentationModelAssembler<Comment, EntityModel<Comment>> {

    @Override
    @NonNull
    public EntityModel<Comment> toModel(@NonNull Comment comment) {
        return EntityModel.of(comment,
                linkTo(methodOn(CommentController.class).getCommentById(comment.getId())).withSelfRel(),
                linkTo(methodOn(CommentController.class).getAllCommentsByPostId(comment.getPost().getId())).withRel("post comments"),
                linkTo(methodOn(CommentController.class).getAllComments()).withRel("comments"));
    }

    @Override
    @NonNull
    public CollectionModel<EntityModel<Comment>> toCollectionModel(@NonNull Iterable<? extends Comment> comments) {
        return RepresentationModelAssembler.super.toCollectionModel(comments);
    }

}
